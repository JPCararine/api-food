package com.algaworks.algafood.api.DTO.Pedido;

import com.algaworks.algafood.api.DTO.Restaurante.RestauranteResumoDTO;
import com.algaworks.algafood.api.DTO.Usuario.UsuarioIdNomeEmailDTO;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.OffsetDateTime;

@JsonPropertyOrder({"codigo", "restaurante", "usuario","itens", "subtotal", "taxaFrete", "valorTotal", "formaPagamentos", "endereco", "status"})
@Data
public class PedidoResumoAdminDTO {

    private String codigo;

    private UsuarioIdNomeEmailDTO usuario;

    private RestauranteResumoDTO restaurante;

    private OffsetDateTime dataCriacao;
    private StatusPedido status;
}
