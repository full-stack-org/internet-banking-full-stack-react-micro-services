import React, { useEffect } from "react";
import {
  Container,
  Row,
  Card,
  Form,
  FormGroup,
  FormCheck,
  FormLabel,
  Button,
} from "react-bootstrap";
import { useForm } from "react-hook-form";
import * as Yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";
import { useNavigate } from "react-router-dom";
import Stepper from "react-stepper-horizontal";
import PaymentsNav from "../../nav-bar/PaymentsNav";
import * as fectAPIData from "../../redux/invoker/ServiceInvoker";
import serviceUrlData from "../../common/service-url.json";

export default function Step2() {
  //Logic to set the current active Nav
  let currentNav = "/transfer";
  useEffect(() => {
    localStorage.setItem("activeNav", currentNav);
  }, [currentNav]);

  //Setting up the error messages.
  const validationSchema = Yup.object({
    terms: Yup.bool().oneOf([true], "Please Accept Terms and Conditions"),
  });

  //Validating the From with Yup
  const { register, handleSubmit, errors } = useForm({
    resolver: yupResolver(validationSchema),
  });

  //Pulling up the data
  let cacheData = JSON.parse(localStorage.getItem("step1Details"));
  let profileData = JSON.parse(localStorage.getItem("profileData"));
  let jwtToken = localStorage.getItem("jwtToken");

  //setting up navigate functionality
  let navigate = useNavigate();

  //Submitting the service data
  const onSubmit = (values) => {
   
   let serviceInput = {
     data: {
      customerId: profileData.customerId,
      fromAccount: cacheData.fromAccount,
      paymentAmount: cacheData.paymentAmount,
      paymentDate: cacheData.paymentDate,
      toAccount: cacheData.toAccount
     },
     url: serviceUrlData.devURI+serviceUrlData.transferPaymentURI,
     token: jwtToken,
     callbackSuccess: processSuccess,
     callbackFailure: processFailure,
    };

    //Invoking Service
    fectAPIData.fetchAPIDataPost(serviceInput);

  };

  //Processing the Success Response
  function processSuccess(response){
    if (response.statusResponse.statusCode === 200) {
      console.log("Response from Service ", response)
      localStorage.setItem("step3Details", JSON.stringify(response));
      navigate('/transferstpe3')
    }
  }

  //Processing the Failure Response
  function processFailure(error){
    if(error !==null){
      navigate('/error')
    }
  }

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
          activeStep={1}
        />
        <Row className="justify-content-center mt-4">
          <Card style={{ width: "32rem" }}>
            <Card.Body>
              <Form onSubmit={handleSubmit(onSubmit)}>
                <FormGroup>
                  <FormLabel>
                    <b>From Account</b>
                  </FormLabel>
                  <p>{cacheData.fromAccount}</p>
                </FormGroup>
                <FormGroup>
                  <FormLabel>
                    <b>To Account</b>
                  </FormLabel>
                  <p>{cacheData.toAccount}</p>
                </FormGroup>
                <FormGroup>
                  <FormLabel>
                    <b>Transfer Date</b>
                  </FormLabel>
                  <p>{cacheData.paymentDate}</p>
                </FormGroup>
                <FormGroup>
                  <FormLabel>
                    <b>Payment Amount</b>
                  </FormLabel>
                  <p>{cacheData.paymentAmount}</p>
                </FormGroup>
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
                  className="mb-2"
                  onClick={() => navigate("/transfer")}
                >
                  Previous
                </Button>
                <Button
                  type="submit"
                  variant="danger"
                  className="mb-2 next-style"
                >
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
