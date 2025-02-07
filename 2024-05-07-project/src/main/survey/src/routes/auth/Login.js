import React, { useEffect, useState } from "react";
import "./Login.css";
import kakao from "../../images/kakao.webp";
import naver from "../../images/naver.png";
import logo2 from "../../images/logo2.png";
import IndexSignup from "./indexSignup";
import { Link, useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import { useCookies } from "react-cookie";
import useAuthStore from "../../store/auth.store";
// 날짜 : 2024.05.08
// 작성자 : 심규창
export default function Login() {
  const [email, setEmail] = useState("");
  const [pwd, setPwd] = useState("");
  const [activeButton, setActiveButton] = useState("personal");
  const navigate = useNavigate();
  const [, setCookies, removeCookies] = useCookies(["token"]);
  const { login, logout } = useAuthStore();

  const onEmail = (e) => {
    setEmail(e.target.value);
  };

  const onPwd = (e) => {
    setPwd(e.target.value);
  };

  const onSnsButtonClickHandler = (sns) => {
    window.location.href = `${"http://localhost:8000/api/v1/auth/sns-sign-in/"}${sns}`;
  };

  const submit = async () => {
    try {
      const response = await axios.post(
        "http://localhost:8000/api/v1/auth/login",
        {
          email: email,
          password: pwd,
        }
      );
      if (response.data) {
        console.log(response.data.data);
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
      console.log(data);
      const { token, expirationTime } = data;
      setToken(token, expirationTime);
      localStorage.setItem("userName", data.user_name);
      localStorage.setItem("userCode", data.user_code);

      login({
        token: token,
      });
      setAutoLogout(expirationTime);
      navigate("/");
    } else {
      alert("이메일 또는 비밀번호가 틀렸습니다.");
    }
  };

  const setAutoLogout = (expirationTime) => {
    setTimeout(() => {
      logoutUser();
    }, expirationTime);
  };

  const logoutUser = () => {
    removeCookies("token", { path: "/" });
    localStorage.removeItem("userName");
    localStorage.removeItem("userCode");
    logout();
    navigate("/auth/login");
  };

  return (
    <div className="signin_container">
      <div className="signin_left">
        <img className="logo_image" src={logo2} alt="logoImg"></img>
        {/* <h1 className='logo_title'>설문할래</h1> */}
      </div>
      <div className="signin_right">
        <div className="signin_box">
          <img src={logo2} alt="logoImg"></img>
          <span>로그인 할래?</span>
        </div>
        <div className="signup-linkbox">
          <Link to={"/auth/login"}>
            <button
              className="login-link-button"
              style={{
                backgroundColor:
                  activeButton === "personal" ? "#0F3360" : "white",
                color: activeButton === "personal" ? "white" : "black",
              }}
              onClick={() => setActiveButton("personal")}
            >
              개인
            </button>
          </Link>
          <Link to={"/auth/login/com"}>
            <button
              className="login-link-button"
              style={{
                backgroundColor: activeButton === "group" ? "#0F3360" : "white",
                color: activeButton === "group" ? "white" : "black",
              }}
              onClick={() => setActiveButton("group")}
            >
              단체
            </button>
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
            placeholder="비밀번호 "
            onChange={onPwd}
          />
          <button className="login-btn" type="submit" onClick={submit}>
            로그인{" "}
          </button>
          <Link to="/auth/ready/signup">
            <div className="signup-button">
              <span>회원가입</span>
            </div>
          </Link>
          <div className="sns-box">
            <div
              className="kakao"
              onClick={() => onSnsButtonClickHandler("kakao")}
            >
              <img src={kakao} alt="kakao" />
              <span>카카오로 로그인</span>
            </div>
            <div
              className="naver"
              onClick={() => onSnsButtonClickHandler("naver")}
            >
              <img src={naver} alt="naver" />
              <span>네이버로 로그인</span>
            </div>
          </div>
        </div>
      </div>
      <img className="logo_image-bottom" src={logo2} alt="logoImg"></img>
    </div>
  );
}
