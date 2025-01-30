package com.surveyProject.project.handler;

import com.surveyProject.project.common.object.CustomOAuth2User;
import com.surveyProject.project.domain.survey.user.CustomOauth2User;
import com.surveyProject.project.domain.survey.user.User;
import com.surveyProject.project.domain.survey.user.UserRepository;
import com.surveyProject.project.mapper.UserMapper;
import com.surveyProject.project.provider.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final UserMapper userMapper;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = customOAuth2User.getAttributes();
        boolean existed = customOAuth2User.isExisted();

        // 회원가입 O
        if (existed) {
            User optionalUser = userMapper.findByUser(customOAuth2User.getName());
            User user = null;
            String accessToken = null;
            user = optionalUser;
            if (user != null) {
                accessToken = jwtProvider.generateJwtToken(user.getUser_email());
            }
            int expirTime = jwtProvider.getExpiration();
            response.sendRedirect("http://localhost:3000/sns-success?accessToken=" + accessToken + "&expiration= + " + expirTime);
        }
        // 회원가입 X
        else {
            String snsId = (String) attributes.get("snsId");
            String joinPath = (String) attributes.get("joinPath");
            System.out.println("snsId: " + snsId);
            System.out.println("joinPath: " + joinPath);
            response.sendRedirect("http://localhost:3000/auth?snsId=" + snsId + "&joinPath=" + joinPath);
        }

    }

}
