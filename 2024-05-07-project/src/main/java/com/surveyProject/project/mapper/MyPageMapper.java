package com.surveyProject.project.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.surveyProject.project.domain.survey.mypage.ApplyList;
import com.surveyProject.project.domain.survey.mypage.UserInfo;
import com.surveyProject.project.domain.survey.mypage.UserSurveyList;
import com.surveyProject.project.domain.survey.user.User;

@Mapper
public interface MyPageMapper {
	UserInfo getUserInfoByUserCode(int user_code);
	
	List<UserSurveyList> getUserSurveyListByUserCode(int user_code);
	
	int ChangePoint(HashMap<String, Object> map);
	
	int DeleteUser(int user_code);
	
	int ChangeUserInfo(UserInfo userInfo);
	
	List<ApplyList> getApplyListByCode(HashMap<String, Object> map);
	
	User getUserInfoByUsercode(int user_code);
	
	int changePassword(User user);

}
