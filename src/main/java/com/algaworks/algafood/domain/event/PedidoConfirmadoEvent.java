package com.algaworks.algafood.domain.event;


import com.algaworks.algafood.domain.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PedidoConfirmadoEvent {

    private Pedido pedido;
}
