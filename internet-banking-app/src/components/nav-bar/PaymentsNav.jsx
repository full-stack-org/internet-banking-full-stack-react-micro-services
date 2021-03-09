import React, { useEffect } from "react";
import { Nav } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

export default function PaymentsNav() {
  //Pulling up the data from Redux
  const loggedUser = localStorage.getItem("loggedUserData");

  //setting up navigate functionality
  let navigate = useNavigate();

  //If customer is logged in then proceed otherwise taking them to ogin Page
  //Rediirecting to logout if not login

  useEffect(()=>{
    if (loggedUser === null) {
      navigate('/');
    }
  },[])


  let activeTab = localStorage.getItem("activeNav");

  return (
    <div>
      <Nav fill variant="tabs" defaultActiveKey={activeTab} className="mt-3">
        <Nav.Item>
          <Nav.Link href="/transfer">Transfers</Nav.Link>
        </Nav.Item>
        <Nav.Item>
          <Nav.Link href="/scheduledPayments">Scheduled Payments</Nav.Link>
        </Nav.Item>
        <Nav.Item>
          <Nav.Link href="/internalAccountStep1">
            Add Internal Bank Account
          </Nav.Link>
        </Nav.Item>
        <Nav.Item>
          <Nav.Link href="/addBankStep1">Add External Bank Account</Nav.Link>
        </Nav.Item>
      </Nav>
    </div>
  );
}
