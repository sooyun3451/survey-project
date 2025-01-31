import React, {useState} from 'react'
import '../IndexSurvey.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faHouse, faTruckMedical} from '@fortawesome/free-solid-svg-icons';
import {faXmark} from '@fortawesome/free-solid-svg-icons';
import './SurveyGroup.css';
import '../SurveyList.css';
import Modal from 'react-modal';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useEffect, useRef } from 'react';
import { useInView } from 'react-intersection-observer';
import qs from 'qs';
import { useCookies } from 'react-cookie';


Modal.setAppElement('#root')

export default function SurveyGroup() { 

const [modal, setModal] = useState(false);
const [surveyList, setSurveyList] = useState([]);
const [cookies] = useCookies(["token"]);
const token = cookies.token;
const[page,setPage] = useState(1);
const[surveyData, setSurveyData] = useState([]);
const [selectedGender, setSelectedGender] = useState([]);
const [selectedCategories, setSelectedCategories] = useState([]);
const [selectedAges, setSelectedAges] = useState([]);

const handleGenderChange = (gender) => {
    setSelectedGender(prevSelectedGender => {
        if (prevSelectedGender.includes(gender)) { 
        return prevSelectedGender.filter(g => g !== gender);
        } else {
        return [...prevSelectedGender, gender];
        }
    });
    };

const handleCategoryChange = (categoryName) => {
    setSelectedCategories(prevCategories => {
        if (prevCategories.includes(categoryName)) {
        return prevCategories.filter(c => c !== categoryName);
        } else {
        return [...prevCategories, categoryName];
        }
    });
    };

const handleAgeChange =(age) => {
    setSelectedAges(preAges => {
        if(preAges.includes(age)){
            console.log(age)
            return preAges.filter(a => a !== age)
        }else{
            console.log(typeof(selectedAges))
            return [...preAges, age]
        }
    })
}

useEffect (() => {
    axios.get('http://localhost:8000/survey/group/list/surveyList',
    {
        headers:{
            'Authorization' :`Bearer ${token}` 
            }
    }).then(response=>{
        console.log(response.data.data)
        setSurveyData(response.data.data)
    }).catch(error => {
        console.log(error)
    })
},[])
const [ref, inView] = useInView();
const [isLoading, setIsLoading] = useState(false);
const observer = useRef();


// const getSurveyList = async (page) => {
//     try {
//         setIsLoading(true);
//         const response = await axios.get('/survey/group/list', {
//             headers: {
//                 Authorization: `Bearer ${token}`
//             },
//             params: {
//                 page: page,
//                 contentCount: 9,
//                 surveyClass: '단체',
//                 categoryCode: null, // null이면 전달되지 않음
//                 surveyTargetGender: null, // 빈 문자열이면 전달되지 않음
//                 surveyTargetAge: null // 빈 문자열이면 전달되지 않음
//             }
//         });
//         if (response.data.data.length > 0) {
//             setSurveyList(prevList => page === 1 ? response.data.data : [...prevList, ...response.data.data]);
//         } else if(observer.current) {
//             observer.current.disconnect();
//         }
//         setIsLoading(false);
//     } catch (error) {
//         console.log('Error:', error);
//         setIsLoading(false);
//     }
// };

useEffect(() => {

    const currentObserver = observer.current;
    if(currentObserver) {
        currentObserver.disconnect();
    }

    observer.current = new IntersectionObserver((entries) => {
        const target = entries[0];
        if (target.isIntersecting && !isLoading) {
            setPage(prevPage => prevPage + 1);
        }
    }, {
        root: null,
        rootMargin: '0px',
        threshold: 1.0
    });

    if (ref.current) {
        observer.current.observe(ref.current);
    }

    return () => {
        if (observer.current) {
            observer.current.disconnect();
        }
    };
}, [ref, isLoading]);

// useEffect(() => {
//     getSurveyList(page);
// }, [page]);

useEffect(() => {
    setSurveyList([]);
    setPage(1);
    // getSurveyList(1);
}, []);


    const openModal = () => {
        setModal(!modal);
    };

    const closeModal = () => {
        setModal(!modal);
    };

    const modalStyle = {
        content : {
            top : '50%',
            left : '50%',
            right : 'auto',
            bottom : 'auto',
            marginRight : '-50%',
            transform : 'translate(-50%, -50%)',
            width : '520px',
            height : '320px',
            borderRadius : '16px'
        },
        overlay: {
            backgroundColor: 'rgba(255, 255, 255, 0.3)'
        }
    };

    
return(
    <div className='container'>
        <div className='surveyBox-surveygroup'>
            <div className='infoBox-surveygroup'>
            <FontAwesomeIcon icon={faHouse} size='lg' className='houseImg-surveygroup'/>
            </div>
                <div className='infoBox-surveygroup'><span>설문조사</span></div>
                <div className='infoBox-surveygroup'><span>설문조사(단체)</span></div>
        </div>

            {/* 필터 박스 */}
            <div>
            <div style={{marginTop: "15px",
                        display: "flex",
                        flexDirection: "row"}}>
                <div style={{marginLeft: "20px"}}>
                    <span>카테고리</span>
                    <CategoryFilter selectedCategories={selectedCategories} onCategoryChange={handleCategoryChange} />
                </div>
                <div style={{marginLeft: "20px"}}>
                    <span>성별</span>
                    <div style={{display: "flex",                     
                        marginTop: "10px",
                        width: "250px",
                        height: "100px",
                        alignItems: "center",
                        backgroundColor: "#eee"}}>

                                <div style={{display: "flex", alignItems: "center", marginBottom: "8px", justifyContent : 'center'}}>
                                <GenderFilter selectedGender={selectedGender} handleGenderChange = {handleGenderChange}/>
                            </div>
                        </div>
                    
                </div>
            <AgeFilter handleAgeChange={handleAgeChange} selectedAges={selectedAges}/>
            </div>
        </div> 
        {/* 필터 박스 */}

        <hr></hr>
        <div className='surveyList'>
            <SurveyList surveyData={surveyData} openModal={openModal} selectedGender={selectedGender} selectedCategories={selectedCategories} selectedAges={selectedAges} /> 
        {
            surveyList.map((survey, index) => {
                return(
                    <>
                    <div className='survey-list' style={{cursor: "pointer"}} onClick={openModal}
                    key={survey.surveyCode}>
                        <div className='survey-title'>
                            <span>{survey.surveyTitle}</span>
                        </div>
                        <div className='survey-list-content'>
                            <p>기간 : {survey.surveyPeriodStart} ~ {survey.surveyPeriodStop}</p>
                        </div>
                    </div>
                    
                    <Modal 
                    isOpen={modal}
                    onRequestClose={closeModal}
                    shouldCloseOnOverlayClick={false}
                    style={modalStyle}
                    >
                    <div style={{
                        position: "absolute",
                        top: 0,
                        left: 0,
                        right: 0,
                        height: "70px",
                        borderRadius: "16px 16px 0px 0px",       
                        backgroundColor: "#0F3360",
                        alignContent: "center"
                        }}>
                        <div style={{display: "flex",
                                    justifyContent: "space-between"}}>
                            <h2 style={{color: "#fefefe",
                                fontWeight: "normal",
                                position: "relative",
                                right: -40
                                }}>코드 입력</h2>
                                <div>
                                    <FontAwesomeIcon icon={faXmark} 
                                    style={{color: "#fefefe",
                                            transform: "scale(2, 2)",
                                            position: "relative",
                                            right: 26,
                                            top: 6,
                                            cursor: "pointer"
                                            }}
                                            onClick={closeModal}
                                            />
                                </div>
                        </div>
                    </div>
                    <div className='밑 3분의 2' style={{display: "flex",
                                                flexDirection: "column",
                                                alignItems: "center",
                                                position: "relative",
                                                top: 75
                                                }}>

                        {/* 코드를 입력해주세요 네모 창 */}
                        <div style={{width: "400px",
                                    height: "150px",
                                    display: "flex",
                                    justifyContent: "center",
                                    alignItems: "center",
                                    border: "1px solid #141414",
                                    borderRadius: "6px"
                                    }}>
                            <div style={{position: "absolute",
                                        top: 0,
                                        display: "flex",
                                        justifyContent:"center",
                                        width: "400px",
                                        height: "40px",
                                        backgroundColor: "#0F3360",
                                        color: "#fefefe",
                                        borderRadius: "6px",
                                        alignItems: "center"
                                        }}>
                                <h3 style={{fontWeight: "normal"
                                }}>코드를 입력해주세요</h3>
                            </div>
                            <div>
                                <input type='text' size="20" maxLength="10"
                                
                                style={{borderTop: "none",
                                        borderLeft: "none",
                                        borderRight: "none",
                                        textAlign: "center",
                                        position: "relative",
                                        top: 20   
                                }}
                                onFocus={(e) => {
                                    e.target.style.borderWidth = "3px";
                                    e.target.style.borderColor = "#77A1D7";
                                    e.target.style.outline = "none";
                                }}
                                onBlur={(e) => {
                                    e.target.style.borderWidth = "2px";
                                    e.target.style.borderColor = "#141414";
                                }}
                                />
                            </div>
                        </div>
                        <br></br>

                        {/* 입력버튼 */}
                        <div>
                            <input onClick={() => {}} type='button' value="입력" 
                            style={{fontSize: "18px",
                                    width: "84px",
                                    height: "30px",
                                    color: "#fefefe",
                                    backgroundColor: "#0F3360",
                                    borderRadius: "5px",
                                    position: "relative",
                                    bottom: 4,
                                    cursor: "pointer"
                                    }}/>
                        </div>
                    </div>
                </Modal>        
                </>
                    )})
            }
                <div ref={ref} style={{ height: '1px' }} />
                {isLoading && <p>Loading...</p>}           
        </div>
    </div>
)
}

const SurveyList = ({surveyData, openModal, selectedCategories, selectedGender, selectedAges}) => {

const filteredProducts = surveyData.filter(survey => {
    const categoryMatch = selectedCategories.length === 0 || selectedCategories.includes(survey.categoryName);
    const genderMatch = selectedGender.length === 0 || selectedGender.includes(survey.surveyTargetGender)
    const ageMatch = selectedAges.length === 0 || selectedAges.includes(survey.surveyTargetAge)

    return categoryMatch && genderMatch && ageMatch;
})

const navigate = useNavigate();

const handleSurveyClick = (surveyCode) => {
navigate(`/survey/group/list/start/${surveyCode}`);
};

return(
        <>
            {filteredProducts.map((survey) => (
                        <div className='survey-list' style={{cursor: "pointer"}} onClick={() => {handleSurveyClick(survey.surveyCode)}}>
                            <div className='survey-title'>
                                <span>{survey.surveyTitle}</span>
                            </div>
                            <div className='survey-list-content'>
                                <span>카테고리 : {survey.categoryName}</span>
                                <span>적립금 : {survey.surveyPerMoney}</span>
                                <span>성별 : {survey.surveyTargetGender}</span>
                                <span>연령 : {survey.surveyTargetAge}</span>
                                <p>기간 : {survey.surveyPeriodStart} ~ {survey.surveyPeriodStop}</p>
                            </div>
                        </div>
                ))
            }  
        </>
        )
}

const categories = ['교육', '경제', '건강', '취미', '사회', '기술', '미용', '기타'];
const CategoryFilter = ({selectedCategories, onCategoryChange}) =>{
    return(
        <>
            <div style={{display: "flex", justifyContent: "center",
                                marginTop: "10px",
                                width: "410px",
                                height: "100px",
                                alignItems: "center",
                                backgroundColor: "#eee"}}>
                    <div style={{marginRight: "20px"}}>
                        <div style={{display: "flex", flexDirection: "row", marginBottom: "8px"}}>
                            <div>
                                {categories.map(categoryName => (
                            <label key={categoryName}>
                            <input
                            type="checkbox"
                            value={categoryName}
                            checked={selectedCategories.includes(categoryName)}
                            onChange={() => onCategoryChange(categoryName)}
                            className='checkbox'
                            />
                            {categoryName}
                                </label>

                        ))}

                        </div>
                    </div>
                </div>
            </div>
        </>

    )
}

const genders = ['모두', '남성', '여성'];
const GenderFilter = ({selectedGender,handleGenderChange }) => {
    return(
        <>
                {
            genders.map(gender => (
                <label key={gender}>
                    <input type='checkbox'
                            value={gender}
                            checked={selectedGender.includes(gender)}
                            onChange={()=>{handleGenderChange(gender)}}
                            className='checkbox'/>
                    {gender}
                </label>
                
            ))
        }
        </>

    )
}
const Ages = ['10', '20', '30', '40', '50', '60']
const AgeFilter = ({selectedAges, handleAgeChange, }) => {
    return(
        <>
            <div style={{marginLeft: "20px"}}>
            <span>연령</span>
            <div style={{display: "flex", justifyContent:"center",
                            alignContent: "space-around",

                        marginTop: "10px",

                        width: "500px",
                        height: "100px",

                        alignItems: "center",
                        backgroundColor: "#eee"}}>

            <div style={{marginRight: "20px"}}>
                <div style={{display: "flex", flexDirection: "row", marginBottom: "8px"}}>
                {
                    Ages.map(age => (
                        <div className='C'>
                        <input value={age} 
                            type='checkbox' 
                            className='age-checkbox'
                            checked={selectedAges.includes(age)}
                            onChange={()=>{handleAgeChange(age)}}/>
                        <span>{age}대</span>
                    </div>
                ))
                }
                    </div>
                </div>
        </div>
    </div>
        </>

    )
}

                            

