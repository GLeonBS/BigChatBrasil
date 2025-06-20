package com.bigchatbrasil.modules.mensagem.useCases;

import com.bigchatbrasil.exceptions.ChatNotFoundException;
import com.bigchatbrasil.exceptions.DestinatarioNotFoundException;
import com.bigchatbrasil.exceptions.PlanoNotFoundException;
import com.bigchatbrasil.modules.chat.repository.ChatRepository;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.interfaces.CheckSaldo;
import com.bigchatbrasil.modules.cliente.useCases.FindClienteUseCase;
import com.bigchatbrasil.modules.destinatario.repository.DestinatarioRepository;
import com.bigchatbrasil.modules.mensagem.config.MensagemConfig;
import com.bigchatbrasil.modules.mensagem.dto.CreateMensagemRequestDTO;
import com.bigchatbrasil.modules.mensagem.dto.MensagemResponseDTO;
import com.bigchatbrasil.modules.mensagem.entity.MensagemEntity;
import com.bigchatbrasil.modules.mensagem.enums.PrioridadeEnum;
import com.bigchatbrasil.modules.mensagem.enums.StatusMensagem;
import com.bigchatbrasil.modules.mensagem.repository.MensagemRepository;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EnviarMensagensUseCase {

    private final MensagemRepository repository;
    private final ChatRepository chatRepository;
    private final DestinatarioRepository destinatarioRepository;
    private final FindClienteUseCase findClienteUseCase;
    private final List<CheckSaldo> estrategiasSaldos;
    private final RabbitTemplate rabbitTemplate;

    public MensagemResponseDTO execute(CreateMensagemRequestDTO mensagem, UUID clienteId) {
        ClienteEntity cliente = findClienteUseCase.execute(clienteId);

        CheckSaldo checkSaldo = estrategiasSaldos.stream()
                .filter(e -> e.getPlano().equals(cliente.getConta().getPlano()))
                .findFirst()
                .orElseThrow(PlanoNotFoundException::new);
        checkSaldo.verificaDescontaSaldoCliente(cliente, mensagem.prioridade());

        MensagemEntity mensagemEntity = montarMensagem(mensagem, cliente);

        MensagemEntity mensagemSalva = repository.save(mensagemEntity);

        rabbitTemplate.convertAndSend(MensagemConfig.NOME_EXCHANGE, MensagemConfig.ROUTING_KEY, mensagemSalva, message -> {
            int prioridade = mensagemEntity.getPrioridade().getNivel();
            message.getMessageProperties().setPriority(prioridade);
            return message;
        });

        return MensagemResponseDTO.from(mensagemSalva);
    }

    private MensagemEntity montarMensagem(CreateMensagemRequestDTO dto, ClienteEntity cliente) {
        MensagemEntity mensagem = new MensagemEntity();
        mensagem.setCliente(cliente);
        mensagem.setChat(chatRepository.findById(dto.chatId()).orElseThrow(ChatNotFoundException::new));
        mensagem.setDestinatario(destinatarioRepository.findById(dto.destinatarioId()).orElseThrow(DestinatarioNotFoundException::new));
        mensagem.setTexto(dto.texto());
        mensagem.setWhatsapp(dto.whatsapp());
        mensagem.setPrioridade(dto.prioridade());
        mensagem.setStatus(StatusMensagem.NA_FILA);
        mensagem.setCusto(dto.prioridade() == PrioridadeEnum.URGENTE ? CheckSaldo.valorPrioritario : CheckSaldo.valorNormal);
        return mensagem;
    }
}
