import React from 'react'
import { Link } from 'react-router-dom'

export default function footer() {
  return (
    <div className="footerBox">
          <div className="footerBox1">
            <Link
              to={"/webinfo"}
              style={{
                textDecorationLine: "none",
                fontSize: "12px",
              }}
            >
              <p id="footerBox1_item">웹사이트 소개</p>
            </Link>
            <Link
              to={"/notice"}
              style={{
                textDecorationLine: "none",
                fontSize: "12px",
              }}
            >
              <p id="footerBox1_item">공지사항</p>
            </Link>
            <p
              id="footerBox1_item"
              style={{
                fontSize: "12px",
              }}
            >
              E-mail : soyun@gmail.com
            </p>
          </div>
          <div>
            <p
              style={{
                fontSize: "12px",
              }}
            >
              ⓒ 2024 신지영, 심규창, 최소윤, 김예찬
            </p>
          </div>
        </div>
  )
}
