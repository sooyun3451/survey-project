import React from "react";
import "./ReadySignUp.css";
import kakao from "../../images/kakao.webp";
import naver from "../../images/naver.png";
import { Link } from "react-router-dom";

export default function ReadySignUp() {
  const onSnsButtonClickHandler = (sns) => {
    window.location.href = `${"http://localhost:8000/api/v1/auth/sns-sign-in/"}${sns}`;
  };

  return (
    <div className="signUpContainer">
      <div className="signup-box">
        <Link to="/auth/signup/company">
          <div className="ready-signup-btn">
            <span>기업으로 회원가입</span>
          </div>
        </Link>
        <Link to="/auth/signup/person">
          <div className="ready-signup-btn">
            <span>개인으로 회원가입</span>
          </div>
        </Link>
        <div className="kakao" onClick={() => onSnsButtonClickHandler("kakao")}>
          <img src={kakao} alt="kakao" />
          <span>카카오로 회원가입(개인)</span>
        </div>
        <div className="naver" onClick={() => onSnsButtonClickHandler("naver")}>
          <img src={naver} alt="naver" />
          <span>네이버로 회원가입(개인)</span>
        </div>
      </div>
    </div>
  );
}
