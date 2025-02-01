import React from 'react'
import { useLocation } from 'react-router-dom';
import kakao from "../../images/kakao.webp";
import naver from "../../images/naver.png";

export default function ReadySignUp() {
  const onSnsButtonClickHandler = (sns) => {
    window.location.href = `${"http://localhost:8000/api/v1/auth/sns-sign-in/"}${sns}`;
  };

  return (
    <div>
      <div className='signup-btn'>설문할래로 회원가입</div>
    <div className="sns-box">
    <div className='kakao' onClick={() => onSnsButtonClickHandler("kakao")}><img src={kakao} alt="kakao"/><span>카카오로 회원가입</span></div>
    <div className='naver' onClick={() => onSnsButtonClickHandler("naver")}><img src={naver} alt="naver"/><span>네이버로 회원가입</span></div>
  </div>
    </div>
  )
}
