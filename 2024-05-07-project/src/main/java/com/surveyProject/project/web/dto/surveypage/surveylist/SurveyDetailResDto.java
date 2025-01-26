package com.surveyProject.project.web.dto.surveypage.surveylist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SurveyDetailResDto {

    private int survey_code;
    private String survey_title;
    private String survey_content;
    private List<SurveyDetailQuestionResDto> questionResDtoList;
    //    private int question_code;
//    private String question_title;
//    private String detail_question;
//    private String option_content;
//    private String survey_answer_short;
//    private String survey_answer_long;
//    private String detail_answer;
//    private int question_essential;
//    private int select_type;




}
