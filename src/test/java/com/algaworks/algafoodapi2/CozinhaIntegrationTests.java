package com.algaworks.algafoodapi2;

import com.algaworks.algafoodapi2.domain.model.Cozinha;
import com.algaworks.algafoodapi2.service.CozinhaService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
public class CozinhaIntegrationTests {

        @Autowired
        private CozinhaService cozinhaService;

        @Test
        public void testarCozinhaSave(){

            Cozinha cozinha = new Cozinha();
            cozinha.setNome("Chinesa");

            cozinha = cozinhaService.save(cozinha);

            assertThat(cozinha).isNotNull();
            assertThat(cozinha.getId()).isNotNull();
        }
        @Test
        public void testeCozinhaQuandoSemNome() {
            Cozinha cozinha = new Cozinha();
            cozinha.setNome(null);

            ConstraintViolationException erro = Assertions.assertThrows(ConstraintViolationException.class, () -> {
                cozinhaService.save(cozinha);
            });

            assertThat(erro).isNotNull();

        }

}
