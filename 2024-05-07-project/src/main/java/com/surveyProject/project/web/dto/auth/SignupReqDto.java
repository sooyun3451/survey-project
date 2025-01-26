package com.surveyProject.project.web.dto.auth;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SignupReqDto {

    private String email;
    private String password;
    private String userName;
    private String gender;
    private String birth;
    private String role = "ROLE_USER";
    private LocalDateTime createDate = LocalDateTime.now();
    private LocalDateTime updateDate = LocalDateTime.now();
}
