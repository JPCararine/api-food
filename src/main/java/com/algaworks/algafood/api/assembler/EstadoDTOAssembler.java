package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.DTO.Estado.EstadoDTO;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstadoDTOAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public EstadoDTO toDTO(Estado estado) {
        return modelMapper.map(estado, EstadoDTO.class);
    }
}
