package com.bigchatbrasil.modules.mensagem.useCases;

import com.bigchatbrasil.modules.chat.entity.ChatEntity;
import com.bigchatbrasil.modules.chat.repository.ChatRepository;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.interfaces.CheckSaldo;
import com.bigchatbrasil.modules.cliente.useCases.FindClienteUseCase;
import com.bigchatbrasil.modules.mensagem.dto.CreateMensagemRequestDTO;
import com.bigchatbrasil.modules.mensagem.entity.MensagemEntity;
import com.bigchatbrasil.modules.mensagem.enums.Prioridade;
import com.bigchatbrasil.modules.mensagem.enums.StatusMensagem;
import com.bigchatbrasil.modules.mensagem.interfaces.EnviarMensagem;
import com.bigchatbrasil.modules.mensagem.repository.MensagemRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EnviarMensagensUseCase {

    private final MensagemRepository repository;

    private final ChatRepository chatRepository;

    private final List<CheckSaldo> estrategiasSaldos;

    private final List<EnviarMensagem> estrategiaEnvio;

    private final FindClienteUseCase findClienteUseCase;

    public void execute(List<CreateMensagemRequestDTO> mensagens, UUID clienteId) {

        List<CreateMensagemRequestDTO> mensagensPrioritarias = mensagens.stream()
                .filter(mensagem -> Prioridade.URGENTE.equals(mensagem.prioridade()))
                .toList();

        List<CreateMensagemRequestDTO> mensagensNormais = mensagens.stream()
                .filter(mensagem -> Prioridade.NORMAL.equals(mensagem.prioridade()))
                .toList();

        ClienteEntity cliente = findClienteUseCase.execute(clienteId);
        List<ChatEntity> chats = chatRepository.findAllByIdIn(mensagens.stream().map(CreateMensagemRequestDTO::chatId).toList());

        CheckSaldo checkSaldo = estrategiasSaldos.stream()
                .filter(estrategia -> cliente.getConta().getPlano().equals(estrategia.getPlano()))
                .findFirst().orElseThrow(() -> new RuntimeException("Plano não encontrado"));

        checkSaldo.verificaSaldoCliente(cliente, mensagensNormais.size(), mensagensPrioritarias.size());

        LinkedList<MensagemEntity> mensagensNaFila = new LinkedList<>();

        processarMensagensNaFila(mensagensPrioritarias, chats, cliente, mensagensNaFila);

        processarMensagensNaFila(mensagensNormais, chats, cliente, mensagensNaFila);

        List<MensagemEntity> mensagensSalvas = repository.saveAll(mensagensNaFila);

        mensagensSalvas.forEach(mensagem -> {
            EnviarMensagem enviarMensagem = estrategiaEnvio.stream()
                    .filter(estrategia -> mensagem.isWhatsapp() == estrategia.viaWhatsapp())
                    .findFirst().orElseThrow(() -> new RuntimeException("Estratégia de envio não encontrada"));

            checkSaldo.descontaSaldoCliente(cliente, mensagem.getPrioridade());
            enviarMensagem.enviarMensagem(mensagem);
        });

    }

    private void processarMensagensNaFila(List<CreateMensagemRequestDTO> mensagens, List<ChatEntity> chats, ClienteEntity cliente, LinkedList<MensagemEntity> mensagensNaFila) {
        mensagens.forEach(mensagem -> {
            MensagemEntity mensagemEntity = new MensagemEntity();
            BeanUtils.copyProperties(mensagem, mensagemEntity);

            ChatEntity chat = chats.stream()
                    .filter(chatEntity -> chatEntity.getId().equals(mensagem.chatId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Chat não encontrado"));

            mensagemEntity.setChat(chat);
            mensagemEntity.setCliente(cliente);
            mensagemEntity.setDestinatario(chat.getDestinatario());
            mensagemEntity.setStatus(StatusMensagem.NA_FILA);
            mensagensNaFila.add(mensagemEntity);
        });
    }


}
