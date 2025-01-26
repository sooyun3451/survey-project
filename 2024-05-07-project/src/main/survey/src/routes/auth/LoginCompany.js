import React, { useState } from "react";
import "./Login.css";
import google from "../../images/google.png";
import facebook from "../../images/facebook.png";
import naver from "../../images/naver.png";
import logo2 from "../../images/logo2.png";
import IndexSignup from "./indexSignup";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { useCookies } from "react-cookie";
import useAuthStore from "../../store/auth.store";
// 날짜 : 2024.05.08
// 작성자 : 심규창

export default function LoginCompany() {
  let [email, setEmail] = useState("");
  let [pwd, setPwd] = useState("");
  const navigate = useNavigate();
  const [, setCookies] = useCookies(["token"]);
  const { login } = useAuthStore();

  const onEmail = (e) => {
    console.log(e.target.value);
    setEmail(e.target.value);
  };

  const onPwd = (e) => {
    console.log(e.target.value);
    setPwd(e.target.value);
  };

  const submit = async () => {
    try {
      const response = await axios.post(
        "http://localhost:8000/api/v1/auth/logincom",
        {
          email: email,
          password: pwd,
        }
      );
      if (response.data) {
        signInSuccessresponse(response.data.data);
      }
    } catch (e) {
      console.error(e);
      alert("로그인 실패");
    }
  };

  const setToken = (token, exprTime) => {
    const expires = new Date(Date.now() + exprTime);
    setCookies("token", token, { path: "/", expires });
  };

  const signInSuccessresponse = (data) => {
    if (data) {
      console.log("signInSuccess");
      const { token, expirationTime } = data;
      setToken(token, expirationTime);
      localStorage.setItem("companyName", data.company_name);

      login({
        token: token,
      });
      navigate("/");
    } else {
      alert("실패");
    }
  };

  return (
    <div className="signin_container">
      <div className="signin_left">
        <img className="logo_image" src={logo2}></img>
        {/* <h1 className='logo_title'>설문할래</h1> */}
      </div>
      <div className="signin_right">
        <div className="signin_box">
          <img src={logo2}></img>
          <span>로그인 할래?</span>
        </div>
        <div className="signup-linkbox">
          <Link to={"/auth/login"}>
            <button className="signup-link-button">개인</button>
          </Link>
          <Link to={"/auth/login/com"}>
            <button className="signup-link-button">단체</button>
          </Link>
        </div>
        <div className="social-box">
          <input
            className="signin-input"
            type="email"
            value={email}
            placeholder="이메일"
            onChange={onEmail}
          />
          <input
            className="signin-input"
            type="password"
            value={pwd}
            placeholder="비밀번호 "
            onChange={onPwd}
          />

          <button className="login-btn" type="submit" onClick={submit}>
            로그인{" "}
          </button>

          <div className="google-box">
            <img className="login_image" src={google} alt="google" />
          </div>
          <div className="naver-box">
            <img className="login_image" src={naver} alt="naver" />
          </div>
          <div className="facebook-box">
            <img className="login_image" src={facebook} alt="facebook" />
          </div>
          <a className="signin-tag">
            <Link to="/auth/signup/person">회원가입 할래?</Link>
          </a>

          {/* <div className='google-box'><img className='login_image' src={'../google.png'}/><span id='login-text'>google로 계속하기</span></div>
          <div className='naver-box'><img className='login_image' src={'../naver.png'}/><span id='login-text'>naver로 계속하기</span></div>
          <div className='facebook-box'><img className='login_image' src={'../facebook.png'}/><span id='login-text'>facebook로 계속하기</span></div> */}
        </div>
      </div>
      <img className="logo_image-bottom" src={logo2}></img>
    </div>
  );
}
