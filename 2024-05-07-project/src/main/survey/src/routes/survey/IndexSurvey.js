import "./IndexSurvey.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHouse, faL } from "@fortawesome/free-solid-svg-icons";
import { faXmark } from "@fortawesome/free-solid-svg-icons";
import done from "./../../images/done.png";
import "./SurveyList.css";
import { Link, useLocation, useNavigate } from "react-router-dom";
import Modal from "react-modal";
import "./SurveyList.css";
import { useState } from "react";
import axios from "axios";
import { useEffect } from "react";
import { useCookies } from "react-cookie";

// 날짜 : 2024.05.09
// 작성자 : 김예찬
//2024.5.21 최소윤 modal부분

Modal.setAppElement("#root");

//설문조사 개인 카테고리 필터
export default function IndexSurvey() {
  const [surveyData, setSurveyData] = useState([]);
  const [cookies] = useCookies(["token"]);
  const token = cookies.token;
  const navigate = useNavigate();

  useEffect(() => {
    if(!token) {
      alert("로그인 후 사용 가능합니다.");
      navigate("/auth/signup/person");
    }
  })

  useEffect(() => {
    axios
      .get("/survey/personal/list/main/surveyList", {
        headers: {
          Authorization: `Bearer ${token}`, 
        },
      })
      .then((response) => {
        setSurveyData(response.data.data);
        console.log(response.data.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  const [selectedGender, setSelectedGender] = useState([]);
  const [selectedCategories, setSelectedCategories] = useState([]);
  const [selectedAges, setSelectedAges] = useState([]);

  const handleGenderChange = (gender) => {
    setSelectedGender((prevSelectedGender) => {
      if (prevSelectedGender.includes(gender)) {
        return prevSelectedGender.filter((g) => g !== gender);
      } else {
        return [...prevSelectedGender, gender];
      }
    });
  };

  const handleCategoryChange = (categoryName) => {
    setSelectedCategories((prevCategories) => {
      if (prevCategories.includes(categoryName)) {
        return prevCategories.filter((c) => c !== categoryName);
      } else {
        return [...prevCategories, categoryName];
      }
    });
  };

  const handleAgeChange = (age) => {
    setSelectedAges((preAges) => {
      if (preAges.includes(age)) {
        console.log(age);
        return preAges.filter((a) => a !== age);
      } else {
        console.log(typeof selectedAges);
        return [...preAges, age];
      }
    });
  };

  //검색창 결과
  const location = useLocation();
  const [query, setQuery] = useState("");
  useEffect(() => {
    if (location.state && location.state.query != null) {
      setQuery(location.state.query);
    }
  }, [location.state]);

  const [modal, setModal] = useState(false); 
  const [message, setMessage] = useState(""); 

  const openModal = (message) => {
    setModal(!modal);
    setMessage(message);
  };

  const closeModal = () => {
    setModal(!modal);
    setMessage(""); 
  };

  const CustomModal = ({ modal, message }) => {
    const modalStyle = {
      content: {
        top: "50%",
        left: "50%",
        right: "auto",
        bottom: "auto",
        marginRight: "-50%",
        transform: "translate(-50%, -50%)",
        width: "520px",
        height: "320px",
        borderRadius: "16px",
      },
    };

    return (
      <Modal
        isOpen={modal}
        onRequestClose={closeModal}
        shouldCloseOnOverlayClick={false}
        style={modalStyle}
      >
        <div
          style={{
            position: "absolute",
            top: 0,
            left: 0,
            right: 0,
            height: "70px",
            borderRadius: "16px 16px 0px 0px",
            backgroundColor: "#77A1D7",
            alignContent: "center",
          }}
        >
          <div>
            <FontAwesomeIcon
              icon={faXmark}
              style={{
                color: "#fefefe",
                marginTop: "2px",
                transform: "scale(2, 2)",
                position: "relative",
                right: -475,
                cursor: "pointer",
              }}
              onClick={closeModal}
            />
          </div>
        </div>
        <div
          style={{
            display: "flex",
            justifyContent: "space-around",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <div>
            <img
              src={done}
              alt="done"
              style={{
                marginTop: "15px",
                transform: "scale(1.5, 1.5)",
                position: "relative",
                top: 95,
              }}
            />
          </div>
          <div>
            <h2
              style={{
                color: "#ff0000",
                position: "absolute",
                bottom: 35,
                left: 130,
              }}
            >
              {message}
            </h2>
          </div>
        </div>
      </Modal>
    );
  };

  return (
    <div className="container">
      <div className="surveyBox-survey">
        <div className="infoBox-survey">
          <FontAwesomeIcon
            icon={faHouse}
            size="lg"
            className="houseImg-survey"
          />
        </div>
        <div className="infoBox-survey">
          <span>설문조사</span>
        </div>
        <div className="infoBox-survey">
          <span>설문조사(개인)</span>
        </div>
      </div>
      {/* 필터 박스 */}
      <div>
        <div
          style={{ marginTop: "15px", display: "flex", flexDirection: "row" }}
        >
          <div style={{ marginLeft: "20px" }}>
            <span>카테고리</span>
            <CategoryFilter
              selectedCategories={selectedCategories}
              onCategoryChange={handleCategoryChange}
            />
          </div>
          <div style={{ marginLeft: "20px" }}>
            <span>성별</span>
            <div
              style={{
                display: "flex",
                marginTop: "10px",
                width: "250px",
                height: "100px",
                alignItems: "center",
                justifyContent: "center",
                backgroundColor: "#eee",
              }}
            >
              <div
                style={{
                  display: "flex",
                  alignItems: "center",
                  marginBottom: "8px",
                  justifyContent: "center",
                }}
              >
                <GenderFilter
                  selectedGender={selectedGender}
                  handleGenderChange={handleGenderChange}
                />
              </div>
            </div>
          </div>
          <AgeFilter
            handleAgeChange={handleAgeChange}
            selectedAges={selectedAges}
          />
        </div>
      </div>
      {/* 필터 박스 */}
      <hr />
      <div className="surveyList">
        <SurveyList
          surveyData={surveyData}
          selectedGender={selectedGender}
          selectedCategories={selectedCategories}
          query={query}
          selectedAges={selectedAges}
        />
      </div>
    </div>
  );
}

const SurveyList = ({
  surveyData,
  genderName,
  selectedCategories,
  query,
  selectedGender,
  selectedAges,
}) => {
  const filteredProducts = surveyData.filter((survey) => {
    const categoryMatch =
      selectedCategories.length === 0 ||
      selectedCategories.includes(survey.categoryName);
    const genderMatch =
      selectedGender.length === 0 ||
      selectedGender.includes(survey.surveyTargetGender);
    const ageMatch =
      selectedAges.length === 0 ||
      selectedAges.includes(survey.surveyTargetAge);
    const searchMatch = survey.surveyTitle.includes(query);

    return categoryMatch && genderMatch && ageMatch && searchMatch;
  });

  const navigate = useNavigate();

  const handleSurveyClick = (surveyCode) => {
    navigate(`/survey/personal/list/start/${surveyCode}`);
  };

  return (
    <>
      {filteredProducts.map((survey) => (
        <div
          className="survey-list"
          key={survey.surveyCode}
          onClick={() => handleSurveyClick(survey.surveyCode)}
        >
          <div
            className="survey-title"
            style={{
              display: "flex",
              justifyContent: "center",
            }}
          >
            <span
              style={{
                width: "180px",
                overflow: "hidden",
                textOverflow: "ellipsis",
                whiteSpace: "nowrap",
              }}
            >
              {survey.surveyTitle}
            </span>
          </div>
          <div className="survey-list-content">
            <span>카테고리 : {survey.categoryName}</span>
            <span>적립금 : {survey.surveyPerMoney}</span>
            <span>성별 : {survey.surveyTargetGender}</span>
            <span>연령 : {JSON.parse(survey.surveyTargetAge)}</span>
            <span>
              기간 : {survey.surveyPeriodStart} ~ {survey.surveyPeriodStop}
            </span>
          </div>
        </div>
      ))}
    </>
  );
};

const categories = [
  "교육",
  "경제",
  "건강",
  "취미",
  "사회",
  "기술",
  "미용",
  "기타",
];

const CategoryFilter = ({ selectedCategories, onCategoryChange }) => {
  return (
    <>
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          marginTop: "10px",
          width: "410px",
          height: "100px",
          alignItems: "center",
          backgroundColor: "#eee",
        }}
      >
        <div style={{ marginRight: "20px" }}>
          <div
            style={{
              display: "flex",
              flexDirection: "row",
              marginBottom: "8px",
            }}
          >
            <div>
              {categories.map((categoryName) => (
                <label key={categoryName}>
                  <input
                    type="checkbox"
                    value={categoryName}
                    checked={selectedCategories.includes(categoryName)}
                    onChange={() => onCategoryChange(categoryName)}
                    className="checkbox"
                  />
                  {categoryName}
                </label>
              ))}
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

const genders = ["모두", "남성", "여성"];
const GenderFilter = ({ selectedGender, handleGenderChange }) => {
  return (
    <>
      {genders.map((gender) => (
        <label key={gender}>
          <input
            type="checkbox"
            value={gender}
            checked={selectedGender.includes(gender)}
            onChange={() => {
              handleGenderChange(gender);
            }}
            className="checkbox"
          />
          {gender}
        </label>
      ))}
    </>
  );
};

const Ages = ["10", "20", "30", "40", "50", "60"];
const AgeFilter = ({ handleAgeChange, selectedAges }) => {
  return (
    <div style={{ marginLeft: "20px" }}>
      <span>연령</span>
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          alignContent: "space-around",
          marginTop: "10px",
          width: "500px",
          height: "100px",
          alignItems: "center",
          backgroundColor: "#eee",
        }}
      >
        <div style={{ marginRight: "20px" }}>
          <div
            style={{
              display: "flex",
              flexDirection: "row",
              marginBottom: "8px",
            }}
          >
            {Ages.map((age) => (
              <div className="C">
                <input
                  value={age}
                  type="checkbox"
                  className="age-checkbox"
                  checked={selectedAges.includes(age)}
                  onChange={() => {
                    handleAgeChange(age);
                  }}
                />
                <span>{age}대</span>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};
