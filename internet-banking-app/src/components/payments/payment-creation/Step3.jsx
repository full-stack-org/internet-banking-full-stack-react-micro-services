import React, { useEffect} from "react";
import { Container, Row, Card, Table } from "react-bootstrap";
import { FiThumbsUp } from "react-icons/fi";
import { AiFillPrinter } from "react-icons/ai";

import Stepper from "react-stepper-horizontal";
import PaymentsNav from "../../nav-bar/PaymentsNav";

export default function Step3() {
  //Logic to set the current active Nav
  let currentNav = "/transfer";
  useEffect(() => {
    localStorage.setItem("activeNav", currentNav);
  }, [currentNav]);

  //Pulling up the data
  let step1Data = JSON.parse(localStorage.getItem("step1Details"));
  let step3Data = JSON.parse(localStorage.getItem("step3Details"));

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
            <Card.Title className="mt-2 text-success text-center">
              <FiThumbsUp size="1.5em" className="mr-2" />
              Congratulations, Your payment creation successful.
            </Card.Title>
            <Card.Body>
              <Table striped bordered hover>
                <thead>
                  <tr>
                    <th>Reference Number</th>
                    <th>Payment Amount</th>
                    <th>From Account</th>
                    <th>To Account</th>
                    <th>Payment Date</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>{step3Data.confirmationNumber}</td>
                    <td>{step1Data.paymentAmount}</td>
                    <td>{step1Data.fromAccount}</td>
                    <td>{step1Data.toAccount}</td>
                    <td>{step1Data.paymentDate}</td>
                  </tr>
                </tbody>
              </Table>
              <p className="print-style">
                <AiFillPrinter />{" "}
                <a
                  onClick={() => window.print()}
                  href="#"
                  className="mr-3 link-style"
                >
                  Print
                </a>
                <a href="/scheduledPayments" className="link-style">
                  Scheduled Payments
                </a>
              </p>
            </Card.Body>
          </Card>
        </Row>
      </Container>
    </div>
  );
}
