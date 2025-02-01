import React, { useEffect } from "react";
import { useCookies } from "react-cookie";
import { useNavigate, useSearchParams } from "react-router-dom";

export default function SnsSuccess() {
  const [cookies, setCookies] = useCookies(["token"]);
  const token = cookies.token;

  const [queryParam] = useSearchParams();
  const accessToken = queryParam.get("accessToken");
  const expiration = queryParam.get("expiration");
  const userName = queryParam.get("userName");
  const userCode = queryParam.get("userCode");

  const navigator = useNavigate();


  useEffect(() => {
    if (accessToken && expiration) {
      const expires = new Date(Date.now() + Number(expiration));
      console.log(expires);
      setCookies("token", accessToken, {
        path: "/",
        expires,
      });

      localStorage.setItem("userName", userName);
      localStorage.setItem("userCode", userCode);

      navigator("/");
    } else navigator("/auth/signup/preson");
  }, [accessToken, expiration, navigator, setCookies]);

  return <></>;
}