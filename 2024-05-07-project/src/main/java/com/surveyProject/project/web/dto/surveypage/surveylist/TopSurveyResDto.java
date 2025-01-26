package com.surveyProject.project.web.dto.surveypage.surveylist;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TopSurveyResDto {

    private int surveyCode;
    private String surveyTitle;
}
