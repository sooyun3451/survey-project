import "./App.css";
import { Link, Route, Routes, useNavigate } from "react-router-dom";

import NavigationBar from "./components/NavigationBar";
import IndexMain from "./routes/main/IndexMain";
import IndexMypage from "./routes/mypage/IndexMypage";
import Login from "./routes/auth/Login";
import ReadySignUp from "./routes/auth/ReadySignUp";
import SnsSuccess from "./routes/auth/SnsSuccess";
import IndexSurveyInfo from "./routes/surveyinfo/IndexSurveyInfo";
import IndexWebInfo from "./routes/webinfo/IndexWebInfo";
import IndexNotice from "./routes/notice/IndexNotice";
import IndexCreateNotice from "./routes/createNotice/IndexCreateNotice";
import IndexNoticeDetail from "./routes/noticeDetail/IndexNoticeDetail";
import IndexDetailModify from "./routes/detailModify/IndexDetailModify";
import IndexQuestion from "./routes/question/IndexQuestion";
import IndexSurveyApply from "./routes/surveyApply/IndexSurveyApply";
import IndexSurveyApplyPerson from "./routes/surveyApply/IndexSurveyApplyPerson";
import IndexSurveyApplyPeople from "./routes/surveyApply/IndexSurveyApplyPeople";
import IndexAdmin from "./routes/Admin/IndexAdmin";
import SurveyGroup from "./routes/survey/group/SurveyGroup";
import IndexSurveyStart from "./routes/survey/IndexSurveyStart";
import IndexSurveyPage from "./routes/survey/IndexSurveyPage";
import SurveyGroupStart from "./routes/survey/SurveyGroupStart";
import IndexSurveyComplete from "./routes/survey/complete/IndexSurveyComplete";
import SurveyGroupComplete from "./routes/survey/complete/SurveyGroupComplete";
import IndexMypageModify from "./routes/mypage/IndexMypageModify";
import IndexSurveyForm from "./routes/surveyApply/IndexSurveyForm";
import IndexAdmin2 from "./routes/Admin/IndexAdmin2";
import IndexSignupCompany from "./routes/auth/IndexSignupCompany";
import LoginCompany from "./routes/auth/LoginCompany";
import IndexSignup from "./routes/auth/indexSignup";
import IndexSurveyPayment from "./routes/payment/IndexSurveyPayment";
import IndexSurveyPaymentResult from "./routes/payment/IndexSurveyPaymentResult";
import IndexPassword from "./routes/mypage/IndexPassword";
import IndexSurvey from "./routes/survey/IndexSurvey";
import IndexResult from "./routes/result/IndexResult";
import Footer from "./components/Footer";
import { useEffect } from "react";
import { useCookies } from "react-cookie";

function App() {
  const [cookies] = useCookies(["token"]);
  const token = cookies.token;

  const navigate = useNavigate();

  const EXCLUDED_PATHS = ["/auth/ready/signup", "/auth/signup/person", "/auth/signup/company", "/auth/login", "/auth/login/com"];

  useEffect(() => {
    const currentPath = window.location.pathname;
    const isExcludedPath = EXCLUDED_PATHS.some(path => currentPath.includes(path));
    if (!token && !isExcludedPath) {
        navigate("/");
    }
}, [token]);

  return (
    <>
      <div className="containerApp">
        <NavigationBar />
        <Routes>
          <Route path="/" element={<IndexMain />} />
          <Route path="/mypage" element={<IndexMypage />} />
          <Route path="/auth/login" element={<Login />} />
          <Route path="/auth/login/com" element={<LoginCompany />} />
          <Route path="/auth/ready/signup" element={<ReadySignUp />} />
          <Route path="/sns-success" element={<SnsSuccess />} />
          <Route path="/auth/signup/person" element={<IndexSignup />} />
          <Route path="/auth/signup/company" element={<IndexSignupCompany />} />
          <Route path="/surveyinfo" element={<IndexSurveyInfo />} />
          <Route path="/webinfo" element={<IndexWebInfo />} />
          <Route path="/notice" element={<IndexNotice />} />
          <Route path="/notice/create" element={<IndexCreateNotice />} />
          <Route
            path="/notice/detail/:noticeCode"
            element={<IndexNoticeDetail />}
          />
          <Route
            path="/notice/detail/modify/:noticeCode"
            element={<IndexDetailModify />}
          />
          <Route path="/question" element={<IndexQuestion />} />
          <Route path="/apply" element={<IndexSurveyApply />} />
          <Route path="/apply/person" element={<IndexSurveyApplyPerson />} />
          <Route path="/apply/people" element={<IndexSurveyApplyPeople />} />
          <Route path="/survey/personal/list" element={<IndexSurvey />} />
          <Route path="/survey/group/list" element={<SurveyGroup />} />
          <Route path="/adminpage/company" element={<IndexAdmin />} />
          <Route
            path="/survey/personal/list/:contentCode"
            element={<IndexSurveyStart />}
          />
          <Route
            path="/survey/personal/list/start/:surveyCode"
            element={<IndexSurveyStart />}
          />
          <Route
            path="/survey/survey/page/:surveyCode"
            element={<IndexSurveyPage />}
          />
          <Route
            path="/survey/group/list/start/:surveyCode"
            element={<SurveyGroupStart />}
          />
          <Route
            path="/survey/personal/list/:surveyCode/complete"
            element={<IndexSurveyComplete />}
          />
          <Route
            path="/survey/group/list/:surveyCode/complete"
            element={<SurveyGroupComplete />}
          />
          <Route path="/mypage/modify" element={<IndexMypageModify />} />
          <Route path="/survey/surveyform" element={<IndexSurveyForm />} />
          <Route path="/adminpage/survey" element={<IndexAdmin2 />} />
          <Route path="/payment/:surveyCode" element={<IndexSurveyPayment />} />
          <Route
            path="/payment/success"
            element={<IndexSurveyPaymentResult />}
          />
          <Route path="/mypage/password" element={<IndexPassword />} />
          <Route path="/survey/result/:surveyCode" element={<IndexResult />} />
        </Routes>
      </div>
      <footer>
        <Footer />
      </footer>
    </>
  );
}

export default App;
