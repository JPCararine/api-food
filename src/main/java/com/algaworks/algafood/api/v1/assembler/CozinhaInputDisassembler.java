package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.DTO.Cozinha.CozinhaInputDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CozinhaInputDisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public Cozinha toEntity(CozinhaInputDTO cozinhaInputDTO) {
        return modelMapper.map(cozinhaInputDTO, Cozinha.class);
    }
    public void copyToEntity(CozinhaInputDTO cozinhaInputDTO, Cozinha cozinha) {
        modelMapper.map(cozinhaInputDTO, cozinha);
    }
}
