package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.DTO.Restaurante.CozinhaDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class CozinhaDTOAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public CozinhaDTO toDto(Cozinha cozinha) {
        return modelMapper.map(cozinha, CozinhaDTO.class);
    }
}
