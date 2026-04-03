package com.algaworks.algafood.api.DTO.Pedido;

import com.algaworks.algafood.api.DTO.Endereco.EnderecoSimplificadoDTO;

import com.algaworks.algafood.api.DTO.ItemPedido.ItemPedidoResumoDTO;
import com.algaworks.algafood.api.DTO.Usuario.UsuarioIdNomeEmailDTO;
import com.algaworks.algafood.domain.model.*;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
@JsonPropertyOrder({"codigo", "usuario","itens", "subtotal", "taxaFrete", "valorTotal", "formaPagamentos", "endereco", "status"})
@Data
public class PedidoResumoDTO {

    private String codigo;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;

    private OffsetDateTime dataCriacao;

    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataCancelamento;
    private OffsetDateTime dataEntrega;

    private UsuarioIdNomeEmailDTO usuario;

    private EnderecoSimplificadoDTO endereco;

    private List<ItemPedidoResumoDTO> itens;

    private String formaPagamentos;

    private StatusPedido status;


}
