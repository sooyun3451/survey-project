package com.surveyProject.project.web.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginReqDto {
    private String email;
    private String password;
}
