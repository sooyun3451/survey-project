import React, { useEffect, useState } from "react";
import Search from "./search/Search";
import "./IndexMain.css";
import axios from "axios";
import { useCookies } from "react-cookie";

export default function IndexMain() {
  const [cookies] = useCookies(["token"]);
  const token = cookies.token;
  const [topSurvey, setTopSurvey] = useState([]);
  const [data, setData] = useState([]);

  const getNotice = () => {
    axios
      .get("/api/v1/survey/main/notice", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        setData(response.data);
      });
  };
  const getSurvey = () => {
    axios
      .get("/survey/personal/list/main/survey", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        setTopSurvey(response.data.data);
      });
  };

  useEffect(() => {
    getNotice();
    getSurvey();
  }, []);

  return (
    <>
      <div>
        <Search />
        <div className="indexMain-container">
          <div className="popular-rank">
            <div className="top-text">이번 주 인기 설문</div>
            <div className="bottom-text">
              {topSurvey.map((item, i) => (
                <a
                  href={`/survey/personal/list/start/${item.surveyCode}`}
                  className="bottom-text-content"
                  key={i}
                  onClick={(e) => {
                    if (!token) {
                      e.preventDefault();
                      alert("로그인 후 이용가능합니다.");
                    }
                  }}
                >
                  - {item.surveyTitle}
                </a>
              ))}
            </div>
          </div>
          <div className="main-notice">
            <div className="main-notice-top">공지사항</div>
            <div className="main-notice-bottom">
              {data.map((item, i) => (
                <a
                  href={`/notice/detail/${item.notice_code}`}
                  className="bottom-text-content"
                  key={i}
                  onClick={(e) => {
                    if (!token) {
                      e.preventDefault();
                      alert("로그인 후 이용가능합니다.");
                    }
                  }}
                >
                  - {item.notice_title}
                </a>
              ))}
              <div className="bottom-text-content">{} </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
