package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.DTO.Cidade.CidadeInputDTO;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeInputDisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public Cidade toEntity(CidadeInputDTO cidadeInputDTO, Estado estado) {
        return modelMapper.map(cidadeInputDTO, Cidade.class);
    }
    public void copyToEntity(CidadeInputDTO cidadeInputDTO, Cidade cidade) {
        cidade.setEstado(null);
        modelMapper.map(cidadeInputDTO, cidade);
    }
}
