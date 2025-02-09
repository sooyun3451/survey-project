//2024.05.24 김예찬
//ex. 필터를 클릭하는 것에 대한 로직을 짜기, Dto -> Entity형식으로 바꿔주는 로직 
package com.surveyProject.project.service.survey.surveypage;

import java.util.List;
import java.util.Map;

import com.surveyProject.project.domain.survey.survey.SurveyInformation;
import com.surveyProject.project.domain.survey.survey.SurveyList;
import com.surveyProject.project.domain.survey.survey.SurveyStartComplete;
import com.surveyProject.project.web.dto.surveypage.surveyCompleteDto;
import com.surveyProject.project.web.dto.surveypage.SurveyAnswerReqDto;
import com.surveyProject.project.web.dto.surveypage.SurveyStartRespDto;
import com.surveyProject.project.web.dto.surveypage.surveylist.*;
import com.surveyProject.project.web.dto.surveypage.surveystartcomplete.SurveyStartCompleteDto;


public interface SurveyService {
	
	public List<SurveySearchListResDto> getSearchList(String word) throws Exception;

	public surveyCompleteDto surveyComplete(int surveyCode) throws Exception;
	
	public boolean updateSurveyComplete(int surveyCode, int userCode, int money) throws Exception;

	public SurveyDetailResDto getSurveyDetail(int surveyCode) throws Exception;
	
	public boolean createSurveyAnswer(int surveyCode, SurveyAnswerReqDto surveyAnswerReqDto) throws Exception;

	public List<TopSurveyResDto> getTopSurvey();

    List<SurveyListRespDto> getSurveyList() throws Exception;

    List<SurveyListRespDto> getSurveyGroupList() throws Exception;	
	
    public SurveyStartRespDto getSurveyStart(int surveyCode) throws Exception;
    
    public SurveyStartRespDto getSurveyGroupStart(int surveyCode) throws Exception;
	
}