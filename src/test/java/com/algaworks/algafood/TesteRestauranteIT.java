package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.infrastructure.repository.CozinhaRepository;
import com.algaworks.algafood.infrastructure.repository.RestauranteRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-teste.properties")
public class TesteRestauranteIT {

    @LocalServerPort
    private int port;
    @Autowired
    private RestauranteRepository restauranteRepository;
    private static final int ID_RESTAURANTE_INEXISTENTE = 100;
    private Restaurante burgerTopRestaurante;
    @Autowired
    DatabaseCleaner dataBaseCleaner;
    @Autowired
    private CozinhaRepository cozinhaRepository;

    private Cozinha cozinhaBrasileira;
    private Cozinha cozinhaJaponesa;

    private String jsonRestaurante;
    private String jsonRestauranteSemFrete;

    @BeforeEach
    public void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.basePath="/restaurantes";
        RestAssured.port = port;
        dataBaseCleaner.clearTables();
        prepararDados();
        jsonRestaurante = ResourceUtils.StringFromRecourse("/restaurante.json");
        jsonRestauranteSemFrete = ResourceUtils.StringFromRecourse("/restaurante-semfrete.json");

    }

    @Test
    public void deveRetornar200QuandoGetRestaurante() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }
    @Test
    public void deveRetornar200QuandoGetIDRestauranteEncontrado() {
        RestAssured.given()
                .pathParam("id", burgerTopRestaurante.getId())
                .accept(ContentType.JSON)
                .when()
                .get("/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", equalTo(burgerTopRestaurante.getNome()));

    }
    @Test
    public void deveRetornar404QuandoGetIDRestauranteNaoEncontrado() {
        RestAssured.given()
                .pathParam("id", ID_RESTAURANTE_INEXISTENTE)
                .when()
                .get("/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
    @Test
    public void deveRetornar201QuandoPostRestaurante() {
        RestAssured.given()
                .body(jsonRestaurante)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("nome", equalTo("Lá no Soneca"));
    }
    @Test
    public void deveRetornar400QuandoPostRestauranteSemFrete() {

        RestAssured.given()
                .body(jsonRestauranteSemFrete)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    public void prepararDados() {
        cozinhaBrasileira = new Cozinha();
        cozinhaBrasileira.setNome("Brasileira");
        cozinhaRepository.save(cozinhaBrasileira);

        cozinhaJaponesa = new Cozinha();
        cozinhaJaponesa.setNome("Japonesa");
        cozinhaRepository.save(cozinhaJaponesa);

        burgerTopRestaurante = new Restaurante();
        burgerTopRestaurante.setNome("Burger");
        burgerTopRestaurante.setTaxaFrete(new BigDecimal("10"));
        burgerTopRestaurante.setCozinha(cozinhaBrasileira);
        burgerTopRestaurante.setAtivo(true);
        restauranteRepository.save(burgerTopRestaurante);


    }
}

