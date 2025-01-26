package com.surveyProject.project.web.dto.auth;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class SignupComReqDto {
	
    private String email;
    private String password;
    private String companyName;
    private String role = "ROLE_USER";
    private int joinStatus = 0;
    private LocalDateTime createDate = LocalDateTime.now();
    private LocalDateTime updateDate = LocalDateTime.now();
    private MultipartFile file;
}
