package com.example.pcAssemblyShop.email;

import com.google.gson.annotations.SerializedName;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class MailDto {

    private String to;
    private String subject;
    private String message;

    //Dto, Entity에 multipart자체를 담지 말고 multipart를 저장한 클라우드 주소를 저장하자.
    //For email case, we don't need to store email data, so we don't need to add multipart IV
    // for the Mail dto, just get multipart with @RequestPart

    @Builder
    public MailDto(String to, String subject, String message) {
        this.to = to;
        this.subject = subject;
        this.message = message;

    }
}
