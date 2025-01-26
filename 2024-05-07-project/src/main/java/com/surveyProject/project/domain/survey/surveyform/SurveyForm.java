package com.surveyProject.project.domain.survey.surveyform;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyForm {
	private int survey_code;
	private int survey_status;
	private String status_content;
	private String survey_title;
	private String survey_info;
	private int question_code;
	private String question_title;
	private int question_select_option;
	private int question_essential;
	private String detail_question;
	private int select_type;
	private List<SurveyFormOption> question_options;
}

