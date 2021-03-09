import React, { useEffect } from "react";
import {
  Container,
  Row,
  Col,
  Card,
  FormGroup,
  FormLabel,
} from "react-bootstrap";

import Stepper from "react-stepper-horizontal";
import { FiThumbsUp } from "react-icons/fi";
import PaymentsNav from "../nav-bar/PaymentsNav";
export default function Step2() {

  //Pulling the Data from local storage
  let accountResponse = JSON.parse(localStorage.getItem("internalAccountResponse"));

  //Logic to set the current active Nav
  const currentNav = "/internalAccountStep1";
  useEffect(() => {
    localStorage.setItem("activeNav", currentNav);
  }, [currentNav]);
  return (
    <div>
      <Container>
        <PaymentsNav />
        <Stepper
          steps={[{ title: "Account Details" }, { title: "Confirmation" }]}
          activeStep={1}
        />
        <Row className="justify-content-center mt-4">
          <Card style={{ width: "50rem" }}>
            <Card.Title className="mt-2 text-success ml-3">
              <FiThumbsUp size="1.5em" /> Congratulations, You have successfully
              added your account.
            </Card.Title>
            <Card.Body>
              <Row>
                <Col>
                  <FormGroup>
                    <FormLabel>
                      <b>Account Number</b>
                    </FormLabel>
                    <p>{accountResponse.accountNumber}</p>
                  </FormGroup>
                </Col>
                <Col>
                  <FormGroup>
                    <FormLabel>
                      <b>Customer Id</b>
                    </FormLabel>
                    <p>{accountResponse.customerId}</p>
                  </FormGroup>
                </Col>
              </Row>
            </Card.Body>
          </Card>
        </Row>
      </Container>
    </div>
  );
}
