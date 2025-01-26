//2024.05.24 김예찬
package com.surveyProject.project.service.survey.surveypage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.surveyProject.project.web.dto.surveypage.surveyCompleteDto;
import com.surveyProject.project.web.dto.surveypage.SurveyStartRespDto;
import com.surveyProject.project.web.dto.surveypage.surveylist.*;
import org.springframework.stereotype.Service;

import com.surveyProject.project.domain.survey.survey.SurveyInformation;
import com.surveyProject.project.domain.survey.survey.SurveyList;
import com.surveyProject.project.domain.survey.survey.SurveyStartComplete;
import com.surveyProject.project.mapper.SurveyMapper;
import com.surveyProject.project.web.dto.surveypage.surveystartcomplete.SurveyStartCompleteDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {


	private final SurveyMapper surveyMapper;

	@Override
	public List<SurveyListRespDto> checkFilterAndGetSurveyListS(int page, int contentCount, String surveyClass, List<Integer> selectedCategories, List<String> selectedGenders, List<String> selectedAges) throws Exception {

		Map<String, Object> params = new HashMap<>();

		params.put("index", (page-1) * contentCount); 
		params.put("contentCount", contentCount);
		params.put("surveyClass", surveyClass); 

		params.put("categoryCode", selectedCategories != null ? selectedCategories : new ArrayList<>());  
		params.put("surveyTargetGender", selectedGenders != null ? selectedGenders : new ArrayList<>());  
		params.put("surveyTargetAge", selectedAges != null ? selectedAges : new ArrayList<>()); 


		System.out.println("params 테스트 = " + params);
		System.out.println(surveyMapper.checkFilterAndGetSurveyListR(params));
		List<SurveyList> surveyList = surveyMapper.checkFilterAndGetSurveyListR(params);


		List<SurveyListRespDto> surveyListRespDtos = new ArrayList<SurveyListRespDto>();

		surveyList.forEach(list -> {
			surveyListRespDtos.add(list.toSurveyListRespDto());
		});
		System.out.println("서비스 테스트 : " + surveyListRespDtos);
		return surveyListRespDtos;
	}



	@Override
	public List<SurveyStartCompleteDto> checkSurveyPasswordAndTargetAndSurveyStartS(String surveyPassword, int surveyCode, int userCode) throws Exception {

		Map<String, Object> map = new HashMap<>();

		map.put("surveyPassword", surveyPassword);
		map.put("surveyCode", surveyCode);
		map.put("userCode", userCode);

		System.out.println("map 테스트 = " + map);


		List<SurveyStartComplete> surveyStartComplete = surveyMapper.checkSurveyPasswordAndTargetAndSurveyStartR(map);

		System.out.println("surveyStartComplete 테스트 = " + surveyStartComplete);

		List<SurveyStartCompleteDto> surveyStartCompleteDto = new ArrayList<SurveyStartCompleteDto>();
		surveyStartComplete.forEach(list -> {
			surveyStartCompleteDto.add(list.toSurveyStartCompleteDto());
		});
		System.out.println("서비스 테스트 : " + surveyStartCompleteDto);
		return surveyStartCompleteDto;
	}

	//검색기능
	@Override
	public List<SurveySearchListResDto> getSearchList(String word) throws Exception {
		Map<String, Object> map = new HashMap<String,Object>();

		map.put("word", word);
		List<SurveyInformation> surveyInformationList = surveyMapper.getSearchList(map);
		List<SurveySearchListResDto> surveySearchListResDtoList = new ArrayList<>();
		surveyInformationList.forEach(surveyInformation -> {surveySearchListResDtoList.add(surveyInformation.toListDto());

		});


		System.out.println("서비스 : surveyInformationList = " + surveyInformationList);
		return surveySearchListResDtoList;
	}

	//설문조사 디테일
	@Override
	public SurveyDetailResDto getSurveyDetail(int surveyCode) throws Exception{
		Map<String, Object> map  = new HashMap<String,Object>();
		map.put("surveyCode", surveyCode);

		SurveyDetailResDto surveyDetailResDto  = new SurveyDetailResDto();
		surveyDetailResDto = surveyMapper.getSurveyDetailTitle(map).toDetailTitleDto();

		List<SurveyDetailQuestionResDto> questionResDtoList = new ArrayList<SurveyDetailQuestionResDto>();
		List<SurveyInformation> surveyInformationList = new ArrayList<SurveyInformation>();
		surveyInformationList = surveyMapper.getSurveyDetailQuestion(map);
		surveyInformationList.forEach(survey -> {
			SurveyDetailQuestionResDto questionResDto = new SurveyDetailQuestionResDto();
			questionResDto = survey.toDetailQuestionDto();
			List<SurveyDetailOptionResDto> optionResDtoList = new ArrayList<>();
			List<SurveyInformation> surveyInformationList2 = new ArrayList<>();
			try{
				surveyInformationList2 = surveyMapper.getSurveyDetailOption(survey.getQuestion_code());
				surveyInformationList2.forEach(survey2 -> {
					optionResDtoList.add(survey2.toDetailOptionDto());
				});
				questionResDto.setOptionResDtoList(optionResDtoList);
			}catch(Exception exception){
				exception.printStackTrace();
			}
			questionResDtoList.add(questionResDto);
			System.out.println("서비스 : "+questionResDtoList);
		});
		surveyDetailResDto.setQuestionResDtoList(questionResDtoList);
		
		return surveyDetailResDto;
	}


	// 메인페이지 인기 설문
	@Override
	public List<TopSurveyResDto> getTopSurvey() {
		List<TopSurveyResDto> topSurveyResDtoList = new ArrayList<>();

		topSurveyResDtoList = surveyMapper.getTopSurvey();
		return topSurveyResDtoList;
	}

	//개인 설문 전체 띄우기
	@Override
	public List<SurveyListRespDto> getSurveyList() throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<SurveyList> surveyLists = surveyMapper.getSurveyList(map);
		System.out.println("surveyLists" + surveyLists);
		List<SurveyListRespDto> surveyListRespDtoList = new ArrayList<>();
		surveyLists.forEach(surveyList -> surveyListRespDtoList.add(surveyList.toSurveyListRespDto()));
		System.out.println(surveyListRespDtoList);
		return surveyListRespDtoList;
	}
	
	//단체 설문 전체 띄우기
	@Override
	public List<SurveyListRespDto> getSurveyGroupList() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SurveyList> surveyLists= surveyMapper.getSurveyGroupList(map);
		List<SurveyListRespDto> surveyListRespDtoList = new ArrayList<>();      
		surveyLists.forEach(surveyList -> surveyListRespDtoList.add(surveyList.toSurveyListRespDto()));
		return surveyListRespDtoList;
	}



	@Override
	public surveyCompleteDto surveyComplete(int surveyCode) throws Exception { //지영

		surveyCompleteDto completeDto = null;

		completeDto = surveyMapper.SurveyComplete(surveyCode).toDto();

		return completeDto;
	}



	@Override
	public boolean updateSurveyComplete(int surveyCode, int userCode, int money) throws Exception {

		boolean status1 = false;
		boolean status2 = false;
		boolean status3 = false;

		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("user_code", userCode);
		map1.put("user_money", money);

		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("survey_code", surveyCode);
		map2.put("user_code", userCode);

		status1 = surveyMapper.updateSurveyCompleteSurvey(surveyCode) > 0;
		status2 = surveyMapper.updateSurveyCompleteUser(map1) > 0;
		status3 = surveyMapper.updateSurveyCompleteRespondent(map2) > 0;

		return status1 && status2 && status3;
	}



	//survey personal Start페이지(소윤)
	@Override
	public SurveyStartRespDto getSurveyStart(int surveyCode) throws Exception {
		return surveyMapper.getSurveyStart(surveyCode).toSurveyStartRespDto();
	}


	//survey group Start페이지(소윤)
	@Override
	public SurveyStartRespDto getSurveyGroupStart(int surveyCode) throws Exception {
		return surveyMapper.getSurveyGroupStart(surveyCode).toSurveyStartGroupRespDto();
	}

}