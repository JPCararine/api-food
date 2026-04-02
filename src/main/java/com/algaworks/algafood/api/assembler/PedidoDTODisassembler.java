package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.DTO.Pedido.PedidoInputDTO;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class PedidoDTODisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public Pedido toEntity(PedidoInputDTO pedidoInputDTO) {
        return  modelMapper.map(pedidoInputDTO, Pedido.class);
    }
    public void copyToEntity(PedidoInputDTO pedidoInputDTO, Pedido pedido) {
        modelMapper.map(pedidoInputDTO, pedido);
    }
}
