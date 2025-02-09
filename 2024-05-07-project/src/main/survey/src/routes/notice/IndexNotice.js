import React, { useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHouse } from "@fortawesome/free-solid-svg-icons";
import Logo from "./../../images/Logo.png";
import "./IndexNotice.css";
import { Link } from "react-router-dom";
import CustomerSurviceBox from "../../components/CustomerSurviceBox";
import axios from "axios";
import { useInView } from "react-intersection-observer";
import { useCookies } from "react-cookie";

export default function IndexNotice() {
  const [noticedata, setNoticeData] = useState([]);
  const [role, setRole] = useState("");
  const [ref, inView] = useInView();
  const [page, setPage] = useState(1);
  const [cookies] = useCookies(["token"]);
  const token = cookies.token;
  const userCode = localStorage.getItem("userCode");

  const notices = () => {
    axios
      .get("/api/v1/survey/notice", {
        params: { page, contentCount: 20 },
      })
      .then((response) => {
        setNoticeData((noticedata) => [...noticedata, ...response.data.data]);
        setPage(page + 1);
      })
      .catch((error) => {
        console.log(error);
      }, []);
  };

  const users = () => {
    if (token) {
      axios
        .get(`/api/v1/survey/notice/user/${userCode}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then((response) => {
          setRole(response.data.data.userRole);
        })
        .catch((error) => {
          console.log(error);
        });
    }
  };

  useEffect(() => {
    setNoticeData([]);
    setPage(1);
    users();
  }, []);

  useEffect(() => {
    if (inView) {
      notices();
    }
  }, [inView]);

  return (
    <div className="container">
      <div className="surveyBox-notice">
        <div className="infoBox-notice">
          <FontAwesomeIcon
            icon={faHouse}
            size="lg"
            className="houseImg-notice"
          />
        </div>
        <div className="infoBox-notice">
          <span>고객센터</span>
        </div>
        <div className="infoBox-notice">
          <span>공지사항</span>
        </div>
      </div>
      <main>
        <div className="notice-left">
          <CustomerSurviceBox />
          {role === "ROLE_ADMIN" && (
            <Link to={"/notice/create"}>
              <button className="notice-create-button">공지사항 등록</button>
            </Link>
          )}
        </div>
        <div className="notice-main">
          <div className="notice-main-top">
            <div className="notice-main-top-left">
              <span className="notice-main-text">공지사항</span>
            </div>
            <div className="notice-main-top-right">
              <img src={Logo} alt="Logo" />
              <span className="notice-main-text">설문할래</span>
            </div>
          </div>
          <div className="notice-main-under">
            <table className="notice-table">
              <thead>
                <tr>
                  <th className="notice-title">no</th>
                  <th className="notice-title">title</th>
                  <th className="notice-title">date</th>
                </tr>
              </thead>
              <tbody>
                {noticedata.map((notice, index) => {
                  return (
                    <tr key={index}>
                      <Link
                        to={`/notice/detail/${notice.noticeCode}`}
                        style={{
                          textDecoration: "none",
                          color: "black",
                          display: "contents",
                        }}
                      >
                        <td className="notice-content">{notice.noticeCode}</td>
                        <td className="notice-content">{notice.noticeTitle}</td>
                        <td className="notice-content">{notice.updatedate}</td>
                      </Link>
                    </tr>
                  );
                })}
              </tbody>
            </table>
            <div ref={ref}></div>
          </div>
        </div>
      </main>
    </div>
  );
}
