package com.surveyProject.project.domain.survey.user;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Mapper
public interface UserRepository {
    public int save(User user) throws Exception;
    
    public int saveDtl(User user) throws Exception;

    public User findByEmail(String email) throws UsernameNotFoundException;
    
    public boolean findByUser(String email);

    public int savecom(Company company) throws Exception;

    public Company findByComEmail(String email) throws UsernameNotFoundException;
    //유저정보 묶음을 리턴
}
