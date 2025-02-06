import React, { useEffect, useState } from "react";
import "./applyForm.css";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import { startOfDay } from "date-fns";
import { useCookies } from "react-cookie";

export default function IndexSurveyApplyPerson() {
  const navigate = useNavigate();
  const [name, setName] = useState("");
  const [surveyClass, setSurveyClass] = useState("");
  const [pwd, setPwd] = useState("");
  const [participantNum, setParticipantNum] = useState("");
  const [gender, setGender] = useState("");
  const [age, setAge] = useState();
  const [perMoney, setPerMoeny] = useState("");
  const [periodStart, setPeriodStart] = useState("");
  const [periodStop, setPeriodStop] = useState("");
  const [category, setCategory] = useState("");
  const [file, setFile] = useState("");

  const onAgree = (e) => {
    if (e.target.id === "no") alert("개인정보 수집에 동의해주셔야합니다.");
  };

  const onSurveyClass = (e) => {
    setSurveyClass(e.target.value);
  };

  const onPwd = (e) => {
    const newPwd = e.target.value;
    setPwd(newPwd);
  };

  const onGender = (e) => {
    const newGender = e.target.value;
    setGender(newGender);
  };

  const onAge = (e) => {
    setAge(e.target.value);
  };

  const onMoney = (e) => {
    const newMoney = e.target.value;
    newMoney < 0 ? alert("적립금을 다시 입력해주세요.") : setPerMoeny(newMoney);
  };

  const onStart = (e) => {
    setPeriodStart(e.target.value);
  };

  const onStop = (e) => {
    setPeriodStop(e.target.value);
  };

  useEffect(() => {
  },[periodStart]);

  useEffect(() => {
  },[periodStop]);

  const onCategory = (e) => {
    let newCategory = e.target.value;
    setCategory(newCategory);
  };

  const onParticipant = (e) => {
    const newParticipant = e.target.value;
    newParticipant < 0
      ? alert("최대 참여인원을 다시 입력해주세요.")
      : setParticipantNum(newParticipant);
  };
  const [cookies] = useCookies(["token"]);
  const token = cookies.token;
  const applyClass = localStorage.getItem("applyClass");
  const userCode = localStorage.getItem("userCode");

  //첨부파일
  let formData = new FormData();
  useEffect(() => {
    console.log(formData);
  }, [formData]);

  const onFile = (e) => {
    formData.append("file", e.target.files[0]);
    axios
      .post("http://localhost:8000/api/v1/survey/file", formData)
      .then((response) => {
        alert("파일 업로드에 성공했습니다.");
      })
      .catch((error) => {
        alert("파일 업로드에 실패했습니다.");
      });
  };

  function onClickHandler() {
    console.log("categort", category);
    navigate(
      "/survey/surveyform",
      {
        state: {
          applyClass,
          surveyClass,
          pwd,
          userCode,
          category,
          gender,
          age,
          perMoney,
          participantNum,
          periodStart,
          periodStop,
        },
      },
      {
        headers: {
          Authorization: `Bearer ${token}`, 
        },
      }
    );
  }

  return (
    <>
      <div className="container-form">
        <div className="agreeBox">
          <div
            style={{
              marginTop: "10px",
            }}
          >
            <h3
              style={{
                fontSize: "20px",
              }}
            >
              개인정보 수집 및 이용 동의
              <span
                style={{
                  fontSize: "14px",
                  color: "red",
                }}
              >
                (필수)
              </span>
            </h3>
          </div>
          <div className="radioCheck">
            <input
              type="radio"
              id="yes"
              name="agree"
              checked="checked"
              onClick={onAgree}
            />
            <label for="yes">예</label>
            <input type="radio" id="no" name="agree" onClick={onAgree} />
            <label for="no">아니요</label>
          </div>
        </div>
        <div className="applyInfo">
          <h4
            style={{
              color: "red",
              fontSize: "18px",
            }}
          >
            * 설문조사 첨부파일 내 필수 입력사항
          </h4>
          <h5>1. 이 조사를 실시하는 이유</h5>
          <h5>2. 이 조사를 통해 무엇에 이용하고자 하는지</h5>
          <h5>3. 알아야 할 사항은 무엇인지</h5>
          <h5>4. 조사 결과를 받기 원하는 시기</h5>
        </div>
        <div className="inputInfo">
          <table className="apply_table">
            <tr className="apply_tr">
              <th className="apply_th">성명</th>
              <td colSpan={3} className="apply_td">
                <input
                  type="text"
                  value={localStorage.getItem("userName" || "company_Name")}
                  style={{
                    border: "none",
                    outline: "none",
                  }}
                />
              </td>
            </tr>
            {surveyClass === "단체" ? (
              <tr className="apply_tr">
                <th className="apply_th">비밀번호</th>
                <td colSpan={3} className="apply_td">
                  <input
                    type="text"
                    style={{
                      border: "none",
                      outline: "none",
                    }}
                    onClick={onPwd}
                  />
                </td>
              </tr>
            ) : null}
            <tr className="apply_tr">
              <th className="apply_th">대상</th>
              <td className="apply_td">
                <input
                  type="radio"
                  id="person"
                  value="개인"
                  name="surveyClass"
                  onClick={onSurveyClass}
                ></input>
                <label for="person">개인</label>
                <input
                  type="radio"
                  id="people"
                  value="단체"
                  name="surveyClass"
                  onClick={onSurveyClass}
                ></input>
                <label for="people">단체</label>
              </td>
              <td className="apply_td">
                <input
                  type="radio"
                  id="woman"
                  value="여성"
                  name="surveyClassGender"
                  onClick={onGender}
                ></input>
                <label for="woman">여성</label>
                <input
                  type="radio"
                  id="man"
                  value="남성"
                  name="surveyClassGender"
                  onClick={onGender}
                ></input>
                <label for="man">남성</label>
                <input
                  type="radio"
                  id="genderAll"
                  value="모두"
                  name="surveyClassGender"
                  onClick={onGender}
                ></input>
                <label for="genderAll">모두</label>
              </td>
              <td className="apply_td">
                <input
                  type="radio"
                  id="ten"
                  name="surveyClassAge"
                  value="10"
                  onChange={onAge}
                ></input>
                <label for="ten">10대</label>
                <input
                  type="radio"
                  id="twenty"
                  name="surveyClassAge"
                  value="20"
                  onChange={onAge}
                ></input>
                <label for="twenty">20대</label>
                <input
                  type="radio"
                  id="thirty"
                  name="surveyClassAge"
                  value="30"
                  onChange={onAge}
                ></input>
                <label for="thirty">30대</label>
                <input
                  type="radio"
                  id="forty"
                  name="surveyClassAge"
                  value="40"
                  onChange={onAge}
                ></input>
                <label for="forty">40대</label>
                <br></br>
                <input
                  type="radio"
                  id="fifty"
                  name="surveyClassAge"
                  value="50"
                  onChange={onAge}
                ></input>
                <label for="fifty">50대</label>
                <input
                  type="radio"
                  id="sixty"
                  name="surveyClassAge"
                  value="60"
                  onChange={onAge}
                ></input>
                <label for="sixty">60대이상</label>
              </td>
            </tr>
            <tr className="apply_tr">
              <th className="apply_th">1인당 적립금</th>
              <td colSpan={3} className="apply_td">
                <input
                  type="number"
                  step={50}
                  min={0}
                  style={{
                    border: "none",
                    outline: "none",
                  }}
                  onChange={onMoney}     
                />
              </td>
            </tr>
            <tr className="apply_tr">
              <th className="apply_th">최대 참여인원</th>
              <td colSpan={3} className="apply_td">
                <input
                  type="number"
                  step={10}
                  min={0}
                  style={{
                    border: "none",
                    outline: "none",
                  }}
                  onChange={onParticipant}
                />
              </td>
            </tr>
            <tr className="apply_tr">
              <th className="apply_th">설문조사 기간</th>
              <td colSpan={2} className="apply_td">
                <label>
                  시작 :
                  <input
                    type="date"
                    name="startPeriod"
                    id="startPeriod"
                    min={new Date().toISOString().split('T')[0]}
                    onChange={onStart}
                    value={periodStart}
                  ></input>
                </label>
              </td>
              <td className="apply_td">
                <label>
                  마감 :
                  <input
                    type="date"
                    name="endPeriod"
                    id="endPeriod"
                    min={new Date().toISOString().split('T')[0]}
                    onChange={onStop}
                    value={periodStop}
                  ></input>
                </label>
              </td>
            </tr>
            <tr className="apply_tr">
              <th className="apply_th">카테고리</th>
              <td colSpan={3} className="apply_td">
                <select onChange={onCategory}>
                  <option value="1">교육</option>
                  <option value="2">사회</option>
                  <option value="3">경제</option>
                  <option value="4">기술</option>
                  <option value="5">건강</option>
                  <option value="6">미용</option>
                  <option value="7">취미</option>
                  <option value="8">기타</option>
                </select>
              </td>
            </tr>
            <tr className="apply_tr">
              <th className="apply_th">첨부파일</th>
              <td colSpan={3} className="apply_td">
                <input type="file" onChange={onFile} />
              </td>
            </tr>
          </table>
        </div>
        <button
          style={{
            width: "100px",
            height: "30px",
            backgroundColor: "#0F3360",
            color: "white",
            fontWeight: 600,
            margin: "auto",
            cursor: "pointer",
          }}
          onClick={onClickHandler}
        >
          다음으로
        </button>
      </div>
    </>
  );
}
