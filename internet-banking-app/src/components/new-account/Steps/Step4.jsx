import React from "react";
import {
  Container,
  Row,
  Card,
  Form,
  Col,
  FormLabel,
  FormGroup,
  FormCheck,
  Button,
} from "react-bootstrap";
import Stepper from "react-stepper-horizontal";
import { useForm } from "react-hook-form";
import * as Yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";
import { useNavigate } from "react-router-dom";
import * as fectAPIData from "../../redux/invoker/ServiceInvoker";
import serviceUrlData from "../../common/service-url.json";

export default function Step4() {

 //Pulling up the data
 let step1Data = JSON.parse(localStorage.getItem("step1Details"));
 let step2Data = JSON.parse(localStorage.getItem("step2Details"));
 let step3Data = JSON.parse(localStorage.getItem("step3Details"));

  //Setting up the error messages.
  const validationSchema = Yup.object({
    terms: Yup.bool().oneOf([true], "Please Accept Terms and Conditions"),
  });

  //Validating the From with Yup
  const { register, handleSubmit, errors } = useForm({
    resolver: yupResolver(validationSchema),
  });

  //setting up navigate functionality
  let navigate = useNavigate();

  //Submitting the service data
  const onSubmit = (values) => {
    if (values.terms !== null) {

      let serviceInput = {
        data: {
          firstName:step1Data.firstName,
          lastName: step1Data.lastName,
          gender:step1Data.gender,
          password:step1Data.password,
          dateOfBirth:step1Data.dateOfBirth,
          aadharNumber:step2Data.aadhar,
          panNumber:step2Data.pan,
          accountType:step3Data.accountType,
        },
        url: serviceUrlData.devURI+serviceUrlData.openInternalAccountURI,
        callbackSuccess: processSuccess,
        callbackFailure: processFailure,
       };
   
       //Invoking Service
       fectAPIData.fetchAPIDataPostWithOutJWTToken(serviceInput);
    }
  };

  //Processing the Success Response
  function processSuccess(response) {
    if (response.statusResponse.statusCode === 200) {   
      localStorage.removeItem("step1Details")
      localStorage.removeItem("step2Details")
      localStorage.removeItem("step3Details")
      localStorage.setItem('accountOpenResponse',JSON.stringify(response))  
      navigate("/accountstep5");
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
      <Stepper
        steps={[
          { title: "Basic Details" },
          { title: "Secure Details" },
          { title: "Account Details" },
          { title: "Review Your Details" },
          { title: "Confirmation" },
        ]}
        activeStep={3}
      />
      <Container>
        <Row className="justify-content-center mt-4">
          <Card style={{ width: "35rem" }}>
            <Card.Body>
              <Form onSubmit={handleSubmit(onSubmit)}>
                <h4> Personal Details </h4>
                <Form.Row className="mt-3">
                  <Form.Group as={Col}>
                    <Form.Label>
                      <b>First Name</b>
                    </Form.Label>
                    <p>{step1Data.firstName}</p>
                  </Form.Group>

                  <Form.Group as={Col}>
                    <Form.Label>
                      <b>Last Name</b>
                    </Form.Label>
                    <p>{step1Data.lastName}</p>
                  </Form.Group>
                  <Form.Group as={Col}>
                    <Form.Label>
                      <b>Gender</b>
                    </Form.Label>
                    <p>{step1Data.gender}</p>
                  </Form.Group>
                </Form.Row>
                <h4> Secure Details </h4>
                <Form.Row className="mt-3">
                  <Form.Group as={Col}>
                    <Form.Label>
                      <b>Aadhar Number</b>
                    </Form.Label>
                    <p>{step2Data.aadhar}</p>
                  </Form.Group>

                  <Form.Group as={Col}>
                    <Form.Label>
                      <b>Pan Number</b>
                    </Form.Label>
                    <p>{step2Data.pan}</p>
                  </Form.Group>
                </Form.Row>
                <h4> Account Details </h4>
                <Form.Row className="mt-3">
                  <Form.Group as={Col}>
                    <Form.Label>
                      <b>Account Type</b>
                    </Form.Label>
                    <p>{step3Data.accountType}</p>
                  </Form.Group>
                </Form.Row>
                <FormGroup className="form-inline mb-4">
                  <FormCheck
                    name="terms"
                    ref={register}
                    className={errors.terms ? " is-invalid" : ""}
                  ></FormCheck>
                  <FormLabel>
                    I have read all terms and conditions and ready to save my
                    data.
                  </FormLabel>
                  <div className="invalid-feedback">
                    {errors.terms?.message}
                  </div>
                </FormGroup>
                <Button
                  type="button"
                  variant="danger"
                  className="mb-2 mr-5"
                  onClick={() => navigate("/accountstep3")}
                >
                  Previous
                </Button>
                <Button type="submit" variant="danger" className="mb-2 ml-5">
                  Next
                </Button>
              </Form>
            </Card.Body>
          </Card>
        </Row>
      </Container>
    </div>
  );
}
