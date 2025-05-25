package com.bigchatbrasil.modules.mensagem.useCases;

import com.bigchatbrasil.modules.chat.repository.ChatRepository;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.interfaces.CheckSaldo;
import com.bigchatbrasil.modules.cliente.useCases.FindClienteUseCase;
import com.bigchatbrasil.modules.destinatario.repository.DestinatarioRepository;
import com.bigchatbrasil.modules.mensagem.dto.CreateMensagemRequestDTO;
import com.bigchatbrasil.modules.mensagem.dto.MensagemResponseDTO;
import com.bigchatbrasil.modules.mensagem.entity.MensagemEntity;
import com.bigchatbrasil.modules.mensagem.enums.Prioridade;
import com.bigchatbrasil.modules.mensagem.enums.StatusMensagem;
import com.bigchatbrasil.modules.mensagem.interfaces.EnviarMensagem;
import com.bigchatbrasil.modules.mensagem.repository.MensagemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EnviarMensagensUseCase {

    private final MensagemRepository repository;

    private final ChatRepository chatRepository;

    private final List<CheckSaldo> estrategiasSaldos;

    private final List<EnviarMensagem> estrategiaEnvio;

    private final FindClienteUseCase findClienteUseCase;

    private final DestinatarioRepository destinatarioRepository;

    private final Queue<MensagemEntity> filaMensagens = new PriorityQueue<>();

    public MensagemResponseDTO execute(CreateMensagemRequestDTO mensagem, UUID clienteId) {
        ClienteEntity cliente = findClienteUseCase.execute(clienteId);
        MensagemEntity mensagemEntity = new MensagemEntity();
        mensagemEntity.setChat(chatRepository.findById(mensagem.chatId()).orElseThrow(() -> new RuntimeException("Chat não encontrado")));
        mensagemEntity.setCliente(cliente);
        mensagemEntity.setDestinatario(destinatarioRepository.findById(mensagem.destinatarioId()).orElseThrow(() -> new RuntimeException("Destinatário não encontrado")));
        mensagemEntity.setTexto(mensagem.texto());
        mensagemEntity.setPrioridade(mensagem.prioridade());
        mensagemEntity.setWhatsapp(mensagem.whatsapp());
        filaMensagens.offer(mensagemEntity);
        mensagemEntity.setStatus(StatusMensagem.NA_FILA);
        mensagemEntity.setCusto(Prioridade.NORMAL.equals(mensagem.prioridade()) ? CheckSaldo.valorNormal : CheckSaldo.valorPrioritario);
        MensagemEntity mensagemSalva = repository.save(mensagemEntity);
        processarFila(cliente, mensagem.prioridade());

        return new MensagemResponseDTO(mensagemSalva.getId(), mensagemSalva.getCliente().getId(), mensagemSalva.getDestinatario().getId(), mensagemSalva.getTexto(), mensagemSalva.getDataHoraEnvio(), mensagemSalva.getPrioridade(), mensagemSalva.getStatus(), mensagemSalva.getCusto());
    }

    private void processarFila(ClienteEntity cliente, Prioridade prioridade) {

        CheckSaldo checkSaldo = estrategiasSaldos.stream()
                .filter(estrategia -> cliente.getConta().getPlano().equals(estrategia.getPlano()))
                .findFirst().orElseThrow(() -> new RuntimeException("Plano não encontrado"));

        checkSaldo.verificaDescontaSaldoCliente(cliente, prioridade);
        while (!filaMensagens.isEmpty()) {
            MensagemEntity mensagem = filaMensagens.poll();
            if (mensagem != null) {
                mensagem.setStatus(StatusMensagem.PROCESSANDO);
                EnviarMensagem enviarMensagem = estrategiaEnvio.stream()
                        .filter(estrategia -> mensagem.isWhatsapp() == estrategia.viaWhatsapp())
                        .findFirst().orElseThrow(() -> new RuntimeException("Estratégia de envio não encontrada"));

                enviarMensagem.enviarMensagem(mensagem);
                mensagem.setStatus(StatusMensagem.ENVIADA);
                repository.save(mensagem);
            }
        }
    }


}
