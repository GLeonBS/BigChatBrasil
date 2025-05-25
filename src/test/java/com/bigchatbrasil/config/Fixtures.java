package com.bigchatbrasil.config;

import com.bigchatbrasil.modules.chat.entity.ChatEntity;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.enums.PlanoEnum;
import com.bigchatbrasil.modules.cliente.enums.RoleEnum;
import com.bigchatbrasil.modules.cliente.enums.TipoDocumentoEnum;
import com.bigchatbrasil.modules.cliente.vo.Conta;
import com.bigchatbrasil.modules.destinatario.entity.DestinatarioEntity;
import com.bigchatbrasil.modules.mensagem.entity.MensagemEntity;
import com.bigchatbrasil.modules.mensagem.enums.Prioridade;
import com.bigchatbrasil.modules.mensagem.enums.StatusMensagem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Fixtures {

    public static ClienteEntity createCliente(UUID id) {
        Conta conta = new Conta(PlanoEnum.PRE_PAGO, new BigDecimal("100.00"), BigDecimal.ZERO, BigDecimal.ZERO);

        return new ClienteEntity(id, "Leon ltda", "49387186000172", TipoDocumentoEnum.CNPJ,
                conta, true, "44999999999", "SenhaTeste", RoleEnum.ROLE_CLIENTE);
    }

    public static DestinatarioEntity createDestinatario(UUID id, ClienteEntity cliente) {

        DestinatarioEntity destinatario = new DestinatarioEntity();

        destinatario.setId(id);
        destinatario.setNome("Leon");
        destinatario.setNumeroTelefone("44999999999");
        destinatario.setCliente(cliente);

        return destinatario;
    }

    public static ChatEntity createChat(UUID id, ClienteEntity cliente, DestinatarioEntity destinatario) {
        ChatEntity chat = new ChatEntity();
        chat.setId(id);
        chat.setRemetente(cliente);
        chat.setDestinatario(destinatario);
        return chat;
    }

    public static MensagemEntity createMensagem(UUID id, ChatEntity chat, String texto) {
        MensagemEntity mensagem = new MensagemEntity();
        mensagem.setId(id);
        mensagem.setChat(chat);
        mensagem.setCliente(chat.getRemetente());
        mensagem.setDestinatario(chat.getDestinatario());
        mensagem.setDataHoraEnvio(LocalDateTime.of(2025, 5, 25, 11, 2));
        mensagem.setPrioridade(Prioridade.NORMAL);
        mensagem.setStatus(StatusMensagem.ENTREGUE);
        mensagem.setCusto(BigDecimal.valueOf(0.25));
        mensagem.setWhatsapp(false);
        mensagem.setTexto(texto);
        return mensagem;
    }
}
