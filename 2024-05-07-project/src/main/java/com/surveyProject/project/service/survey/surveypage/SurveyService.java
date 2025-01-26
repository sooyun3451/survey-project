//2024.05.24 김예찬
//ex. 필터를 클릭하는 것에 대한 로직을 짜기, Dto -> Entity형식으로 바꿔주는 로직 
package com.surveyProject.project.service.survey.surveypage;

import java.util.List;
import java.util.Map;

import com.surveyProject.project.domain.survey.survey.SurveyInformation;
import com.surveyProject.project.domain.survey.survey.SurveyList;
import com.surveyProject.project.domain.survey.survey.SurveyStartComplete;
import com.surveyProject.project.web.dto.surveypage.surveyCompleteDto;
import com.surveyProject.project.web.dto.surveypage.SurveyStartRespDto;
import com.surveyProject.project.web.dto.surveypage.surveylist.*;
import com.surveyProject.project.web.dto.surveypage.surveystartcomplete.SurveyStartCompleteDto;


public interface SurveyService {
	
	public List<SurveySearchListResDto> getSearchList(String word) throws Exception;

	
	public List<SurveyListRespDto> checkFilterAndGetSurveyListS(int page, int contentCount, String surveyClass, List<Integer> selectedCategories, List<String> selectedGenders, List<String> selectedAges) throws Exception;

	public List<SurveyStartCompleteDto> checkSurveyPasswordAndTargetAndSurveyStartS(String surveyPassword, int surveyCode, int userCode) throws Exception;
 
	public surveyCompleteDto surveyComplete(int surveyCode) throws Exception; //지영
	
	public boolean updateSurveyComplete(int surveyCode, int userCode, int money) throws Exception;

	//설문조사 디테일 페이지
	public SurveyDetailResDto getSurveyDetail(int surveyCode) throws Exception;

	//메인페이지 설문조사 띄우기
	public List<TopSurveyResDto> getTopSurvey();

	//설문조사 전부 띄우기
    List<SurveyListRespDto> getSurveyList() throws Exception;

    List<SurveyListRespDto> getSurveyGroupList() throws Exception;	
	
    //survey personal Start페이지(소윤)
    public SurveyStartRespDto getSurveyStart(int surveyCode) throws Exception;
    
    //survey group Start페이지(소윤)
    public SurveyStartRespDto getSurveyGroupStart(int surveyCode) throws Exception;
	
}