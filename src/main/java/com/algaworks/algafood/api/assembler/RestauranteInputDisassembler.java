package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.DTO.Restaurante.RestauranteDTOPut;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteInputDisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public Restaurante toEntity(RestauranteDTOPut restauranteDTOPut, Cozinha cozinha, Cidade cidade) {
         Restaurante restaurante = modelMapper.map(restauranteDTOPut, Restaurante.class);

         restaurante.setCozinha(cozinha);
        if(restaurante.getEndereco() != null) {
            restaurante.getEndereco().setCidade(cidade);
        }

         return restaurante;

    }
    public void copyToEntity(RestauranteDTOPut restauranteDTOPut, Restaurante restaurante) {
        restaurante.setCozinha(new Cozinha());

        if(restaurante.getEndereco() != null) {
            restaurante.getEndereco().setCidade(new Cidade());
        }
        modelMapper.map(restauranteDTOPut, restaurante);
    }
}
