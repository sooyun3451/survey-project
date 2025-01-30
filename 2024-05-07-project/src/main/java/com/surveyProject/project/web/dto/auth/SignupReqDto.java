package com.surveyProject.project.web.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SignupReqDto {

    private String email;
    private String password;
    private String userName;
    private String gender;
    private String birth;
    private String role = "ROLE_USER";
    private LocalDateTime createDate = LocalDateTime.now();
    private LocalDateTime updateDate = LocalDateTime.now();
    private String joinPath;
    private String snsId;
}
