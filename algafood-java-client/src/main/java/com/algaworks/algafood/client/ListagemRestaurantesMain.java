package com.algaworks.algafood.client;

import com.algaworks.algafood.client.api.ClientApiException;
import com.algaworks.algafood.client.api.RestauranteClient;
import org.springframework.web.client.RestTemplate;

public class ListagemRestaurantesMain {

    public static void main(String[] args) {
        try {
        RestTemplate  restTemplate = new RestTemplate();

        RestauranteClient restauranteClient = new RestauranteClient("http://localhost:8080", restTemplate);

        restauranteClient.listar().stream()
                .forEach(System.out::println);
    } catch (ClientApiException e) {
            if(e.getProblem() != null) {
                System.out.println(e.getProblem().getUserMessage());
            } else {
                System.out.println("Erro desconhecido");
                e.printStackTrace();
            }
        }

        }
}
