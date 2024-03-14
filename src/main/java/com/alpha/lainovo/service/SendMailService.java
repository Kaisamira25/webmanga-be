package com.alpha.lainovo.service;

import com.alpha.lainovo.model.Email;
import com.alpha.lainovo.service.ServiceInterface.EmailInterface;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SendMailService implements EmailInterface {
    private final JavaMailSender mailSender;

    List<Email> listEmail = new ArrayList<>();
    @Override
    public void send(Email mail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(mail.getFrom());
            helper.setTo(mail.getTo());
            helper.setSubject(mail.getTitle());
            helper.setText(mail.getBody(), true);
            helper.setReplyTo(mail.getFrom());
            String[] cc = mail.getCc();
            if (cc != null && cc.length > 0){
                helper.setCc(cc);
            }
            String[] bcc = mail.getBcc();
            if (bcc != null && bcc.length > 0){
                helper.setBcc(bcc);
            }
            mailSender.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
