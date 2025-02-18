import React, { useEffect, useState } from "react";
import "./IndexSurveyPage.css";
import { Link, useLocation } from "react-router-dom";
import axios from "axios";
import { useCookies } from "react-cookie";

export default function IndexSurveyPage() {
  const { state } = useLocation();

  const [surveyDetailTitle, setSurveyDetailTitle] = useState("");
  const [surveyDetailContent, setSurveyDetailContent] = useState("");
  const [surveyDetailQuestion, setSurveyDetailQuestion] = useState([]);
  const [cookies] = useCookies(["token"]);
  const token = cookies.token;
  // const [answers, setAnswers] = useState({
  //   questionCode: [],
  //   optionCode: [],
  //   shortAnswer: [],
  //   duplicationAnswer: [],
  //   subjectiveAnswer: [],
  //   detailAnswer: []
  // });

  // const handleAnswerChange = (questionIndex, value) => {
  //   setAnswers((prev) => {
  //     const updatedShortAnswers = [...prev.shortAnswer];
  //     updatedShortAnswers[questionIndex] = value;
  //     return { ...prev, shortAnswer: updatedShortAnswers };
  //   });
  // };

  // const handleOptionChange = (questionIndex, optionCode) => {
  //   setAnswers((prev) => {
  //     const updatedOptionCodes = [...prev.optionCode];
  //     updatedOptionCodes[questionIndex] = optionCode;
  //     return { ...prev, optionCode: updatedOptionCodes };
  //   });
  // };

  // const handleCheckboxChange = (questionIndex, optionCode) => {
  //   setAnswers((prev) => {
  //     const updatedDuplicationAnswers = [...prev.duplicationAnswer];
  //     if (!updatedDuplicationAnswers[questionIndex]) {
  //       updatedDuplicationAnswers[questionIndex] = [];
  //     }
  //     if (updatedDuplicationAnswers[questionIndex].includes(optionCode)) {
  //       updatedDuplicationAnswers[questionIndex] = updatedDuplicationAnswers[questionIndex].filter(
  //         (code) => code !== optionCode
  //       );
  //     } else {
  //       updatedDuplicationAnswers[questionIndex].push(optionCode);
  //     }
  //     return { ...prev, duplicationAnswer: updatedDuplicationAnswers };
  //   });
  // };
  

  useEffect(() => {
    axios
      .get(
        `http://localhost:8000/survey/personal/list/details/${state.surveyCode}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      )
      .then((response) => {
        setSurveyDetailTitle(response.data.data.survey_title);
        setSurveyDetailContent(response.data.data.survey_content);
        setSurveyDetailQuestion(response.data.data.questionResDtoList);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  // const submitAnswer = () => {
  //   axios.post(
  //     `http://localhost:8000/survey/personal/list/answer/${state.surveyCode}`,
  //     answers,
  //     {
  //       headers: {
  //         Authorization: `Bearer ${token}`,
  //       },
  //     }
  //   )
  //   .then((response) => {
  //     console.log(response);
  //   })
  //   .catch((error) => {
  //     console.error(error);
  //   });
  // }

  return (
    <>
      <div className="surveypage-container">
        <div className="surveypage-main">
          <div className="survey-title-box">
            <div
              style={{
                backgroundColor: "#0F3360",
                height: "20px",
                borderTopLeftRadius: "8px",
                borderTopRightRadius: "8px",
              }}
            ></div>
            <div className="title-box">
              <div>
                <span
                  style={{
                    border: "none",
                    fontSize: "25px",
                    background: "#f9f9f9",
                  }}
                >
                  {surveyDetailTitle}
                </span>
              </div>
            </div>
            <div className="info-box-surveypage">
              <span
                style={{
                  width: "560px",
                  border: "none",
                  background: "#f9f9f9",
                }}
              >
                {surveyDetailContent}
              </span>
            </div>
          </div>
          {surveyDetailQuestion.map((question, i) => (
            <div className="question_box_input">
              <div className="question_title_box">
                <div>
                  <input
                    name={i}
                    value={question.question_title}
                    style={{
                      border: "none",
                      background: "#f4f4f4",
                      fontSize: "18px",
                      minWidth: "400px",
                    }}
                    placeholder="제목 없는 질문"
                    readOnly
                  />
                </div>
                <div>
                  <select value={question.select_type} name={i}>
                    <option value="0">주관식 질문</option>
                    <option value="1">객관식 질문</option>
                    <option value="2">체크박스</option>
                  </select>
                </div>
              </div>
              {question.select_type === 0 ? (
                <div className="survey_form_answer">
                  답변:
                  <input
                    style={{
                      border: "none",
                      backgroundColor: "#f4f4f4",
                      borderBottom: "1px solid black",
                      width: "560px",
                    }}
                    // onChange={(e) => handleAnswerChange(i, e.target.value)}
                  />
                </div>
              ) : question.select_type === 1 ? (
                <div className="survey_form_options">
                  {question.optionResDtoList.map(
                    (option, index2) =>
                      option.option_content !== "" && (
                        <div>
                          <div>
                            <input
                              type="radio"
                              name="question_option"
                              readOnly
                            />
                            <input
                              value={option.option_content}
                              name={[i, index2]}
                              style={{
                                marginLeft: "10px",
                              }}
                              placeholder={"옵션" + (index2 + 1)}
                              // onChange={() => handleOptionChange(i, option.oprion_code)}
                              readOnly
                            />
                          </div>
                        </div>
                      )
                  )}
                </div>
              ) : question.select_type === 2 ? (
                <div className="survey_form_options">
                  {question.optionResDtoList.map(
                    (option, index2) =>
                      option.option_content !== "" && (
                        <div>
                          <div>
                            <input type="checkbox" name="question_checkbox" />
                            <input
                              value={option.option_content}
                              name={[i, index2]}
                              style={{
                                marginLeft: "10px",
                              }}
                              placeholder={"옵션" + (index2 + 1)}
                              // onChange={() => handleCheckboxChange(i, option.oprion_code)}
                              readOnly
                            />
                          </div>
                          <form>
                            <input
                              type="file"
                              name={[i, index2]}
                              accept=".jpg, .png, .gif, .jpeg"
                              className="inputImage"
                              readOnly
                            />
                          </form>
                        </div>
                      )
                  )}
                </div>
              ) : null}
              {question.detail_question &&
                question.detail_question.trim() !== "" && (
                  <div className="detail_question_box">
                    <div className="detail_question_title">
                      <input
                        value={question.detail_question}
                        name={i}
                      />
                    </div>
                    <div>
                      <textarea
                        style={{
                          width: "560px",
                          height: "30px",
                          resize: "none",
                          marginTop: "10px",
                        }}
                      ></textarea>
                    </div>
                  </div>
                )}
              <div className="question_bottom_box">
                <div>
                  필수
                  <input
                    type="checkbox"
                    checked={question.question_essential}
                    style={{ marginLeft: "5px" }}
                  />
                </div>
                <div></div>
              </div>
            </div>
          ))}
          <div className="surveypage-buttons">
            {
              <Link to={"/"}>
                <button type="button" className="surveypage-cancel-button">
                  취소
                </button>
              </Link>
            }
            {
              <Link
                to={"/survey/personal/list/{surveyCode}/complete"}
                state={{ surveyCode: state.surveyCode }}
              >
                <button type="button" className="surveypage-submit-button">
                  제출
                </button>
              </Link>
            }
          </div>
        </div>
      </div>
    </>
  );
}
