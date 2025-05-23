package com.bigchatbrasil.start;

import com.bigchatbrasil.modules.chat.repository.ChatRepository;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;
import com.bigchatbrasil.modules.destinatario.repository.DestinatarioRepository;
import com.bigchatbrasil.modules.mensagem.repository.MensagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartApp implements ApplicationRunner {

    @Autowired
    private ChatRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private DestinatarioRepository destinatarioRepository;

    @Autowired
    private MensagemRepository mensagemRepository;

    @Override
    public void run(org.springframework.boot.ApplicationArguments args) throws Exception {

//        ClienteEntity cliente = new ClienteEntity();
//        cliente.setNome("Leon");
//        cliente.setDocumento("40089815000103");
//        cliente.setTipoDocumento(TipoDocumento.CNPJ);
//
//
//        Conta conta = new Conta();
//        conta.setPlano(PlanoEnum.PRE_PAGO);
//        conta.setSaldo(new BigDecimal("100.00"));
//
//        cliente.setConta(conta);
//
//        ClienteEntity clienteSalvo = clienteRepository.save(cliente);
        //
        //        DestinatarioEntity destinatario = new DestinatarioEntity();
        //        destinatario.setNome("Destinatario 1");
        //        destinatario.setNumeroTelefone("44999999999");
        //
        //        DestinatarioEntity destinatarioSalvo = destinatarioRepository.save(destinatario);
        //
        //        DestinatarioEntity destinatario2 = new DestinatarioEntity();
        //        destinatario2.setNome("Destinatario 2");
        //        destinatario2.setNumeroTelefone("44999999998");
        //
        //        DestinatarioEntity destinatarioSalvo2 = destinatarioRepository.save(destinatario2);
        //
        //        ChatEntity chat = new ChatEntity();
        //        chat.setRemetente(clienteSalvo);
        //
        //        ChatEntity chatSalvo = repository.save(chat);
        //
        //        ChatDestinatarioEntity chatDestinatario = new ChatDestinatarioEntity();
        //        chatDestinatario.setChat(chatSalvo);
        //        chatDestinatario.setDestinatario(destinatarioSalvo);
        //
        //        ChatDestinatarioEntity chatDestinatarioSalvo = chatDestinatarioRepository.save(chatDestinatario);
        //
        //        ChatDestinatarioEntity chatDestinatario2 = new ChatDestinatarioEntity();
        //        chatDestinatario2.setChat(chatSalvo);
        //        chatDestinatario2.setDestinatario(destinatarioSalvo2);
        //
        //        ChatDestinatarioEntity chatDestinatarioSalvo2 = chatDestinatarioRepository.save(chatDestinatario2);
        //
        //        MensagemEntity mensagem = new MensagemEntity();
        //        mensagem.setNumeroTelefone("44999999999");
        //        mensagem.setTexto("Olá, tudo bem?");
        //        mensagem.setChat(chatSalvo);
        //
        //        MensagemEntity mensagem2 = new MensagemEntity();
        //        mensagem2.setNumeroTelefone("44999999999");
        //        mensagem2.setTexto("Oláaaa, tudo bem?");
        //        mensagem2.setChat(chatSalvo);
        //
        //        mensagemRepository.save(mensagem);
        //        mensagemRepository.save(mensagem2);

    }
}
