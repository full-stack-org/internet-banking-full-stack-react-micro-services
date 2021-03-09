import React from "react";
import { Routes, Route } from "react-router-dom";
import ForgotPasswordStep1 from "../forgot-password/ForgotPasswordStep1";
import Login from "../login/Login";
import Step1 from "../new-account/Steps/Step1";
import Step2 from "../new-account/Steps/Step2";
import Step3 from "../new-account/Steps/Step3";
import Step4 from "../new-account/Steps/Step4";
import Step5 from "../new-account/Steps/Step5";
import ForGotPasswordStep2 from "../forgot-password/ForGotPasswordStep2";
import TransferStep1 from "../payments/payment-creation/Step1";
import TransferStep2 from "../payments/payment-creation/Step2";
import TransferStep3 from "../payments/payment-creation/Step3";
import ExternalAccountStep1 from "../external-account/Step1";
import ExternalAccountStep2 from "../external-account/Step2";
import ExternalAccountStep3 from "../external-account/Step3";
import ScheduledPayments from "../payments/scheduled-payments/ScheduledPayments";
import AccountTemplate from "../new-account/template/AccountTemplate";
import InternalAccountStep1 from "../internal-account/Step1";
import InternalAccountStep2 from "../internal-account/Step2";
import Profile from "../profile/profile";
import Error from "../common/Error";
import ForgotPasswordFailure from "../forgot-password/ForgotPasswordFailure";
import ForgotPasswordSuccess from "../forgot-password/ForgotPasswordSuccess";

export default function Routing() {
  //Pulling up the data from Redux
  let logged = false;

  //Verify whether customer logged in or not.
  let loggedUser = JSON.parse(localStorage.getItem("loggedUserData"));
  if (loggedUser !== null && loggedUser.authenticatedSuccessfully) {
    logged = true;
  }

  return (
    <div>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/forgot" element={<ForgotPasswordStep1 />} />
        <Route path="/accounttemplate" element={<AccountTemplate />} />
        <Route path="/accountstep1" element={<Step1 />} />
        <Route path="/accountstep2" element={<Step2 />} />
        <Route path="/accountstep3" element={<Step3 />} />
        <Route path="/accountstep4" element={<Step4 />} />
        <Route path="/accountstep5" element={<Step5 />} />
        <Route path="/forgotStep2" element={<ForGotPasswordStep2 />} />
        <Route path="/forgotFailure" element={<ForgotPasswordFailure />} />
        <Route path="/forgotSuccess" element={<ForgotPasswordSuccess />} />

        <Route path="/profile" element={<Profile />} />
        <Route path="/transfer" element={<TransferStep1 />} />
        <Route path="/transferstpe2" element={<TransferStep2 />} />
        <Route path="/transferstpe3" element={<TransferStep3 />} />
        <Route path="/addBankStep1" element={<ExternalAccountStep1 />} />
        <Route path="/addBankStep2" element={<ExternalAccountStep2 />} />
        <Route path="/addBankStep3" element={<ExternalAccountStep3 />} />
        <Route path="/scheduledPayments" element={<ScheduledPayments />} />
        <Route path="/error" element={<Error />} />
        <Route
          path="/internalAccountStep1"
          element={<InternalAccountStep1 />}
        />
        <Route
          path="/internalAccountStep2"
          element={<InternalAccountStep2 />}
        />
      </Routes>
    </div>
  );
}
