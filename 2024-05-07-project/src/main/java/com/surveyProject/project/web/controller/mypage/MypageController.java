package com.surveyProject.project.web.controller.mypage;

import org.springframework.web.bind.annotation.RestController;

import com.surveyProject.project.service.survey.mypage.MypageService;
import com.surveyProject.project.web.dto.CMRespDto;
import com.surveyProject.project.web.dto.mypage.ChangePasswordDto;
import com.surveyProject.project.web.dto.mypage.GetApplyListRespDto;
import com.surveyProject.project.web.dto.mypage.ReadUserSurveyList;
import com.surveyProject.project.web.dto.mypage.ReadUserinfo;
import com.surveyProject.project.web.dto.mypage.UserModifyReqDto;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
public class MypageController {
	
	private final MypageService mypageService;
	private static final String GETUSERINFOMODIFY = "/mypage/modify/{userCode}";
	private static final String GETUSERINFO = "/mypage/{userCode}";
	private static final String GETUSERSURVEYLIST = "/mypage";
	private static final String CHANGEPOINT = "/mypage/{userCode}/{userMoney}";
	private static final String DELETEUSER = "/mypage/{userCode}";
	private static final String PUTMETHODNAME = "/mypage/modify/{userCode}";
	private static final String GETAPPLYLIST = "/mypage/getApplyList";
	private static final String CHANGEPASSWORD = "/mypage/password";
	
	
	@GetMapping(value = {GETUSERINFOMODIFY, GETUSERINFO})
	public ResponseEntity<?> getUserInfo(@PathVariable int userCode, @AuthenticationPrincipal String email) {
		
		ReadUserinfo readUserinfo = null;
		
		try {
			readUserinfo = mypageService.getUserInfo(userCode);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "Failed", null));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "Success", readUserinfo));
	}
	
	@GetMapping(GETUSERSURVEYLIST)
	public ResponseEntity<?> getUserSurveyList(@RequestParam int userCode, @AuthenticationPrincipal String email) {
		
		List<ReadUserSurveyList> readUserSurveyList = null;
		
		try {
			readUserSurveyList = mypageService.getUserSurveyInfo(userCode);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "Failed", readUserSurveyList));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "success", readUserSurveyList));
	}
	
	@PutMapping(CHANGEPOINT)
	public ResponseEntity<?> ChangePoint(@PathVariable int userCode, @PathVariable int userMoney, @AuthenticationPrincipal String email) {

		boolean status = false;
		
		try {
			status = mypageService.ChangePoint(userCode, userMoney);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "Failed", status));
		}
		
		return ResponseEntity.ok().body(new CMRespDto<>(1, "success", status));
	}
	
	@DeleteMapping(DELETEUSER)
	public ResponseEntity<?> DeleteUser(@PathVariable int userCode, @AuthenticationPrincipal String email) {
		
		boolean status = false;
		
		try {
			status = mypageService.DeleteUser(userCode);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "Failed", status));
		}
		
		return ResponseEntity.ok().body(new CMRespDto<>(1, "success", status));
	}

	@PutMapping(PUTMETHODNAME)
	public ResponseEntity<?> putMethodName(@PathVariable int userCode, @RequestBody UserModifyReqDto modifyReqDto, @AuthenticationPrincipal String email) {
		System.out.println("controller" + modifyReqDto);
		modifyReqDto.setUserCode(userCode);
		
		boolean status = false;   
		
		try {
			status = mypageService.ChangeUserInfo(modifyReqDto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "Failed", status));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "success", status));
	}
	
	@GetMapping(GETAPPLYLIST)
	public ResponseEntity<?> getApplyList(@RequestParam int code, @RequestParam String type) {
		
		List<GetApplyListRespDto> getApplyListRespDtos = null;
		try {
			getApplyListRespDtos = mypageService.getApplyList(code, type);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "Failed", null));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "success", getApplyListRespDtos));
	}
	
	@PutMapping(CHANGEPASSWORD)
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
		
		boolean status = false;
		
		try {
			status = mypageService.changePassword(changePasswordDto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "Failed", status));
		}
		
		return ResponseEntity.ok().body(new CMRespDto<>(1, "success", status));
	}
	
}
