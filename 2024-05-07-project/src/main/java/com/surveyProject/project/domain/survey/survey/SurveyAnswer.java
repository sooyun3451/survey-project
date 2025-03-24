package com.surveyProject.project.domain.survey.survey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SurveyAnswer {
	private int answer_code;
	private int survey_code;
	private int question_code;
	private int option_code;
	private String short_answer;
	private String duplication_answer;
	private String subjective_answer;
	private String detail_answer;
}
