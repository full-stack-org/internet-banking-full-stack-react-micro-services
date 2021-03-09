import React from "react";
import { useNavigate } from "react-router-dom";

export default function ForgotPasswordFailure() {
  const navigate = useNavigate();

  function handleLogin() {
    localStorage.clear();
    navigate("/forgot");
  }

  return (
    <div>
      <div>
        <h2 className="text-center text-success">
          C We are experiencing the system problems. Please try again Later.
          Please call us at +91 8999999999. Click Here to Reset the Password{" "}
          <a onClick={handleLogin} href="#">Click Here</a> to login.
        </h2>
      </div>
    </div>
  );
}
