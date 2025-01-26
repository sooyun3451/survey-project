package com.surveyProject.project.web.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginComResDto {
    private String companyEmail;
    private String companyName;
    private int companyCode;
    private String role;
    private String token;
    private int expirationTime;
}
