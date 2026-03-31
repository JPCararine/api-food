package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.DTO.Cidade.CidadeResumoDTO;
import com.algaworks.algafood.api.DTO.Restaurante.RestauranteDTO;
import com.algaworks.algafood.api.DTO.Restaurante.RestauranteDetalhadoDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public RestauranteDTO toDTO(Restaurante restaurante) {

        return modelMapper.map(restaurante, RestauranteDTO.class);
    }
    public RestauranteDetalhadoDTO toDTODetalhado(Restaurante restaurante) {

        return modelMapper.map(restaurante, RestauranteDetalhadoDTO.class);


    }

}
