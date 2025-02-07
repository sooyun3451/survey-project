import React, { useEffect } from "react";
import { useCookies } from "react-cookie";
import { useNavigate, useSearchParams } from "react-router-dom";
import useAuthStore from "../../store/auth.store";

export default function SnsSuccess() {
  const [cookies, setCookies] = useCookies(["token"]);
  const token = cookies.token;

  const [queryParam] = useSearchParams();
  const accessToken = queryParam.get("accessToken");
  const expiration = queryParam.get("expiration");
  const userName = queryParam.get("userName");
  const userCode = queryParam.get("userCode");

  const navigator = useNavigate();

  const { login } = useAuthStore();

  useEffect(() => {
    if (accessToken && expiration) {
      const expires = new Date(Date.now() + Number(expiration));
      console.log(expires);

      setCookies("token", accessToken, {
        path: "/",
        expires,
      });

      if (userName && userCode) {
        localStorage.setItem("userName", userName);
        localStorage.setItem("userCode", userCode);
      }

      login({
        token: accessToken,
      });

      navigator("/");
    } else navigator("/auth/signup/person");
  }, [accessToken, expiration, navigator, setCookies, login]);

  return <></>;
}
