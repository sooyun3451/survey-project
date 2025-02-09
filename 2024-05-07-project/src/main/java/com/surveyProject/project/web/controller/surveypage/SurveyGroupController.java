package com.surveyProject.project.web.controller.surveypage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.surveyProject.project.service.survey.surveypage.SurveyService;
import com.surveyProject.project.web.dto.CMRespDto;
import com.surveyProject.project.web.dto.surveypage.SurveyStartRespDto;
import com.surveyProject.project.web.dto.surveypage.surveylist.SurveyListRespDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/survey/group/list")
@RequiredArgsConstructor
public class SurveyGroupController {

	private final SurveyService surveyService;
	private static final String GETSURVEYLIST = "/surveyList";
	private static final String CHECKSURVEYPASSWORDANDSURVEYSTART = "/{surveyCode}/start";
	private static final String GETSURVEYGROUPSTART = "/start/{surveyCode}";

	@GetMapping(GETSURVEYLIST)
	public ResponseEntity<?> getSurveyList() {
		List<SurveyListRespDto> surveyListRespDto = new ArrayList<>();
		try {
			surveyListRespDto = surveyService.getSurveyGroupList();
		} catch (Exception exception) {
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "Failed", null));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "Success", surveyListRespDto));

	}

	@GetMapping(GETSURVEYGROUPSTART)
	public ResponseEntity<?> getSurveyGroupStart(@PathVariable int surveyCode, @AuthenticationPrincipal String email) {

		SurveyStartRespDto surveyStartRespDto = null;

		try {
			surveyStartRespDto = surveyService.getSurveyGroupStart(surveyCode);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "Failed", null));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "Success", surveyStartRespDto));
	}

}
