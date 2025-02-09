package com.surveyProject.project.service.survey.notice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.surveyProject.project.web.dto.NoticeRespDto;
import org.springframework.stereotype.Service;

import com.surveyProject.project.domain.survey.notice.Notice;
import com.surveyProject.project.mapper.NoticeMapper;
import com.surveyProject.project.web.dto.notice.Createnotice;
import com.surveyProject.project.web.dto.notice.GetNoticeDetailRespDto;
import com.surveyProject.project.web.dto.notice.GetNoticeRespDto;
import com.surveyProject.project.web.dto.notice.GetUserRespDto;
import com.surveyProject.project.web.dto.notice.UpdateNoticeReqDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{
	
	private final NoticeMapper noticeMapper;
	
	@Override
	public GetUserRespDto getUserRole(int userCode) throws Exception {
		return noticeMapper.getuser(userCode).toUserDto();
	}
	
	@Override
	public List<GetNoticeRespDto> getNoticeList(int page, int contentCount) throws Exception {
		Map<String, Object> map = new HashMap<String,Object>();
		
		map.put("index", (page - 1) * contentCount);
		map.put("count", contentCount);
		
	    List<Notice> noticelist = noticeMapper.getnotice(map);
	    
	    List<GetNoticeRespDto> noticeListRespDtos = new ArrayList<GetNoticeRespDto>();
	    
	    noticelist.forEach(notice -> {
	    	noticeListRespDtos.add(notice.toListDto());
	    });
		return noticeListRespDtos;
	}
	
	@Override
	public boolean createNotice(Createnotice createnotice) throws Exception {
		return noticeMapper.savenotice(createnotice.toEntity()) > 0;
	}

	@Override
	public GetNoticeDetailRespDto getNoticeDetailRespDto(int noticeCode) throws Exception {
		return noticeMapper.noticedetail(noticeCode).toDto();
	}

	@Override
	public boolean updateNotice(UpdateNoticeReqDto updateNoticeReqDto) throws Exception {
		return noticeMapper.updatenotice(updateNoticeReqDto.toEntity()) > 0;
	}

	@Override
	public boolean deleteNotice(int noticeCode) throws Exception {
		return noticeMapper.deletenotice(noticeCode) > 0;
	}

	@Override
	public List<NoticeRespDto> getNotice() {
		return noticeMapper.getNoticeMain();
	}


	
}
