package com.surveyProject.project.service.survey.surveypage;

import java.util.List;
import com.surveyProject.project.web.dto.surveypage.surveyCompleteDto;
import com.surveyProject.project.web.dto.surveypage.SurveyAnswerReqDto;
import com.surveyProject.project.web.dto.surveypage.SurveyStartRespDto;
import com.surveyProject.project.web.dto.surveypage.surveylist.*;


public interface SurveyService {
	
	public List<SurveySearchListResDto> getSearchList(String word) throws Exception;

	public surveyCompleteDto surveyComplete(int surveyCode) throws Exception;
	
	public boolean updateSurveyComplete(int surveyCode, int userCode, int money) throws Exception;

	public SurveyDetailResDto getSurveyDetail(int surveyCode) throws Exception;
	
	public boolean createSurveyAnswer(SurveyAnswerReqDto surveyAnswerReqDto) throws Exception;

	public List<TopSurveyResDto> getTopSurvey();

    List<SurveyListRespDto> getSurveyList() throws Exception;

    List<SurveyListRespDto> getSurveyGroupList() throws Exception;	
	
    public SurveyStartRespDto getSurveyStart(int surveyCode) throws Exception;
    
    public SurveyStartRespDto getSurveyGroupStart(int surveyCode) throws Exception;

	
}