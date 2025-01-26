package com.surveyProject.project.domain.survey.surveyform;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyFormOption {
	
	private int question_code;
	private String option_content;

}
