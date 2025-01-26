package com.surveyProject.project.web.dto.SurveyForm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.surveyProject.project.domain.survey.surveyform.SurveyForm;
import com.surveyProject.project.domain.survey.surveyform.SurveyFormOption;

import lombok.Data;

@Data
public class SurveyFormReqDto {
	private int surveyCode;
	private int surveyStatus;
	private String statusContent;
	private String surveyTitle;
	private String surveyInfo;
	private List<String> questionTitle;
	private List<List<Map<String, String>>> questionOptions;
	private List<Integer> questionSelectOption;
	private List<Boolean> questionEssential;
	private List<String> detailQuestion;
	
	public SurveyForm toEntitySurveyStatus() {
		return SurveyForm.builder()
				.survey_status(surveyStatus)
				.survey_code(surveyCode)
				.status_content(statusContent)
				.build();
	}
	
	public SurveyForm toEntity() {
		return SurveyForm.builder()
				.survey_title(surveyTitle)
				.survey_info(surveyInfo)
				.build();
	}
	
	public List<SurveyForm> toEntityQuestions() {
	    List<SurveyForm> surveyForms = new ArrayList<>();

	    for (int i = 0; i < questionTitle.size(); i++) {
	        int selectType = questionSelectOption.get(i);

	        SurveyForm surveyForm = SurveyForm.builder()
	                .question_title(questionTitle.get(i))
	                .question_select_option(questionSelectOption.get(i))
	                .select_type(selectType)
	                .question_essential(questionEssential.get(i) ? 1 : 0)
	                .detail_question(detailQuestion.get(i) != null ? detailQuestion.get(i) : null)
	                .build();

	        surveyForms.add(surveyForm);
	    }
	    return surveyForms;
	}

	public List<SurveyFormOption> toEntityOptions(List<SurveyForm> surveyForms) {
	    List<SurveyFormOption> surveyFormOptions = new ArrayList<>();

	    for (int i = 0; i < surveyForms.size(); i++) {
	        SurveyForm surveyForm = surveyForms.get(i);
	        List<String> options = questionOptions.get(i).stream()
	                .map(optionMap -> optionMap.get("option"))
	                .collect(Collectors.toList());

	        for (String option : options) {
	            SurveyFormOption surveyFormOption = new SurveyFormOption();
	            surveyFormOption.setQuestion_code(surveyForm.getQuestion_code());
	            surveyFormOption.setOption_content(option);
	            surveyFormOptions.add(surveyFormOption);
	        }
	    }
	    return surveyFormOptions;
	}
}

