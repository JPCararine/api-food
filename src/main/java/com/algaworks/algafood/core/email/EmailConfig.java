package com.algaworks.algafood.core.email;

import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.infrastructure.service.Email.FakeEnvioEmailService;
import com.algaworks.algafood.infrastructure.service.Email.SmtpEnvioEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
@RequiredArgsConstructor
public class EmailConfig {

    private final EmailProperties emailProperties;
    private final JavaMailSender mailSender;
    private final freemarker.template.Configuration freeMarkerConfig;

    @Bean
    public EnvioEmailService envioEmailService() {

        switch (emailProperties.getImpl()) {

            case SMTP:
                return new SmtpEnvioEmailService(
                        emailProperties,
                        mailSender,
                        freeMarkerConfig
                );

            case FAKE:
                return new FakeEnvioEmailService(
                        emailProperties,
                        mailSender,
                        freeMarkerConfig
                );

            default:
                throw new IllegalArgumentException("Tipo inválido");
        }
    }
}
