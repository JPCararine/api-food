package com.algaworks.algafood.infrastructure.service.Email;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.exception.NotFound.EmailException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Slf4j
public class FakeEnvioEmailService extends SmtpEnvioEmailService{


    public FakeEnvioEmailService(EmailProperties emailProperties, JavaMailSender mailSender, Configuration freeMarkerConfig) {
        super(emailProperties, mailSender, freeMarkerConfig);
    }

    @Override
    public void enviar(Mensagem mensagem) {

        String corpo = processarTemplate(mensagem);

        log.info("[FAKE E-MAIL] Para: {}\n{}", mensagem.getDestinatarios(), corpo);
    }

    @Override
    protected String processarTemplate(Mensagem mensagem) {
        return super.processarTemplate(mensagem);
    }
}
