package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.DTO.Pedido.PedidoResumoAdminDTO;
import com.algaworks.algafood.api.DTO.Pedido.PedidoResumoDTO;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoDTOAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public PedidoResumoDTO toDTO(Pedido pedido) {
        PedidoResumoDTO dto = modelMapper.map(pedido, PedidoResumoDTO.class);

        dto.setFormaPagamentos(pedido.getFormaPagamento().getDescricao());

        return dto;
    }
    public PedidoResumoAdminDTO toAdminDTO(Pedido pedido) {
        PedidoResumoAdminDTO dto = modelMapper.map(pedido, PedidoResumoAdminDTO.class);


        return dto;
    }
}
