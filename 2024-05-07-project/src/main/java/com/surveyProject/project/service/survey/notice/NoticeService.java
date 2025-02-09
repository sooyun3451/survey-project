package com.surveyProject.project.service.survey.notice;

import java.util.List;

import com.surveyProject.project.web.dto.NoticeRespDto;
import com.surveyProject.project.web.dto.notice.Createnotice;
import com.surveyProject.project.web.dto.notice.GetNoticeDetailRespDto;
import com.surveyProject.project.web.dto.notice.GetNoticeRespDto;
import com.surveyProject.project.web.dto.notice.GetUserRespDto;
import com.surveyProject.project.web.dto.notice.UpdateNoticeReqDto;

public interface NoticeService {
	
	public GetUserRespDto getUserRole(int userCode) throws Exception;
	
	public List<GetNoticeRespDto> getNoticeList(int page,int contentCount) throws Exception;
	
	public boolean createNotice(Createnotice createnotice) throws Exception;
	
	public GetNoticeDetailRespDto getNoticeDetailRespDto(int noticeCode) throws Exception;
	
	public boolean updateNotice(UpdateNoticeReqDto putNoticeReqDto) throws Exception;
	
	public boolean deleteNotice(int noticeCode) throws Exception;

	public List<NoticeRespDto> getNotice();

}
