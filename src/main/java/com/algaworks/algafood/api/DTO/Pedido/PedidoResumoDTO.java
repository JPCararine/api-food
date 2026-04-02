package com.algaworks.algafood.api.DTO.Pedido;

import com.algaworks.algafood.api.DTO.Endereco.EnderecoSimplificadoDTO;
import com.algaworks.algafood.api.DTO.ItemPedido.ItemPedidoDTO;
import com.algaworks.algafood.api.DTO.Usuario.UsuarioIdNomeDTO;
import com.algaworks.algafood.domain.model.*;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
@JsonPropertyOrder({"id", "usuario","itens", "subtotal", "taxaFrete", "valorTotal", "formaPagamentos", "endereco", "status"})
@Data
public class PedidoResumoDTO {

    private Long id;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;

    private OffsetDateTime dataCriacao;

    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataCancelamento;
    private OffsetDateTime dataEntrega;




    private UsuarioIdNomeDTO usuario;


    private EnderecoSimplificadoDTO endereco;

    private List<ItemPedidoDTO> itens;

    private String formaPagamentos;

    private StatusPedido status;


}
