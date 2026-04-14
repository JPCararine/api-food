package com.algaworks.algafood.api.v1.assembler;


import com.algaworks.algafood.api.v1.DTO.ItemPedido.ItemPedidoResumoDTO;
import com.algaworks.algafood.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemPedidoDTOAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public ItemPedidoResumoDTO toDTO(ItemPedido itemPedido) {
        return modelMapper.map(itemPedido, ItemPedidoResumoDTO.class);
    }
}
