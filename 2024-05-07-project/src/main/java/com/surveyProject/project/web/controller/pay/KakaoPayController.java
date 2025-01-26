package com.surveyProject.project.web.controller.pay;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.surveyProject.project.exception.BusinessLogicException;
import com.surveyProject.project.exception.ExceptionCode;
import com.surveyProject.project.service.survey.pay.KakaoPayService;
import com.surveyProject.project.service.survey.surveyform.SurveyFormService;
import com.surveyProject.project.web.dto.CMRespDto;
import com.surveyProject.project.web.dto.pay.KakaoApproveResponse;
import com.surveyProject.project.web.dto.pay.KakaoCancelResponse;
import com.surveyProject.project.web.dto.pay.KakaoReadyResponse;
import com.surveyProject.project.web.dto.pay.PaymentDto;

import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/payment")
@RequiredArgsConstructor
public class KakaoPayController {

private final KakaoPayService kakaoPayService;
private final SurveyFormService surveyFormService;

	// 정보가져오기 
	@GetMapping("/{surveyCode}")
	public ResponseEntity<?> getPaymentInfo(@PathVariable int surveyCode, @AuthenticationPrincipal String email) {
		PaymentDto paymentDto = null;
		
		try {
			paymentDto = surveyFormService.getPaymentInfo(surveyCode);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "Failed", null));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "Success", paymentDto));
	}
    

	// 결제요청 
	@PostMapping("/ready")
	public KakaoReadyResponse readyToKakaoPay(@RequestBody PaymentDto paymentDto, @AuthenticationPrincipal String email) {
	    return kakaoPayService.kakaoPayReady(paymentDto);
	}
	

	// 결제성공 
	@GetMapping("/success")
	public ResponseEntity<?> afterPayRequest(@RequestParam("pg_token") String pgToken) {
	
	    KakaoApproveResponse kakaoApprove = kakaoPayService.ApproveResponse(pgToken);
	
	    return new ResponseEntity<>(kakaoApprove, HttpStatus.OK);
	}
	

	// 결제 진행 중 취소 
	@GetMapping("/cancel")
	public void cancel(@AuthenticationPrincipal String email) {
	    throw new BusinessLogicException(ExceptionCode.PAY_CANCEL);
	}
	
	// 결제 실패 
	@GetMapping("/fail")
	public void fail(@AuthenticationPrincipal String email) {
	    throw new BusinessLogicException(ExceptionCode.PAY_FAILED);
	}
	
	// 환불 
    @PostMapping("/refund")
    public ResponseEntity<?> refund(@AuthenticationPrincipal String email) {

        KakaoCancelResponse kakaoCancelResponse = kakaoPayService.kakaoCancel();

        return new ResponseEntity<>(kakaoCancelResponse, HttpStatus.OK);
    }
}

