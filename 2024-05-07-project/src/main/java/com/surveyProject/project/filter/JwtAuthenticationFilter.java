package com.surveyProject.project.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.surveyProject.project.provider.JwtProvider;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private final JwtProvider jwtProvider;
	
	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authorizationHeader = request.getHeader("Authorization");

            String token = (authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
                    ? jwtProvider.removeBearer(authorizationHeader)
                    : null;

            if(token == null || !jwtProvider.isValidToken(token)) {
                filterChain.doFilter(request, response);
                return;
            }

            String userId = jwtProvider.getUserIdFromJwt(token);

            setAuthenticationContext(request, userId);
        } catch(Exception e) {
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }
	
	private void setAuthenticationContext(HttpServletRequest request, String userCode) {
		AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userCode, null, AuthorityUtils.NO_AUTHORITIES);
		
		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		
		SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
		securityContext.setAuthentication(authenticationToken);
		
		SecurityContextHolder.setContext(securityContext);
	}
	

}
