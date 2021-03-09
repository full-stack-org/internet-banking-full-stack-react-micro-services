import React from "react";
import { useNavigate } from "react-router-dom";

export default function ForgotPasswordSuccess() {
  const navigate = useNavigate();
  function handleLogin() {
    localStorage.clear();
    navigate("/");
  }
  return (
    <div>
      <h2 className="text-center text-danger">
        Congratulation your password updated Successfully.{" "}
        <a onClick={handleLogin} href="#">Click Here</a> to login.
      </h2>
    </div>
  );
}
