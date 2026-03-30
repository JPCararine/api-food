package com.algaworks.algafood;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.infrastructure.repository.CozinhaRepository;
import com.algaworks.algafood.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import com.algaworks.algafood.util.DatabaseCleaner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-teste.properties")
public class TesteCozinhaIT {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;
    @Autowired
    private CozinhaRepository cozinhaRepository;

    private Cozinha cozinhaJaponesa;
    private String jsonCorretoChinesa;
    private static final int COZINHA_ID_INEXISTENTE = 100;

    private int quantidadeCozinhas;



    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.basePath = "/cozinhas";
        RestAssured.port = port;

        databaseCleaner.clearTables();
        prepararDados();
        jsonCorretoChinesa = ResourceUtils.StringFromRecourse("/cozinha-chinesa.json");

    }


    @Test
    public void deveRetornarStatus200_QuandoGetCozinhas() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }
    @Test
    public void deveRetornarQuantidadeCozinhas_QuandoGetCozinhas() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("nome", Matchers.hasSize(quantidadeCozinhas));
    }

    @Test
    public void deveRetornar201QuandoPostCozinha() {
        given()
                .body(jsonCorretoChinesa)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetornarRespostaEStatusQuandoGetCozinhasExistente() {
        given()
                .pathParam("id", cozinhaJaponesa.getId())
                .accept(ContentType.JSON)
                .when()
                .get("/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", equalTo(cozinhaJaponesa.getNome()));

    }
    @Test
    public void deveRetornarErro404RespostaEStatusQuandoGetCozinhasInexistente() {
        given()
                .pathParam("id", COZINHA_ID_INEXISTENTE)
                .accept(ContentType.JSON)
                .when()
                .get("/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());


    }

    private void prepararDados() {
        Cozinha cozinhaTailandesa = new Cozinha();
        cozinhaTailandesa.setNome( "Tailandesa" );
        cozinhaRepository.save(cozinhaTailandesa);
        cozinhaJaponesa = new Cozinha();
        cozinhaJaponesa.setNome( "Japonesa" );
        cozinhaRepository.save(cozinhaJaponesa);


        quantidadeCozinhas =  cozinhaRepository.findAll().size();

    }
}

