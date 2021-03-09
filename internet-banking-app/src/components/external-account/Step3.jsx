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

export default function Step3() {

    //Getting the Cached Data
    let cacheData = JSON.parse(localStorage.getItem("externalAccountStep2"));

  //Logic to set the current active Nav
  const currentNav = "/addBankStep1";
  useEffect(() => {
    localStorage.removeItem("externalAccountStep1");
    localStorage.setItem("activeNav", currentNav);
  }, [currentNav]);

  return (
    <div>
      <Container>
        <PaymentsNav />
        <Stepper
          steps={[
            { title: "Account Details" },
            { title: "Review Details" },
            { title: "Confirmation" },
          ]}
          activeStep={2}
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
                    <p>{cacheData.accountNumber}</p>
                  </FormGroup>
                </Col>
                <Col>
                  <FormGroup>
                    <FormLabel>
                      <b>Account Nick Name</b>
                    </FormLabel>
                    <p>{cacheData.accountNickName}</p>
                  </FormGroup>
                </Col>
                <Col>
                  <FormGroup>
                    <FormLabel>
                      <b>Account Type</b>
                    </FormLabel>
                    <p>{cacheData.accountType}</p>
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
