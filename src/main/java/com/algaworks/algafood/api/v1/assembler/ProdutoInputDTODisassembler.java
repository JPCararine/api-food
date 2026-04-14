package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.DTO.Produto.ProdutoInputDTO;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoInputDTODisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public Produto toEntity(ProdutoInputDTO produtoInputDTO) {
        return modelMapper.map(produtoInputDTO, Produto.class);
    }
    public void copyToEntity(ProdutoInputDTO produtoInputDTO, Produto produto) {
        modelMapper.map(produtoInputDTO, produto);
    }
}
