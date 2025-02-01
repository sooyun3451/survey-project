package com.surveyProject.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.surveyProject.project.domain.survey.user.Company;
import com.surveyProject.project.domain.survey.user.User;

@Mapper
public interface UserMapper {
	int save(User user);
	
	int saveDtl(User user);
	
	 User findByEmail(String email);
	 
	 User findByUser(String email);
	 
	 User findBySnsIdAndJoinPath(@Param("sns_id") String sns_id, @Param("join_path") String join_path);
	 
	 Company findByCompany(String email);
	 
	 int savecom(Company company);
	 
	 Company findByComEmail(String email);
}
