package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Collections;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class NotificacaoClientePedidoCanceladoListener {


    private final EnvioEmailService envioEmailService;

    @TransactionalEventListener
    public void aoCancelarPedido(PedidoCanceladoEvent event) {
        Pedido pedido = event.getPedido();
        Set<String> emails = Collections.singleton(pedido.getUsuario().getEmail());
        envioEmailService.enviar(EnvioEmailService.Mensagem.builder()
                .destinatarios(emails)
                .assunto(pedido.getRestaurante().getNome() + "  - Pedido cancelado")
                .parametro("pedido", pedido)
                .mensagem("pedido-cancelado.ftl")
                .build());
    }
}
