import React, { useEffect, useState } from "react";
import { Container, Row, Card, Table } from "react-bootstrap";
import { AiFillPrinter } from "react-icons/ai";
import { useNavigate } from "react-router-dom";
import PaymentsNav from "../../nav-bar/PaymentsNav";
import * as fectAPIData from "../../redux/invoker/ServiceInvoker";
import serviceUrlData from "../../common/service-url.json";

export default function ScheduledPayments() {
  //Logic to set the current active Nav
  const currentNav = "/scheduledPayments";

  //Pulling the Data from local storage
  let profileData = JSON.parse(localStorage.getItem("profileData"));
  let jwtToken = localStorage.getItem("jwtToken");

  //Storing the Scheduled transfers in State
  let [scheduledTransfersResponse, setScheduledTransfersResponse] = useState(
    null
  );

  let navigate = useNavigate();

  useEffect(() => {
    if (profileData !== null) {
      localStorage.setItem("activeNav", currentNav);

      let serviceInput = {
        data: {
          customerId: profileData.customerId,
        },
        url: serviceUrlData.devURI + serviceUrlData.scheduledPaymentsURI,
        token: jwtToken,
        callbackSuccess: processSuccess,
        callbackFailure: processFailure,
      };

      //Invoking Service
      fectAPIData.fetchAPIDataPost(serviceInput);
    } else {
      navigate("/");
    }
  }, [localStorage.setItem("activeNav", currentNav)]);

  //Processing the Success Response
  function processSuccess(response) {
    if (response.statusResponse.statusCode === 200) {
      setScheduledTransfersResponse(response);
    }
  }

  //Processing the Failure Response
  function processFailure(error) {
    if (error !== null) {
      navigate("/error");
    }
  }

  return (
    <div>
      <Container>
        <PaymentsNav />
        <Row className="justify-content-center mt-4">
          <Card style={{ width: "50rem" }}>
            <Card.Body>
              <Card.Title className="text-center">
                Scheduled Payments
              </Card.Title>
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
                  {scheduledTransfersResponse !== null ? (
                    scheduledTransfersResponse.scheduledPaymentsBeansList.map(
                      (payments, index) => (
                        <tr key={index}>
                          <td>{payments.referenceNumber}</td>
                          <td>{payments.paymentAmount}</td>
                          <td>{payments.fromAccountNumber}</td>
                          <td>{payments.toAccountNumber}</td>
                          <td>{payments.paymentDate}</td>
                        </tr>
                      )
                    )
                  ) : (
                    <p className="text-center">No Scheduled Transfers</p>
                  )}
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
              </p>
            </Card.Body>
          </Card>
        </Row>
      </Container>
    </div>
  );
}
