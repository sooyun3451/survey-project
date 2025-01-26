package com.surveyProject.project.service.survey.surveyform;

import com.surveyProject.project.web.dto.surveypage.surveyform.SurveyApplyReqDto;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.surveyProject.project.domain.survey.surveyform.SurveyForm;
import com.surveyProject.project.domain.survey.surveyform.SurveyFormOption;
import com.surveyProject.project.mapper.SurveyFormMapper;
import com.surveyProject.project.web.dto.SurveyForm.SurveyFormReqDto;
import com.surveyProject.project.web.dto.pay.PaymentDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SurveyFormServiceImpl implements SurveyFormService {
	
	private final SurveyFormMapper surveyFormMapper;

	@Override
	@Transactional
	public boolean saveSurveyForm(SurveyFormReqDto surveyFormReqDto) throws Exception {
		SurveyForm surveyForm = surveyFormReqDto.toEntity();
		int surveyFormSaved = surveyFormMapper.saveSurveyForm(surveyForm);

		if (surveyFormSaved > 0) {
			int surveyCode = surveyForm.getSurvey_code();
			surveyForm.setSurvey_code(surveyCode);
			surveyFormReqDto.setSurveyCode(surveyCode);

			int surveyStatusSaved = surveyFormMapper.saveSurveyStatus(surveyFormReqDto.toEntitySurveyStatus());
			
			List<SurveyForm> surveyForms = surveyFormReqDto.toEntityQuestions();
			int surveyFormQuestionSaved = 0;

			for (SurveyForm form : surveyForms) {
				form.setSurvey_code(surveyCode);
				surveyFormQuestionSaved += surveyFormMapper.saveSurveyFormQuestion(form);
			}

			List<SurveyFormOption> surveyFormOptions = surveyFormReqDto.toEntityOptions(surveyForms);
			int surveyFormOptionSaved = 0;

			for (SurveyFormOption option : surveyFormOptions) {
				surveyFormOptionSaved += surveyFormMapper.saveSurveyFormOption(option);
			}

			return surveyStatusSaved > 0 && surveyFormQuestionSaved > 0 && surveyFormOptionSaved > 0;
		}

		return false;
	}

	@Override
	@Transactional
	public boolean applySurveyForm(SurveyApplyReqDto surveyApplyReqDto) throws Exception {
		boolean result = false;
		String applicationClass = surveyApplyReqDto.getApplicationClass();
		if (applicationClass.equals("개인")) {
			return surveyFormMapper.applySurvey(surveyApplyReqDto.toEntityPerson()) > 0;
			
		} else {
			return result = surveyFormMapper.applySurvey(surveyApplyReqDto.toEntityPeople()) > 0;
		}
	}

	@Override
	public PaymentDto getPaymentInfo(int userCode) throws Exception {
		return surveyFormMapper.getPaymentInfo(userCode).toPaymentDto();
	}

}
