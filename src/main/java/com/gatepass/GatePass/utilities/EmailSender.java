package com.gatepass.GatePass.utilities;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EmailSender {
    @Autowired
    JavaMailSender javaMailSender;
    public void sendEmail(File qrcode, File barCode, String email) throws EmailException{
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try{
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setText("Use this for class attendance");
            mimeMessageHelper.setSubject("Your codes");
            mimeMessageHelper.addAttachment("qrcode", qrcode);
            mimeMessageHelper.addAttachment("barcode", barCode);
            javaMailSender.send(mimeMessage);
        }catch(Exception e){
            log.info(e.getLocalizedMessage());
            throw new EmailException(e.getMessage());
        }
    }
}
