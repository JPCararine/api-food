package com.algaworks.algafood.api.mapper;

import com.algaworks.algafood.api.DTO.CozinhaDTO;
import com.algaworks.algafood.api.DTO.RestauranteDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class RestauranteDTOMapper {

    public RestauranteDTO toDTO(Restaurante restaurante){
        RestauranteDTO restauranteDTO = new RestauranteDTO();
        restauranteDTO.setId(restaurante.getId());
        restauranteDTO.setNome(restaurante.getNome());
        restauranteDTO.setTaxaFrete(restaurante.getTaxaFrete());
        restauranteDTO.setAberto(restaurante.getAberto());
        restauranteDTO.setAtivo(restaurante.getAtivo());
        if(restaurante.getCozinha() != null) {
            CozinhaDTO cozinhaDTO = new CozinhaDTO();
            cozinhaDTO.setId(restaurante.getCozinha().getId());
            cozinhaDTO.setNome(restaurante.getCozinha().getNome());
            restauranteDTO.setCozinha(cozinhaDTO);
        }
        return restauranteDTO;
    }
}
