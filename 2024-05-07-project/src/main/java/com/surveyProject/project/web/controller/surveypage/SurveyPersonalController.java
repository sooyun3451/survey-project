package com.surveyProject.project.web.controller.surveypage;

import com.surveyProject.project.web.dto.surveypage.surveyCompleteDto;
import com.surveyProject.project.web.dto.surveypage.SurveyAnswerReqDto;
import com.surveyProject.project.web.dto.surveypage.SurveyStartRespDto;
import com.surveyProject.project.web.dto.surveypage.surveylist.*;
import org.springframework.web.bind.annotation.RestController;
import com.surveyProject.project.service.survey.surveypage.SurveyService;
import com.surveyProject.project.web.dto.CMRespDto;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/survey/personal/list")
@RequiredArgsConstructor
public class SurveyPersonalController {

	private final SurveyService surveyService;
	private static final String GETSURVEYLIST = "/main/surveyList";
	private static final String GETSURVEYSTART = "/start/{surveyCode}";
	private static final String SURVEYPERSONCOMPLETE = "/{surveyCode}/complete";
	private static final String UPDATESURVEYCOMPLETE = "/{surveyCode}/complete/{userCode}/{money}";
	private static final String GETSURVEYDETAIL = "/details/{surveyCode}";
	private static final String SURVEYANSWER = "/answer/{surveyCode}";
	private static final String SEARCHLIST = "/searchList";
	private static final String MAINSURVEY = "/main/survey";

	@GetMapping(GETSURVEYLIST)
	public ResponseEntity<?> getSurveyList() {
		List<SurveyListRespDto> surveyListRespDtoList = new ArrayList<>();
		try {
			surveyListRespDtoList = surveyService.getSurveyList();
		} catch (Exception exception) {
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "Failed", null));
		}

		return ResponseEntity.ok().body(new CMRespDto<>(1, "Success", surveyListRespDtoList));
	}

	@GetMapping(GETSURVEYSTART)
	public ResponseEntity<?> getSurveyStart(@PathVariable int surveyCode, @AuthenticationPrincipal String email) {
		SurveyStartRespDto surveyStartRespDto = null;

		try {
			surveyStartRespDto = surveyService.getSurveyStart(surveyCode);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "Failed", null));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "Success", surveyStartRespDto));
	}

	@GetMapping(SURVEYPERSONCOMPLETE)
	public ResponseEntity<?> surveyPersonComplete(@PathVariable int surveyCode, @AuthenticationPrincipal String email) {

		surveyCompleteDto completeDto = null;

		try {
			completeDto = surveyService.surveyComplete(surveyCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "Failed", null));
		}

		return ResponseEntity.ok().body(new CMRespDto<>(1, "Success", completeDto));
	}

	@PutMapping(UPDATESURVEYCOMPLETE)
	public ResponseEntity<?> updateSurveyComplete(@PathVariable int surveyCode, @PathVariable int userCode,
			@PathVariable int money, @AuthenticationPrincipal String email) {

		boolean status = false;

		try {
			status = surveyService.updateSurveyComplete(surveyCode, userCode, money);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "Failed", status));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "Success", status));
	}

	@GetMapping(GETSURVEYDETAIL)
	public ResponseEntity<?> getSurveyDetail(@PathVariable int surveyCode, @AuthenticationPrincipal String email) {
		SurveyDetailResDto surveyDetailResDtoList = null;

		try {
			surveyDetailResDtoList = surveyService.getSurveyDetail(surveyCode);
		} catch (Exception exception) {
			return ResponseEntity.internalServerError()
					.body(new CMRespDto<>(-1, "Failed", null));
		}

		return ResponseEntity.ok().body(new CMRespDto<>(1, "Success", surveyDetailResDtoList));
	}
	
	@PostMapping(SURVEYANSWER)
	public ResponseEntity<?> createSurveyAnswer(@RequestBody SurveyAnswerReqDto surveyAnswerReqDto, @AuthenticationPrincipal String email) {
		boolean status = false;
		try {
			System.out.println(surveyAnswerReqDto);
			status = surveyService.createSurveyAnswer(surveyAnswerReqDto);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "Failed", status));
		}
		return ResponseEntity.ok(new CMRespDto<>(1, "Success", status));
	}

	@GetMapping(SEARCHLIST)
	public ResponseEntity<?> searchList(@RequestParam String word) {
		List<SurveySearchListResDto> surveyInformationList = null;
		try {

			surveyInformationList = surveyService.getSearchList(word);

		} catch (Exception exception) {
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "Failed", null));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "Success", surveyInformationList));
	}

	@GetMapping(MAINSURVEY)
	public ResponseEntity<?> mainSurvey() {
		List<TopSurveyResDto> topSurveyResDtoList = new ArrayList<>();
		try {
			topSurveyResDtoList = surveyService.getTopSurvey();			
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "Failed", null));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "Success", topSurveyResDtoList));
	}

}