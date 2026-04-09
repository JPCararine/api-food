package com.algaworks.algafood.client.api;

import com.algaworks.algafood.client.model.RestauranteDetalhadoDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class RestauranteClient {

    private static final String RESOURCE_PATH = "/restaurantes";

    private String url;
    private RestTemplate restTemplate;

    public List<RestauranteDetalhadoDTO> listar() {


        URI resourceURi = URI.create(url + RESOURCE_PATH);

        RestauranteDetalhadoDTO[] restaurantes = restTemplate.getForObject(resourceURi, RestauranteDetalhadoDTO[].class);
        return Arrays.asList(restaurantes);
    }
    



}

