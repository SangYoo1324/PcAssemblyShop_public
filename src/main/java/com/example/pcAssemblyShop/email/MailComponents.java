package com.example.pcAssemblyShop.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
//@RequiredArgsConstructor: 생성자를 별도로 만들지 않아도 자동 삽입해준다.(여기서는 JavaMailSender)
@Component
public class MailComponents {

    private final JavaMailSender javaMailSender;

    public void sendMailTest(){

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("samuel13241@gmail.com");
        simpleMailMessage.setSubject("테스트 이메일");
        simpleMailMessage.setText("정상적으로 송신 되었습니다");

        javaMailSender.send(simpleMailMessage);

    }
}

