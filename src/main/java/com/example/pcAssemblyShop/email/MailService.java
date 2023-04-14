package com.example.pcAssemblyShop.email;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {
        @Autowired
        MailComponents mailComponents;

        private final JavaMailSender javaMailSender;

        public void sendMail(MailDto mailDto, MultipartFile multipartFile){
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            try{
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
                mimeMessageHelper.setTo(mailDto.getTo());
                mimeMessageHelper.setSubject(mailDto.getSubject());
                mimeMessageHelper.setText(mailDto.getMessage(),true);

                // file attachment(only multipartFileList not null)
                if(multipartFile!=null)
//                if(!CollectionUtils.isEmpty(mailDto.getMultipartFileList()))
                {
                   // for(MultipartFile multipartFile: mailDto.getMultipartFileList()){
                        mimeMessageHelper.addAttachment(multipartFile.getOriginalFilename(),multipartFile);
                 //   }
                }
//                javaMailSender.send(mimeMessage);
                log.info("Email successfully sent to "+mimeMessage.getReplyTo());
            }catch(MessagingException e){
                log.info("fail to send email");
            }

        }

}
