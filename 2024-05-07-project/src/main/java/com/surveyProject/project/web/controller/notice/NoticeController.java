package com.surveyProject.project.web.controller.notice;

import java.util.List;

import com.surveyProject.project.web.dto.NoticeRespDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.surveyProject.project.service.survey.notice.NoticeService;
import com.surveyProject.project.web.dto.CMRespDto;
import com.surveyProject.project.web.dto.notice.Createnotice;
import com.surveyProject.project.web.dto.notice.GetNoticeDetailRespDto;
import com.surveyProject.project.web.dto.notice.GetNoticeRespDto;
import com.surveyProject.project.web.dto.notice.GetUserRespDto;
import com.surveyProject.project.web.dto.notice.UpdateNoticeReqDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/survey")
@RequiredArgsConstructor
public class NoticeController{
	
	private final NoticeService noticeService;
	private static final String GETUSERROLE = "/notice/user/{userCode}";
	private static final String GETNOTICE = "/notice";
	private static final String GETNOTICEDETAIL = "/notice/detail/{noticeCode}";
	private static final String CREATENOTICE = "/notice/create";
	private static final String UPDATENOTICE = "/notice/detail/modify/{noticeCode}";
	private static final String DELETENOTICE = "/notice/delete/{noticeCode}";
	private static final String MAINNOTICE = "/main/notice";

	
	@GetMapping(GETUSERROLE)
	public ResponseEntity<?> getUserRole(@PathVariable int userCode) {
		
		GetUserRespDto getUserRespDto = null;
		
		try {
			getUserRespDto = noticeService.getUserRole(userCode);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1,"Failed", null));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1,"Success", getUserRespDto));
	}
	

	@GetMapping (GETNOTICE)
	public ResponseEntity<?> getnotice(@RequestParam int page, int contentCount) {
		List<GetNoticeRespDto> list = null;
		
		try {
			list = noticeService.getNoticeList(page, contentCount);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1,"Failed", null));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1,"Success", list));
	}
	
	
		@GetMapping(GETNOTICEDETAIL)
		public ResponseEntity<?> getnoticedetail(@PathVariable int noticeCode, @AuthenticationPrincipal String email) {
			
			GetNoticeDetailRespDto getNoticeDetailRespDto = null;
			
			try {
				getNoticeDetailRespDto = noticeService.getNoticeDetailRespDto(noticeCode);
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.internalServerError().body(new CMRespDto<>(-1,"Failed", null));
			}
			return ResponseEntity.ok().body(new CMRespDto<>(1,"Success", getNoticeDetailRespDto));
		}
		

	//notice 작성 
	@PostMapping(CREATENOTICE)
	public ResponseEntity<?> createnotice(@RequestBody Createnotice createnotice, @AuthenticationPrincipal String email){
		boolean status = false;
		
		try {
			status = noticeService.createNotice(createnotice);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "Failed", status));
			
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "Success", status));
	}
	
	
	//notice 수정
	@PutMapping(UPDATENOTICE)
	public ResponseEntity<?> updatenotice(@PathVariable int noticeCode, @RequestBody UpdateNoticeReqDto updateNoticeReqDto, @AuthenticationPrincipal String email) {
		boolean status = false;
		
		try {
			updateNoticeReqDto.setNoticeCode(noticeCode);
			status = noticeService.updateNotice(updateNoticeReqDto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new CMRespDto<>(-1,"Failed", status));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1,"Success",status));
	}
	
	
	//notice삭제 
	@DeleteMapping(DELETENOTICE)
	public ResponseEntity<?> deletenotice(@PathVariable int noticeCode, @AuthenticationPrincipal String email) {
		boolean status = false;
		
		try {
			status = noticeService.deleteNotice(noticeCode);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new CMRespDto<>(-1,"Failed", status));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1,"Success", status));
	}


	@GetMapping(MAINNOTICE)
	public ResponseEntity<?> mainNotice() {

		List<NoticeRespDto> notice = null;
		
		try {
			notice = noticeService.getNotice();			
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "Failed", null));
		}
		return ResponseEntity.ok().body(notice);
	}
}
