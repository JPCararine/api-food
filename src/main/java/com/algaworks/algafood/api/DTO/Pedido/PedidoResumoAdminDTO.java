package com.algaworks.algafood.api.DTO.Pedido;

import com.algaworks.algafood.api.DTO.Restaurante.RestauranteResumoDTO;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonPropertyOrder({"id", "restaurante", "usuario","itens", "subtotal", "taxaFrete", "valorTotal", "formaPagamentos", "endereco", "status"})
@Data
public class PedidoResumoAdminDTO {

    private Long id;



    private RestauranteResumoDTO restaurante;


    private StatusPedido status;
}
