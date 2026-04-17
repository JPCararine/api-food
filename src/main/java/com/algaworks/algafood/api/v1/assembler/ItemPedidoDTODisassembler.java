package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.DTO.ItemPedido.ItemPedidoInputDTO;
import com.algaworks.algafood.domain.exception.NotFound.ProdutoNotFoundException;
import com.algaworks.algafood.domain.model.ItemPedido;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.infrastructure.repository.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemPedidoDTODisassembler {

    @Autowired
    private ProdutoRepository produtoRepository;

    public ItemPedido toEntity(ItemPedidoInputDTO itemPedidoInputDTO) {
        Produto produto = produtoRepository.findById(itemPedidoInputDTO.getProduto().getId())
                .orElseThrow(()-> new ProdutoNotFoundException(itemPedidoInputDTO.getProduto().getId()));

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(itemPedidoInputDTO.getQuantidade());
        itemPedido.setObservacao(itemPedidoInputDTO.getObservacao());

        return itemPedido;
    }
}
