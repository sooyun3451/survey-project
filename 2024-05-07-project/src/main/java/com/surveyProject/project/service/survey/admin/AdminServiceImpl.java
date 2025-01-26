package com.surveyProject.project.service.survey.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.surveyProject.project.domain.survey.admin.Company;
import com.surveyProject.project.domain.survey.admin.Survey;
import com.surveyProject.project.mapper.AdminMapper;
import com.surveyProject.project.web.dto.admin.ReadCompanyDto;
import com.surveyProject.project.web.dto.admin.ReadSurveyDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{
	
	private final AdminMapper adminMapper;

	@Override
	public List<ReadCompanyDto> getCompanyList(int page, int contentCount) throws Exception {

		HashMap<String, Object> pagecode = new HashMap<String, Object>();
		pagecode.put("index", (page - 1)*contentCount);
		pagecode.put("count", contentCount);
		
		List<Company> companyList = adminMapper.getCompanyListOfIndex(pagecode);
		List<ReadCompanyDto> companyListRespDto = new ArrayList<ReadCompanyDto>();
		companyList.forEach(company -> {
			companyListRespDto.add(company.toCompanyDto());
		});
		
		return companyListRespDto;
	}

	@Override
	public List<ReadSurveyDto> getSurveyList(int page, int contentCount) throws Exception {
		
		HashMap<String, Object> pagecode = new HashMap<String, Object>();
		pagecode.put("index", (page - 1)*contentCount);
		pagecode.put("count", contentCount);
		
		List<Survey> surveyList = adminMapper.getSurveyListOfIndex(pagecode);
		List<ReadSurveyDto> readSurveyDto = new ArrayList<ReadSurveyDto>();
		surveyList.forEach(survey -> {
			readSurveyDto.add(survey.toSurveyDto());
		});
		
		return readSurveyDto;
	}

	@Override
	public boolean updateCompanyApproval(int companyCode) throws Exception {
		return adminMapper.updateCompanyApproval(companyCode) > 0;
	}

	@Override
	public boolean deleteCompany(int companyCode) throws Exception {
		return adminMapper.deleteCompany(companyCode) > 0;
	}

	@Override
	public boolean updateSurveyApproval(int surveyCode) throws Exception {
		return adminMapper.updateSurveyApproval(surveyCode) > 0;
	}

	@Override
	public boolean deleteSurvey(int surveyCode) throws Exception {
		return adminMapper.deleteSurvey(surveyCode) > 0;
	}

}
