package com.surveyProject.project.service.survey;

import com.surveyProject.project.domain.survey.user.*;
import com.surveyProject.project.mapper.UserMapper;
import com.surveyProject.project.provider.JwtProvider;
import com.surveyProject.project.web.dto.auth.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public boolean signup(SignupReqDto signupReqDto) throws Exception {
        String userEmail = signupReqDto.getEmail();
        String password = signupReqDto.getPassword();
        String name = signupReqDto.getUserName();
        String birth = signupReqDto.getBirth();
        String gender = signupReqDto.getGender();
        String joinPath = signupReqDto.getJoinPath();
        String snsId = signupReqDto.getSnsId();
        
        
        if (userEmail == null || userEmail.isEmpty()) {
            return false;
        }
        
        if (password == null || password.isEmpty()) {
            return false;
        }
    	
//        if (password.length() < 8 || !password.matches(".*[A-Z.*]") || !password.matches(".*\\d.*")) {
//            return false;
//        }
        
        if (name == null || name.isEmpty()) {
            return false;
        }
        
        if(birth == null || birth.isEmpty()) {
        	return false;
        }
        
        if (gender != null && !gender.matches("male|female")) {
            return false;
        }
        
        if (joinPath == null || joinPath.isEmpty()) {
            return false; 
        }
        
        try {
        	String encodedPassword = bCryptPasswordEncoder.encode(password);
        	
        	User user = User.builder()
        			.user_name(name)
        			.user_email(userEmail)
        			.user_password(encodedPassword)
        			.user_birth(birth)
        			.user_gender(gender)
        			.role(signupReqDto.getRole())
        			.create_date(signupReqDto.getCreateDate())
        			.update_date(signupReqDto.getUpdateDate())
        			.join_path(joinPath)
        			.sns_id(snsId)
        			.build();
        	
        	userMapper.save(user);
        	userMapper.saveDtl(user);
        	
        	return true;
        	
        } catch (Exception e) {
        	e.printStackTrace();
        	return false;
        }   
    }

    @Override
    public boolean signupcom(SignupComReqDto signupComReqDto) throws Exception {
        String email = signupComReqDto.getEmail();
        String password = signupComReqDto.getPassword();
        String companyName = signupComReqDto.getCompanyName();
        
        if(email == null || email.isEmpty()) {
        	return false;
        }
        
        if(password == null || password.isEmpty()) {
        	return false;
        }
        
        if(companyName == null || companyName.isEmpty()) {
        	return false;
        }
        
        try {
        	String encodedPassword = bCryptPasswordEncoder.encode(password);
        	
        	Company company = Company.builder()
        			.company_name(companyName)
        			.company_email(email)
        			.company_password(encodedPassword)
        			.role(signupComReqDto.getRole())
        			.join_status(signupComReqDto.getJoinStatus())
        			.create_date(signupComReqDto.getCreateDate())
        			.update_date(signupComReqDto.getUpdateDate())
        			.build();
        	
        	userMapper.savecom(company);
        	
        	return true;
        } catch(Exception e) {
        	e.printStackTrace();
        	return false;
        }
    }

    //회원 중복 검증 메서드
    @Override
    public void validateDuplicateUser(User user) throws Exception {
        User findUser = userMapper.findByEmail(user.getUser_email());
        if(findUser !=null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Override
    public LoginResDto login(LoginReqDto loginReqDto) throws Exception{
        String email = loginReqDto.getEmail();
        String password = loginReqDto.getPassword();

        if(email == null || email.isEmpty()) {
        	return null;
        }
        
        if(password == null || password.isEmpty()) {
        	return null;
        }
        
        try {
        	
        	User user = userMapper.findByUser(email);
        	
        	if(user == null) {
        		return null;
        	}
        	
            if (!bCryptPasswordEncoder.matches(password, user.getUser_password())) {
                return null; // 비밀번호가 일치하지 않으면 null 반환
            }
        	
        	String token = jwtProvider.generateJwtToken(email);
        	int exprTime = jwtProvider.getExpiration();
        	
        	LoginResDto loginResDto = LoginResDto.builder()
        			.user_code(user.getUser_code())
        			.user_email(user.getUser_email())
        			.user_name(user.getUser_name())
        			.token(token)
        			.role(user.getRole())
        			.expirationTime(exprTime)
        			.build();
        	
        	return loginResDto;     
        	
        } catch(Exception e) {
        	e.printStackTrace();
        	return null;
        }
    }

    @Override
    public LoginComResDto loginCom(LoginReqDto loginReqDto) throws Exception {
        String email = loginReqDto.getEmail();
        String password = loginReqDto.getPassword();

        if(email == null || email.isEmpty()) {
        	return null;
        }
        
        if(password == null || password.isEmpty()) {
        	return null; 
        	
    }
        
        try {
        	Company company = userMapper.findByCompany(email);
        	
        	if(company == null) {
        		return null;
        	}
        	
        	if(!bCryptPasswordEncoder.matches(password, company.getCompany_password())) {
        		return null;
        	}
        	String token = jwtProvider.generateJwtToken(email);
        	int exprTime = jwtProvider.getExpiration();
        	
        	LoginComResDto loginComResDto = LoginComResDto.builder()
        			.companyEmail(company.getCompany_email())
        			.companyName(company.getCompany_name())
        			.companyCode(company.getCompany_code())
        			.role(company.getRole())
        			.token(token)
        			.expirationTime(exprTime)
        			.build();
        	
        	return loginComResDto;
        	
        }catch(Exception e) {
        	e.printStackTrace();
        	return null;
        }
   }


    @Override
    public ResponseEntity<? super GetLoginUserResDto> getLoginUser(String email) {
        User user = new User();
        try {
             user = userMapper.findByEmail(email);
             if(user == null) return GetLoginUserResDto.notExistUser();

        }catch (Exception exception){
            exception.printStackTrace();
            return null;
        }
        return GetLoginUserResDto.success(user);
    }
}
