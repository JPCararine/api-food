package com.algaworks.algafood.api.v1.DTO.Pedido;

import com.algaworks.algafood.api.v1.DTO.Endereco.EnderecoSimplificadoInputDTO;
import com.algaworks.algafood.api.v1.DTO.FormaPagamento.FormaPagamentoIDInputDTO;
import com.algaworks.algafood.api.v1.DTO.ItemPedido.ItemPedidoInputDTO;
import com.algaworks.algafood.api.v1.DTO.Usuario.UsuarioIdInputDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PedidoInputDTO {



    @NotNull
    private Long restauranteId;

    @Valid
    @NotNull
    private EnderecoSimplificadoInputDTO endereco;
    @Valid
    @NotEmpty
    private List<ItemPedidoInputDTO> itens;
    @Valid
    @NotNull
    private FormaPagamentoIDInputDTO formaPagamento;


}
