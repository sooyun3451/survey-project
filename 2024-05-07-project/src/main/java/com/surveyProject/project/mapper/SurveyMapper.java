package com.surveyProject.project.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.surveyProject.project.domain.survey.survey.SurveyAnswer;
import com.surveyProject.project.domain.survey.survey.SurveyComplete;
import com.surveyProject.project.domain.survey.survey.SurveyInformation;
import com.surveyProject.project.domain.survey.survey.SurveyList;
import com.surveyProject.project.web.dto.surveypage.surveylist.TopSurveyResDto;

@Mapper
public interface SurveyMapper {

	SurveyComplete SurveyComplete(int survey_code);

	int updateSurveyCompleteSurvey(int survey_code);

	int updateSurveyCompleteUser(Map<String, Object> map);

	int updateSurveyCompleteRespondent(Map<String, Object> map);

	boolean updateSurveyStatus(int survey_code);

	List<SurveyInformation> getSearchList(Map<String, Object> map);

	SurveyInformation getSurveyDetailTitle(Map<String, Object> map);

	List<SurveyInformation> getSurveyDetailQuestion(Map<String, Object> map);

	List<SurveyInformation> getSurveyDetailOption(int question_code);

	int saveSurveyAnswer(@Param("answers") List<SurveyAnswer> answers);
	
	List<TopSurveyResDto> getTopSurvey();

	List<SurveyList> getSurveyList(Map<String, Object> map);

	List<SurveyList> getCategorySurveyList(int categoryCode);

	List<SurveyList> getSurveyGroupList(Map<String, Object> map);

	SurveyList getSurveyStart(int survey_code);

	SurveyList getSurveyGroupStart(int survey_code);

}
