package com.algaworks.algafood.infrastructure.service.Email;

import com.algaworks.algafood.core.email.EmailProperties;
import freemarker.template.Configuration;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class SandBoxEnvioEmailService extends SmtpEnvioEmailService{

    public SandBoxEnvioEmailService(EmailProperties emailProperties, JavaMailSender mailSender, Configuration freeMarkerConfig) {
        super(emailProperties, mailSender, freeMarkerConfig);
    }


    @Override
    protected MimeMessage createMimeMessage(Mensagem mensagem) throws MessagingException {
        MimeMessage mimeMessage = super.createMimeMessage(mensagem);

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setTo(getEmailProperties().getSandBox().getDestinatario());

        return mimeMessage;

    }
}
