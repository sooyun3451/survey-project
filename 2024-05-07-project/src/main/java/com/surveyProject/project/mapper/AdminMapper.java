package com.surveyProject.project.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.surveyProject.project.domain.survey.admin.Company;
import com.surveyProject.project.domain.survey.admin.Survey;

@Mapper
public interface AdminMapper {
	List<Company> getCompanyListOfIndex(Map<String, Object> map);
	
	List<Survey> getSurveyListOfIndex(Map<String, Object> map);
	
	int updateCompanyApproval(int company_code);
	
	int deleteCompany(int company_code);
	
	int updateSurveyApproval(int survey_code);
	
	int deleteSurvey(int survey_code);

}
