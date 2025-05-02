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
	private List<List<Integer>> optionCode;
	private List<String> subjectiveAnswer;
	private List<String> detailAnswer;
	
	public List<SurveyAnswer> toEntity() {
		List<SurveyAnswer> answers = new ArrayList<>();
		
		for(int i = 0; i < questionCode.size(); i++) {			
			List<Integer> options = optionCode.get(i);
			if(options.isEmpty()) {
				answers.add(SurveyAnswer.builder() 
						.survey_code(surveyCode)
						.question_code(questionCode.get(i))
						.option_code(0)
						.subjective_answer(subjectiveAnswer.get(i))
						.detail_answer(detailAnswer.get(i))
						.build());
			}else {
				for(int option: options) {
					answers.add(SurveyAnswer.builder()
							.survey_code(surveyCode)
							.question_code(questionCode.get(i))
							.option_code(option)
							.subjective_answer(subjectiveAnswer.get(i))
							.detail_answer(detailAnswer.get(i))
							.build());
				}
			}
		}
		return answers;
	}
}
