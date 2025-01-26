package com.surveyProject.project.web.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;

import com.surveyProject.project.service.survey.admin.AdminService;
import com.surveyProject.project.web.dto.CMRespDto;
import com.surveyProject.project.web.dto.admin.ReadCompanyDto;
import com.surveyProject.project.web.dto.admin.ReadSurveyDto;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AdminpageController {

	private final AdminService adminService;
	private static final String GETCOMPANYSIGNUPLIST = "/adminpage/company/{page}/{contentCount}";
	private static final String GETSURVEYLIST = "/adminpage/survey/{page}/{contentCount}";
	private static final String COMPANYAPPROVAL = "/adminpage/company/{companyCode}";
	private static final String DELETECOMPANY = "/adminpage/company/{companyCode}";
	private static final String UPDATESURVEYSTATUS = "/adminpage/survey/{surveyCode}";
	private static final String DELETESURVEY = "/adminpage/survey/{surveyCode}";

	// 회사 회원가입 목록 불러오는 get 요청
	@GetMapping(GETCOMPANYSIGNUPLIST)
	public ResponseEntity<?> getCompanySignupList(@PathVariable int page, @PathVariable int contentCount, @AuthenticationPrincipal String email) {
		List<ReadCompanyDto> CompanyList = null;

		try {
			CompanyList = adminService.getCompanyList(page, contentCount);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "Failed", null));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "Success", CompanyList));
	}

	// 신청된 설문조사 목록 불어오는 get 요청
	@GetMapping(GETSURVEYLIST)
	public ResponseEntity<?> getSurveyList(@PathVariable int page, @PathVariable int contentCount, @AuthenticationPrincipal String email) {

		List<ReadSurveyDto> readSurveyDto = null;

		try {
			readSurveyDto = adminService.getSurveyList(page, contentCount);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "Failed", null));
		}

		return ResponseEntity.ok().body(new CMRespDto<>(1, "Success", readSurveyDto));
	}

	// 회사 회원가입 처리 수정하는 update 요청 (승인, 삭제)
	@PutMapping(COMPANYAPPROVAL)
	public ResponseEntity<?> companyApproval(@PathVariable int companyCode, @AuthenticationPrincipal String email) {

		boolean status = false;

		try {
			status = adminService.updateCompanyApproval(companyCode);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "Failed", status));
		}

		return ResponseEntity.ok().body(new CMRespDto<>(1, "Success", status));
	}

	@DeleteMapping(DELETECOMPANY)
	public ResponseEntity<?> deleteCompany(@PathVariable int companyCode, @AuthenticationPrincipal String email) {

		boolean status = false;

		try {
			status = adminService.deleteCompany(companyCode);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "Failed", status));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "Success", status));
	}

	// 설문신청 상태 변경 update 요청 (승인, 삭제)
	@PutMapping(UPDATESURVEYSTATUS)
	public ResponseEntity<?> updateSurveyStatus(@PathVariable int surveyCode, @AuthenticationPrincipal String email) {

		boolean status = false;

		try {
			status = adminService.updateSurveyApproval(surveyCode);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "Failed", status));
		}

		return ResponseEntity.ok().body(new CMRespDto<>(1, "Success", status));
	}

	@DeleteMapping(DELETESURVEY)
	public ResponseEntity<?> deleteSurvey(@PathVariable int surveyCode, @AuthenticationPrincipal String email) {

		boolean status = false;

		try {
			status = adminService.deleteSurvey(surveyCode);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "Failed", status));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "Success", status));
	}
}
