import Axios from 'axios';
import React, { useState } from 'react'
import { useCookies } from 'react-cookie';
import { useLocation } from 'react-router-dom'
export default function IndexSurveyPaymentResult() {

    const location = useLocation();
    const url = location.search;
    const pgToken = url.split('=')[1];
    const [cookies] = useCookies(["token"]);
    const token = cookies.token;

    const [data, setData] = useState(); 
      
    const handleApprove = () => {
      Axios.get('/payment/success', {
        params: {"pg_token" : pgToken},
        headers: {
          Authorization: `Bearer ${token}`
        }
      })
      .then((response) => {
        setData(response.data);
        window.location.href = 'http://localhost:3000'
      })
      .catch((err) => {
        console.log(err);
      })
    }

  return (
    <>  
      <div style={{
        display: 'flex',
        width: '400px',
        height: '600px',
        margin: 'auto'
      }}>
        <button onClick={handleApprove} style={{
          cursor : 'pointer',
          backgroundColor: '#0f3360',
          color: 'white',
          width: '400px',
          height: '50px',
          borderRadius: '5px',
          margin: 'auto',
          fontSize: '20px'
        }}>
          버튼을 누르면 결제가 완료됩니다.
        </button>
      </div>
    </>
  )
}
