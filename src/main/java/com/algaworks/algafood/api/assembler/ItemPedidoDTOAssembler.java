package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.DTO.ItemPedido.ItemPedidoDTO;
import com.algaworks.algafood.domain.model.ItemPedido;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemPedidoDTOAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public ItemPedidoDTO toDTO(ItemPedido itemPedido) {
        return modelMapper.map(itemPedido, ItemPedidoDTO.class);
    }
}
