package com.algaworks.algafood.api.DTO.Pedido;

import com.algaworks.algafood.api.DTO.Endereco.EnderecoSimplificadoDTO;
import com.algaworks.algafood.api.DTO.Endereco.EnderecoSimplificadoInputDTO;
import com.algaworks.algafood.api.DTO.FormaPagamento.FormaPagamentoIDInputDTO;
import com.algaworks.algafood.api.DTO.ItemPedido.ItemPedidoInputDTO;
import com.algaworks.algafood.api.DTO.Usuario.UsuarioIdInputDTO;
import com.algaworks.algafood.api.DTO.Usuario.UsuarioInputDTO;
import com.algaworks.algafood.domain.model.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PedidoInputDTO {

    @Valid
    @NotNull
    private UsuarioIdInputDTO usuario;

    @Valid
    @NotNull
    private EnderecoSimplificadoInputDTO endereco;
    @Valid
    @NotNull
    private List<ItemPedidoInputDTO> itens;
    @Valid
    @NotNull
    private FormaPagamentoIDInputDTO formaPagamento;


}
