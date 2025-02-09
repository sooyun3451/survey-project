import React, { useEffect, useState } from "react";
import "./IndexDetailModify.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHouse } from "@fortawesome/free-solid-svg-icons";
import Logo from "./../../images/Logo.png";
import { Link, useParams } from "react-router-dom";
import CustomerSurviceBox from "../../components/CustomerSurviceBox";
import axios from "axios";
import { useCookies } from "react-cookie";

export default function IndexDetailModify() {
  const [noticeDetail, setNoticeDetail] = useState({
    noticeTitle: "",
    noticeContent: "",
  });
  const [noticeTitle, setNoticeTitle] = useState("");
  const [noticeContent, setNoticeContent] = useState("");
  const [cookies] = useCookies(["token"]);
  const token = cookies.token;
  const userCode = localStorage.getItem("userCode");

  const { noticeCode } = useParams(); 

  const saveNoticeTitle = (e) => {
    setNoticeTitle(e.target.value);
  };

  const saveNoticeContent = (e) => {
    setNoticeContent(e.target.value);
  };

  useEffect(() => {
    axios
      .get(`/api/v1/survey/notice/detail/${noticeCode}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        const data = response.data.data;
        setNoticeDetail(data);
        setNoticeTitle(data.noticeTitle);
        setNoticeContent(data.noticeContent);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [noticeCode]);

  const handlerSubmit = () => {
    axios
      .put(
        `/api/v1/survey/notice/detail/modify/${noticeCode}`,
        {
          userCode: userCode,
          noticeTitle: noticeTitle || noticeDetail.noticeTitle,
          noticeContent: noticeContent || noticeDetail.noticeContent,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      )
      .then(() => {
        alert("수정되었습니다");
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <div className="container">
      <div className="surveyBox-modify">
        <div className="infoBox-modify">
          <FontAwesomeIcon
            icon={faHouse}
            size="lg"
            className="houseImg-modify"
          />
        </div>
        <div className="infoBox-modify">
          <span>고객센터</span>
        </div>
        <div className="infoBox-modify">
          <span>공지사항</span>
        </div>
      </div>
      <main>
        <CustomerSurviceBox />
        <div className="notice-detail-modify-main">
          <div className="notice-detail-modify-main-top">
            <div className="notice-detail-modify-main-top-left">
              <span className="notice-detail-modify-text">공지사항</span>
            </div>
            <div className="notice-detail-modify-main-top-right">
              <img src={Logo} alt="Logo" />
              <span className="notice-detail-modify-text">설문할래</span>
            </div>
          </div>
          <div className="notice-detail-modify-main-main">
            <div className="notice-detail-modify">
              <div className="notice-detail-modify-title">
                <input
                  type="text"
                  defaultValue={noticeTitle}
                  onChange={saveNoticeTitle}
                ></input>
              </div>
              <div className="notice-detail-modify-content">
                <textarea
                  defaultValue={noticeContent}
                  onChange={saveNoticeContent}
                ></textarea>
              </div>
            </div>
            {
              <Link to={"/notice"}>
                <button
                  type="button"
                  className="complete-button"
                  onClick={handlerSubmit}
                >
                  수정 완료
                </button>
              </Link>
            }
          </div>
        </div>
      </main>
    </div>
  );
}
