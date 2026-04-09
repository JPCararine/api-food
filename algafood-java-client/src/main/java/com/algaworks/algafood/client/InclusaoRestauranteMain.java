package com.algaworks.algafood.client;

import com.algaworks.algafood.client.api.RestauranteClient;
import org.springframework.web.client.RestTemplate;


public class InclusaoRestauranteMain {

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();

        RestauranteClient restauranteClient = new RestauranteClient("http://localhost:8080", restTemplate);


    }
}


