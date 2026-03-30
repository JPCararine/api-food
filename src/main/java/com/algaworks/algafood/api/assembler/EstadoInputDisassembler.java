package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.DTO.Estado.EstadoDTO;
import com.algaworks.algafood.api.DTO.Estado.EstadoInputDTO;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstadoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;
    public Estado toEntity(EstadoInputDTO estadoInputDTO) {
        return modelMapper.map(estadoInputDTO, Estado.class);
    }
    public Estado copyToEntity(EstadoInputDTO estadoInputDTO, Estado estado) {
        return modelMapper.map(estadoInputDTO, Estado.class);
    }
}
