package com.surveyProject.project.config;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.surveyProject.project.filter.JwtAuthenticationFilter;
import com.surveyProject.project.handler.OAuth2SuccessHandler;
import com.surveyProject.project.service.survey.oauth2.OAuth2UserServiceImpl;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	
	@Lazy
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2UserServiceImpl oAuth2Service;
	
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		
		CorsConfiguration config = new CorsConfiguration();
		
		config.setAllowCredentials(true);
		config.addAllowedOriginPattern("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.addAllowedOrigin("http://localhost:3000");
		
		source.registerCorsConfiguration("/**", config);
		
		return new CorsFilter(source);
	}
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .authorizeHttpRequests(auth -> auth
                		.requestMatchers(
                				new AntPathRequestMatcher("/api/v1/auth/**"),
                				new AntPathRequestMatcher("/api/v1/survey/notice"),
                				new AntPathRequestMatcher("/api/v1/survey/main/notice"),
                				new AntPathRequestMatcher("/payment"),
                				new AntPathRequestMatcher("/result/{surveyCode}/{code}"), 
                				new AntPathRequestMatcher("/survey/group/list/surveyList"), 
                				new AntPathRequestMatcher("/survey/group/list"),
                				new AntPathRequestMatcher("/survey/personal/list"),
                				new AntPathRequestMatcher("/survey/personal/list/main/surveyList"), 
                				new AntPathRequestMatcher("/survey/personal/list/searchList"),
                				new AntPathRequestMatcher("/survey/personal/list/main/survey"),
                				new AntPathRequestMatcher("/api/v1/survey/update"),
                				new AntPathRequestMatcher("/api/v1/survey/category")
                				)
                		.permitAll()
                		.anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2
                        .redirectionEndpoint(endPoint -> endPoint.baseUri("/oauth2/callback/*"))
                        .authorizationEndpoint(endPoint -> endPoint.baseUri("/api/v1/auth/sns-sign-in"))
                        .userInfoEndpoint(endPoint -> endPoint.userService(oAuth2Service))
                        .successHandler(oAuth2SuccessHandler)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
    	
    	DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    	
    	authProvider.setPasswordEncoder(bCryptPasswordEncoder);
    	
    	return new ProviderManager(List.of(authProvider));
    }
    
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
    	return new BCryptPasswordEncoder();
    }

}
