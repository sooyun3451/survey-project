import React, { useEffect, useState } from 'react'
import './surveyPayment.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faHouse } from '@fortawesome/free-solid-svg-icons'
import Axios from 'axios';
import { useLocation } from 'react-router-dom';
import { useCookies } from 'react-cookie';

export default function IndexSurveyApplyComplete() {
  const [cookies] = useCookies(["token"]);
  const token = cookies.token;
  const {state} = useLocation();

  const loadReady = () => {
    Axios.post('/payment/ready', {
      surveyTitle : state.surveyTitle,
      participantMax : state.perMoney,
      surveyPerMoney : state.participantNum
    }, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
    .then((response) => {
      const nextRedirectPcUrl = response.data.next_redirect_pc_url;
      window.location.href = nextRedirectPcUrl;
      })
    .catch((err) => {
      console.log(err);
      })
  }


  return (
    <>
      <div className='surveyApplyComplete_container'>
        <div className='surveyBox_mypage'>
          <div className='infoBox_mypage'>
          <FontAwesomeIcon icon={faHouse} size='lg' className='houseImg_mypage'/>
          </div>
          <div className='infoBox_mypage'><span style={{
            fontSize: '20px'
          }}>결제페이지</span></div>
        </div>
        <div style={{
          width: '500px',
          height: '500px',
          border: '1px solid #0F3360',
          margin: 'auto',
          marginTop: '30px',
          borderRadius: '5px'
        }}>
          <div className='payment_info'>
            {/* 설문지 제목, 대상 몇명인지, 1인당 포인트, 결제 금액*/}
            <div className='payment_info_detail'>
              <div className='payment_info_detail_title'>
                설문지 제목
              </div>
              <div className='payment_info_detail_content'>
                {state.surveyTitle}
              </div>
            </div>
            <div className='payment_info_detail'>
            <div className='payment_info_detail_title'>
                대상 수
              </div>
              <div className='payment_info_detail_content'>
                {state.participantNum}명
              </div>
            </div>
            <div className='payment_info_detail'>
            <div className='payment_info_detail_title'>
                1인당 지급될 포인트
              </div>
              <div className='payment_info_detail_content'>
                {state.perMoney}P
              </div>
            </div>
            <div className='payment_info_detail'>
            <div className='payment_info_detail_title'>
                결제 금액
              </div>
              <div  className='payment_info_detail_content'>
                {state.participantNum * state.perMoney}원
              </div>
            </div>
            <div className='payment_info_detail'>
              <div className='payment_info_detail_title'>
                  총 결제 금액
                </div>
                <div  className='payment_info_detail_content' style={{ borderBottom: '1px solid #0F3360'}}>
                {state.participantNum * state.perMoney}원
                </div>
            </div>
          </div>
          <div className='payment_button'>
            <button onClick={loadReady}>결제하기</button>
          </div>
        </div>
      </div>
    </>
  )
}
