package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.DTO.Cidade.CidadeDTO;
import com.algaworks.algafood.api.DTO.Cidade.CidadeResumoDTO;
import com.algaworks.algafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CidadeDTO toDto(Cidade cidade) {

        return modelMapper.map(cidade, CidadeDTO.class);
    }

}
