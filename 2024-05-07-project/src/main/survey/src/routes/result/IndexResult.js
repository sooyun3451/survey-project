import React, { useEffect, useState } from "react";
import Axios from "axios";
import { useLocation } from "react-router-dom";
import { useCookies } from "react-cookie";

export default function IndexResult() {
  const [cookies] = useCookies(["token"]);
  const token = cookies.token;
  const userCode = localStorage.getItem("userCode");
  const { state } = useLocation();
  const [surveyTitle, setSurveyTitle] = useState("");
  const [surveyQuestion, setSurveyQuestion] = useState([]);

  const surveyResult = () => {
    Axios.get(`/result/${state.surveyCode}/${userCode}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        console.log(response);
        setSurveyTitle(response.data.data.surveyTitle);
        setSurveyQuestion(response.data.data.questions);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  useEffect(() => {
    surveyResult();
  }, []);

  return (
    <>
      <div>
        <div>
          <h2>{surveyTitle}</h2>
        </div>
        <div>
          {surveyQuestion.map((question, i) => (
            <>
              <div>
                <h3>
                  Q{i + 1} : {question.questionTitle}
                </h3>
              </div>
              <div>
                <h3>
                  Q{i + 1} 꼬리질문 :{question.detailQuestion}
                </h3>
              </div>
            </>
          ))}
        </div>
      </div>
    </>
  );
}
