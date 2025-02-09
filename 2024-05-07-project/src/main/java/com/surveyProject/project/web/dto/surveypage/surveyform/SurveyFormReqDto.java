package com.surveyProject.project.web.dto.surveypage.surveyform;

import java.time.LocalDateTime;

import com.surveyProject.project.domain.survey.survey.SurveyForm;

import lombok.Data;

@Data
public class SurveyFormReqDto {

	private int surveyCode;
	private int userCode;

	private int participantCount;
	private LocalDateTime createDate; 
	private LocalDateTime updateDate; 
	private int questionCode;
	private int selectType; 
	private int optionCode;
	private String optionContent;
	
	private int answerCode;
	private String surveyAnswer;
	private String surveyAnswerShort;
	private String surveyAnswerLong;
	private String detailQuestion;
	private String detailAnswer;

	
	
	public SurveyForm toEntity() {
		return SurveyForm.builder()
				.survey_code(surveyCode)
				.user_code(userCode)
				.participant_count(participantCount)
				.create_date(createDate)
				.update_date(updateDate)
				.question_code(questionCode)
				.select_type(selectType)
				.option_code(optionCode)
				.option_content(optionContent)
				.answer_code(answerCode)
				.survey_answer(surveyAnswer)
				.survey_answer_short(surveyAnswerShort)
				.survey_answer_long(surveyAnswerLong)
				.detail_question(detailQuestion)
				.detail_answer(detailAnswer)
				.build();
	
	
	}
}

