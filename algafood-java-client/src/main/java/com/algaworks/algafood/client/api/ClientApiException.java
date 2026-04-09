package com.algaworks.algafood.client.api;

import com.algaworks.algafood.client.model.Problem;
import lombok.Getter;
import org.springframework.web.client.RestClientResponseException;
import tools.jackson.databind.ObjectMapper;


public class ClientApiException extends RuntimeException {

    @Getter
    private Problem problem;

    public ClientApiException(String message, RestClientResponseException cause) {
        super(message, cause);

        deserializeProblem(cause);
    }

    private void deserializeProblem(RestClientResponseException cause) {
        ObjectMapper mapper = new ObjectMapper();

        this.problem = mapper.readValue(cause.getResponseBodyAsString(), Problem.class);
    }
}
