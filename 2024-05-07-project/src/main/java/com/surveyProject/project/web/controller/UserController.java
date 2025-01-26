package com.surveyProject.project.web.controller;

import com.surveyProject.project.service.survey.UserService;
import com.surveyProject.project.web.dto.CMRespDto;
import com.surveyProject.project.web.dto.auth.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private static final String SIGNUP = "/signup";
    private static final String SIGNUPCOM = "/signupcom";
    private static final String LOGIN = "/login";
    private static final String LOGINCOM = "/logincom";
    private static final String LOGOUT = "/logout";

    @PostMapping(SIGNUP)
    public ResponseEntity<?> signup(@RequestBody SignupReqDto signupReqDto){
    	System.out.println(signupReqDto);
        boolean status = false;
        try {
            status = userService.signup(signupReqDto);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "Failed", status));
        }

        return ResponseEntity.ok().body(new CMRespDto<>(1, "Success", status));
    }

    @PostMapping(SIGNUPCOM)
    public ResponseEntity<?> signupcom(@RequestBody SignupComReqDto signupComReqDto){
        boolean status = false;
        try {
            status = userService.signupcom(signupComReqDto);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "Failed", status));
        }

        return ResponseEntity.ok().body(new CMRespDto<>(1, "Success", status));

    }

    //개인 로그인
    @PostMapping(LOGIN)
    public ResponseEntity<?> login(@RequestBody LoginReqDto loginReqDto) {
        LoginResDto loginResDto = null;

        try {
             loginResDto = userService.login(loginReqDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "Failed", null));
        }
        return ResponseEntity.ok().body(new CMRespDto<>(1, "Success", loginResDto));

    }

    //단체 로그인
    @PostMapping(LOGINCOM)
    public ResponseEntity<?> loginCom(@RequestBody LoginReqDto loginReqDto) {
        LoginComResDto loginComResDto = new LoginComResDto();
        
        try {
            loginComResDto = userService.loginCom(loginReqDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "Failed", null));

        }
        return ResponseEntity.ok().body(new CMRespDto<>(1, "Success", loginComResDto));

    }


    @GetMapping()
    public ResponseEntity<? super  GetLoginUserResDto> getLoginUser(@AuthenticationPrincipal String email){
        ResponseEntity<? super  GetLoginUserResDto> response = userService.getLoginUser(email);
        return response;
    }

    @GetMapping(LOGOUT)
    public String logout(@RequestParam String param) {
        return new String();
    }
}
