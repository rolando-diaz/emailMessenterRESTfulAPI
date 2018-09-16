package com.emailManager.logicService;

import com.emailManager.enums.ENUM;
import com.emailManager.enums.FAILED;
import com.emailManager.entity.Message;
import com.emailManager.enums.SUCCESS;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

/**
 * It encapsulates a message object to be sent out through the JavaMailSender.
 */
public class OutBox {
    public JavaMailSender emailSender;
    private Message message;
    String subject1 = SUCCESS.THANKS.toString() ;
    String subject2 = SUCCESS.MESSAGE_FROM.toString();
    String content = SUCCESS.LINK.toString();

    /**
     * Custom constructor
     * @param emailSender
     * @param message
     */
    public OutBox(JavaMailSender emailSender, Message message){
        this.message = message;
        this.emailSender = emailSender;
    }

    /**
     * Sends out a confirmation email to the client(sender) user, and one to the owner of the portfolio.
     * @return
     */
    public ENUM sendEmail() {
        subject2+=message.getEmail();
        content+="<br><br>"+SUCCESS.MESSAGE_BODY.toString();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        MimeMessage msg = emailSender.createMimeMessage();
        MimeMessageHelper htmlMessage;

        try{
            msg.setContent(content, "text/html");
            htmlMessage = new MimeMessageHelper(msg, false, "utf-8");
            htmlMessage.setTo(message.getEmail());
            htmlMessage.setSubject(subject1);
            emailSender.send(msg);

            simpleMailMessage.setTo(SUCCESS.MyEmail.toString());
            simpleMailMessage.setSubject(subject2);
            simpleMailMessage.setText(message.getBody());
            emailSender.send(simpleMailMessage);

            return SUCCESS.THANKS;
        }catch (Exception e){
            e.printStackTrace();
            return FAILED.INVALID_EMAIL_ADDRESS;
        }
    }

}
