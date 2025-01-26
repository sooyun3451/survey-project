package com.surveyProject.project.web.controller.surveyForm;

import com.surveyProject.project.web.dto.CMRespDto;

import com.surveyProject.project.web.dto.surveypage.surveyform.SurveyApplyReqDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.surveyProject.project.service.survey.surveyform.SurveyFormService;
import com.surveyProject.project.web.dto.SurveyForm.SurveyFormReqDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/survey")
@RequiredArgsConstructor
public class SurveyFormController {
	
	private final SurveyFormService surveyFormService;
	private static final String SURVEYFORM = "/surveyform";
	private static final String APPLYFORM = "/applyform";

	@PostMapping(SURVEYFORM)
	public ResponseEntity<?> surveyform(@RequestBody SurveyFormReqDto surveyFormReqDto, @AuthenticationPrincipal String email) {
		
		boolean status = false;

		try {
			status = surveyFormService.saveSurveyForm(surveyFormReqDto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "Failed", status));
		}

		return ResponseEntity.ok().body(new CMRespDto<>(1, "Success", status));
	}

	// 설문신청 도입부
	@PostMapping(APPLYFORM)
	public ResponseEntity<?> applyForm(@RequestBody SurveyApplyReqDto surveyApplyReqDto, @AuthenticationPrincipal String email) {
		boolean status = false;
		try {
			status = surveyFormService.applySurveyForm(surveyApplyReqDto);
		} catch (Exception exception) {
			return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "Failed", status));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "Success", status));
	}
}
