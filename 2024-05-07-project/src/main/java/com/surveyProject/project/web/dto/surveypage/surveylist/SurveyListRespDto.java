package com.surveyProject.project.web.dto.surveypage.surveylist;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SurveyListRespDto {
	private int surveyCode;
	private String surveyTitle;
	private int categoryCode;
	private String categoryName;
	private String surveyTargetGender;
	private String surveyTargetAge;
//	private String surveyPresentImg;
	private int surveyPerMoney;
	private String surveyPeriodStart;
	private String surveyPeriodStop;
}