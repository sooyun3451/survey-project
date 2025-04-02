package com.surveyProject.project.web.dto.surveypage;

import java.util.ArrayList;
import java.util.List;

import com.surveyProject.project.domain.survey.survey.SurveyAnswer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyAnswerReqDto {
	
	private int surveyCode;
	private List<Integer> questionCode;
	private List<Integer> optionCode;
	private List<String> detailAnswer;
	
	public List<SurveyAnswer> toEntity() {
		List<SurveyAnswer> answers = new ArrayList<>();
		
		for(int i = 0; i < questionCode.size(); i++) {			
			SurveyAnswer surveyAnswer = SurveyAnswer.builder()
					.survey_code(surveyCode)
					.question_code(questionCode.get(i))
					.option_code(optionCode.get(i) != null ? optionCode.get(i) : 0)
					.detail_answer(detailAnswer.get(i) != null ? detailAnswer.get(i) : null)
					.build();
			
			answers.add(surveyAnswer);
		}
		return answers;
	}
}
