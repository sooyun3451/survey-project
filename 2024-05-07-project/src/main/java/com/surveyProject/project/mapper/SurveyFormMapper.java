package com.surveyProject.project.mapper;


import org.apache.ibatis.annotations.Mapper;

import com.surveyProject.project.domain.survey.pay.Payment;
import com.surveyProject.project.domain.survey.survey.ApplyInformation;
import com.surveyProject.project.domain.survey.surveyform.SurveyForm;
import com.surveyProject.project.domain.survey.surveyform.SurveyFormOption;

@Mapper
public interface SurveyFormMapper {
	
	int applySurvey(ApplyInformation applyInformation);
		
	int saveSurveyForm(SurveyForm surveyForm);
	
	int saveSurveyFormQuestion(SurveyForm form);

	int saveSurveyFormOption(SurveyFormOption option);
 
	int saveSurveyStatus(SurveyForm surveyForm);
				
	int getLastInsertId();
	
	Payment getPaymentInfo(int userCode);
}
