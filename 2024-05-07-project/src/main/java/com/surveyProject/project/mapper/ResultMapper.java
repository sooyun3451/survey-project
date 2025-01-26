package com.surveyProject.project.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.surveyProject.project.domain.survey.result.Answers;
import com.surveyProject.project.domain.survey.result.Questions;
import com.surveyProject.project.domain.survey.result.Result;

@Mapper
public interface ResultMapper {
	
	Result getResultByCode(Map<String, Object> map);
	
	List<Questions> getQuestionsByCode(Map<String, Object> map);
	
	List<Answers> getAnswersByCode(int question_code);

}
