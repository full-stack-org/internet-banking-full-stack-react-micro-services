import React from "react";
import { Container, Row, Card, Table } from "react-bootstrap";
import Stepper from "react-stepper-horizontal";
import { FiThumbsUp } from 'react-icons/fi';

export default function Step5() {

  let accountResponse = JSON.parse(localStorage.getItem("accountOpenResponse"));

  return (
    <div>
      <Stepper
        steps={[
          { title: "Basic Details" },
          { title: "Secure Details" },
          { title: "Account Details" },
          { title: "Review Your Details" },
          { title: "Confirmation" },
        ]}
        activeStep={4}
      />
      <Container>
        <Row className="justify-content-center mt-4">
          <Card style={{ width: "49rem" }}>
            <Card.Body>           
              <Card.Title className="text-success">
              <FiThumbsUp size="1.5em"/> Congratulations you have successfully opened new account. Below
                are details.
              </Card.Title>
              <Table striped bordered>
                <thead>
                  <tr>
                    <th>Account Number</th>
                    <th>Customer Id</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>{accountResponse.accountNumber}</td>
                    <td>{accountResponse.customerId}</td>
                  </tr>
                </tbody>
              </Table>
              <p>
                <h4>
                  To do the money transfer <a href="/">Click Here</a>.
                </h4>
              </p>
            </Card.Body>
          </Card>
        </Row>
      </Container>
    </div>
  );
}
