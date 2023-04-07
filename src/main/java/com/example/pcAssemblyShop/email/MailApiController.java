package com.example.pcAssemblyShop.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class MailApiController {
    @Autowired
    MailService mailService;

    @PostMapping("/api/send")
    public ResponseEntity<MailDto> sendmail(@RequestPart(value="file")MultipartFile file,
                                            @RequestParam("subject") String subject,
                                            @RequestParam("message") String message
    ){

        log.info("Attachment info:::::::::"+file.getOriginalFilename());
        log.info("subject info:::::::::"+subject);

        //We don't know requestparam can parse multiple multipart file as list, so we just create
        // multipartFileList to append each file manually
        List<MultipartFile> multipartFileList = new ArrayList<>();

        multipartFileList.add(file);
        MailDto mailDto = MailDto.builder()
                .subject(subject)
                .message(message)
                .build();

        //Testing multiple email sending to all subscribers
        List<String> subscriberEmail = new ArrayList<>();
        subscriberEmail.add("samuel13241@gmail.com");
        subscriberEmail.add("samuel13241@gmail.com");


        //set mail receiver
        mailDto.setTo("samuel13241@gmail.com");

//        MailDto mailDto = MailDto.builder()
//                .to("samuel13241@gmail.com")
//                .subject("title")
//                .message("<html><head></head><body><div style=\"background-color: gray;\">테스트 메일 본문<div></body></html>")
//                .build();

        mailService.sendMail(mailDto,file);

        return ResponseEntity.status(HttpStatus.OK).body(mailDto);
    }
}
