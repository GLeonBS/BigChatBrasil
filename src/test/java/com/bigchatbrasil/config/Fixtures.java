package com.bigchatbrasil.config;

import com.bigchatbrasil.modules.chat.entity.ChatEntity;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.enums.PlanoEnum;
import com.bigchatbrasil.modules.cliente.enums.TipoDocumento;
import com.bigchatbrasil.modules.cliente.vo.Conta;
import com.bigchatbrasil.modules.destinatario.entity.DestinatarioEntity;

import java.math.BigDecimal;
import java.util.UUID;

public class Fixtures {

    public static ClienteEntity createCliente(UUID id) {
        Conta conta = new Conta(PlanoEnum.PRE_PAGO, new BigDecimal("100.00"), BigDecimal.ZERO, BigDecimal.ZERO);

        return new ClienteEntity(id, "Leon ltda", "49387186000172", TipoDocumento.CNPJ,
                conta, true, "44999999999");
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
}
