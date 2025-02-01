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

  const navigator = useNavigate();

  const {login} = useAuthStore();


  useEffect(() => {
    if (accessToken && expiration) {
      const expires = new Date(Date.now() + Number(expiration));
      console.log(expires);
      setCookies("token", accessToken, {
        path: "/",
        expires,
      });

      login({
        token: accessToken,
      });

      navigator("/");
    } else navigator("/auth/signup/preson");
  }, [accessToken, expiration, navigator, setCookies, login]);

  return <></>;
}