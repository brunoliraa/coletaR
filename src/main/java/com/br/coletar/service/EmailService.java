package com.br.coletar.service;

import com.br.coletar.exception.ColetarException;
import com.br.coletar.model.Email;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;

@Service
@Slf4j
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final EmailBuilder mailContentBuilder;


    @Async
    public void sendMail(Email email) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(email.getRecipient());
            messageHelper.setSubject(email.getSubject());
            messageHelper.setText(email.getbody());
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Activation email sent!!");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new ColetarException("erro na tentativa de enviar email para " + email.getRecipient());
        }
    }
}
