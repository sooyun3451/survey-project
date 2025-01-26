//2024.05.24 김예찬
package com.surveyProject.project.web.controller.surveypage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.surveyProject.project.service.survey.surveypage.SurveyService;
import com.surveyProject.project.web.dto.CMRespDto;
import com.surveyProject.project.web.dto.surveypage.SurveyStartRespDto;
import com.surveyProject.project.web.dto.surveypage.surveylist.SurveyListRespDto;
import com.surveyProject.project.web.dto.surveypage.surveystartcomplete.SurveyStartCompleteDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/survey/group/list")
@RequiredArgsConstructor
public class SurveyGroupController {

	private final SurveyService surveyService;
	private static final String GETSURVEYLIST = "/surveyList";
	private static final String CHECKSURVEYPASSWORDANDSURVEYSTART = "/{surveyCode}/start";
	private static final String GETSURVEYGROUPSTART = "/start/{surveyCode}";

	// 설문 전체 띄우기
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

	// 단체 설문조사 리스트들이 모여있는 페이지
	@GetMapping()
	public ResponseEntity<?> checkFilterAndGetSurveyList(@RequestParam int page, @RequestParam int contentCount,
			@RequestParam String surveyClass, @RequestParam(required = false) List<Integer> selectedCategories,
			@RequestParam(required = false) List<String> selectedGenders,
			@RequestParam(required = false) List<String> selectedAges) {

		List<SurveyListRespDto> surveyListRespDto = null;

		try {
			surveyListRespDto = surveyService.checkFilterAndGetSurveyListS(page, contentCount, surveyClass,
					selectedCategories, selectedGenders, selectedAges);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "Failed", null));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "Success", surveyListRespDto));
	}

	// 단체 설문조사 중 하나를 클릭했을 때 시작페이지
	@PostMapping(CHECKSURVEYPASSWORDANDSURVEYSTART)
	public ResponseEntity<?> checkSurveyPasswordAndSurveyStart(@RequestParam(required = false) String surveyPassword,
			@PathVariable int surveyCode, @PathVariable int userCode, @AuthenticationPrincipal String email) {

		List<SurveyStartCompleteDto> surveyStartCompleteDto = null;

		try {
			surveyStartCompleteDto = surveyService.checkSurveyPasswordAndTargetAndSurveyStartS(surveyPassword,
					surveyCode, userCode);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "Failed", null));
		}

		return ResponseEntity.ok().body(new CMRespDto<>(1, "Success", surveyStartCompleteDto));
	}

	// surveyStart
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
