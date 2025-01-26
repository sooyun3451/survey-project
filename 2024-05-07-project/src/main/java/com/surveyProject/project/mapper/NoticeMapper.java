package com.surveyProject.project.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.surveyProject.project.domain.survey.notice.Notice;
import com.surveyProject.project.web.dto.NoticeRespDto;

@Mapper
public interface NoticeMapper {
	Notice getuser(int userCode);
	
	List<Notice> getnotice(Map<String, Object> map);
	
	int savenotice(Notice notice);
	
	Notice noticedetail(int notice_code);
	
	int updatenotice(Notice notice);
	
	int deletenotice(int notice_code);
	
	List<NoticeRespDto> getNoticeMain();

}
