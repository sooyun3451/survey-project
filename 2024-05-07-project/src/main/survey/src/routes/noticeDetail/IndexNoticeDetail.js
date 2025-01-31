//2024.5.13 최소윤
//공지사항 detail
// useEffect완성 2024.6.3

import React, { useEffect, useState } from 'react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faHouse} from '@fortawesome/free-solid-svg-icons';
import Logo from './../../images/Logo.png';
import './IndexNoticeDetail.css'
import { Link, useNavigate, useParams } from 'react-router-dom';
import CustomerSurviceBox from '../../components/CustomerSurviceBox';
import axios from 'axios';
import { useCookies } from 'react-cookie';

export default function IndexNoticeDetail() { 

  const [noticeDetail, setNoticeDetail] = useState('');

  const {noticeCode} = useParams(); 
  
  const [role,setRole] = useState('');
  const [cookies] = useCookies(["token"]);
  const token = cookies.token;
  const userCode = localStorage.getItem("userCode");
  const navigate = useNavigate();

  useEffect(() => {
    axios.get(`/api/v1/survey/notice/detail/${noticeCode}`,{
      headers: {
          'Authorization': `Bearer ${token}`
      }
    })
    .then((response) => {
      setNoticeDetail(response.data.data);
    })
    .catch((error) => {
      console.log(error);
    })
  },[noticeCode]);

  const users = () => {
    if(token) {
      axios.get(`/api/v1/survey/notice/user/${userCode}`,{
      headers: {
          'Authorization': `Bearer ${token}`
      }
    })
    .then((response) => {
      console.log(response.data);
      setRole(response.data.data.userRole);
    })
    .catch((error) => {
      console.log(error);
    })
      }
    }

  useEffect(() => {
    users();
  })

  return (
    <div className='container'>
        <div className='surveyBox-notice-detail'>
          <div className='infoBox-notice-detail'>
          <FontAwesomeIcon icon={faHouse} size='lg' className='houseImg-notice-detail'/>
          </div>
          <div className='infoBox-notice-detail'><span>고객센터</span></div>
          <div className='infoBox-notice-detail'><span>공지사항</span></div>
        </div>
        <main>
          <div className='detail-left'>
            <CustomerSurviceBox />
            {
              role === 'ROLE_ADMIN' ? 
              <div className='notice-detail-buttons'>
              {
                <Link to={`/notice/detail/modify/${noticeCode}`}>
                  <button type='button' className='notice-update-button'> 작성 수정</button>
                </Link>
              }
              {
                <>
                  <button type='button' className='notice-delete-button' onClick={() => {
                    axios.delete(`/api/v1/survey/notice/delete/${noticeCode}`,{
                      headers: {
                          'Authorization': `Bearer ${token}`
                      }
                    })
                    .then(() => {
                      alert('삭제되었습니다');
                      navigate("/notice");
                    })
                    .catch((error) => {
                      console.log(error);
                    })
                  }}>삭제</button>
                </>
              }
            </div>
            : null
            }
              
          </div>
          <div className='notice-detail-main'>
            <div className='notice-detail-main-top'>
              <div className='notice-detail-main-top-left'>
              <span className='notice-detail-text'>공지사항</span>
              </div>
              <div className='notice-detail-main-top-right'>
                <img src={Logo} alt='Logo' />
                <span className='notice-detail-text'>설문할래</span>
              </div>
            </div>
            <div className='notice-detail-main-main'>
                <div className='notice-detail'>
                    <div className='notice-detail-title'>
                    <p>{noticeDetail.noticeTitle}</p>  
                    </div>
                    <div className='notice-detail-content'>
                        <p>{noticeDetail.noticeContent}</p>
                    </div>
                </div>
            </div>
          </div>
        </main>
    </div>
  )
}

