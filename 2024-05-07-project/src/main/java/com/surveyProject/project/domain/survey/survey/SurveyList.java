package com.surveyProject.project.domain.survey.survey;



import com.surveyProject.project.web.dto.surveypage.SurveyStartRespDto;
import com.surveyProject.project.web.dto.surveypage.surveylist.SurveyListReqDto;
import com.surveyProject.project.web.dto.surveypage.surveylist.SurveyListRespDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor 
@Builder																																														
//설문조사 개인 + 단체 목록 페이지
public class SurveyList {
	
	private int user_code; //개인이 설문을 신청했을 경우 그 개인의 정보를 가져오기 위해 필요, 여기서 user의 정보는 사용자가 아닌 신청자의 user정보	
	private String user_birth;
	private int user_gender;
	private int company_code;

	
	private int category_code; //AUTO_INCREMENT
	private String category_name;
	
	private String survey_target_gender;
	private String survey_target_age;
	private int survey_per_money;
	private int participant_max;
	private String survey_period_start;
	private String survey_period_stop;

	
	private String survey_class; //설문 조사 대상이 개인인지 단체인지
	private String survey_password; // 단체 설문조사페이지일 경우
	private int survey_code; //AUTO_INCREMENT
	private String survey_title;
	private String survey_content; // 설문조사 시작 페이지에서 나오는 "제목 없는 설문지" 바로 밑부분에 나오는 설문조사에 대한 설명란에 적을 설명내용
	private String survey_present_img;
	private int participant_count;
	private int survey_status; // AUTO_INCREMENT, 설문의 상태 -> 완료, 기간만료, 대상자 수 만료, 승인대기중을 나타내는 코드
	private String status_content; // 설문 상태의 내용 -> 완료, 기간만료, 대상자 수 만료, 승인대기중
	
	
	
	public SurveyListReqDto toSurveyListReqDto() {
		return SurveyListReqDto.builder()
				.userCode(user_code)
				.surveyPassword(survey_password)
				.surveyCode(survey_code)
				.build();
	}
	
	public SurveyListRespDto toSurveyListRespDto() {
		return SurveyListRespDto.builder()
				.surveyCode(survey_code)
				.categoryName(category_name)
				.categoryCode(category_code)
				.surveyTargetAge(survey_target_age)
				.surveyTargetGender(survey_target_gender)
				.surveyTitle(survey_title)
				.surveyPerMoney(survey_per_money)
				.surveyPeriodStart(survey_period_start)
				.surveyPeriodStop(survey_period_stop)
				.build();
	}
	
	public SurveyStartRespDto toSurveyStartRespDto() {
		return SurveyStartRespDto.builder()
				.surveyTitle(survey_title)
				.surveyPeriodStart(survey_period_start)
				.surveyPeriodStop(survey_period_stop)
				.surveyPerMoney(survey_per_money)
				.build();
	}
	
	public SurveyStartRespDto toSurveyStartGroupRespDto() {
		return SurveyStartRespDto.builder()
				.surveyTitle(survey_title)
				.surveyPeriodStart(survey_period_start)
				.surveyPeriodStop(survey_period_stop)
				.build();
	}


}
 