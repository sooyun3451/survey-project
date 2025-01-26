package com.surveyProject.project.web.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class LoginResDto {
    private int user_code;
    private String user_email;
    private String user_name;
    private String token;
    private String role;
    private int expirationTime;
}
