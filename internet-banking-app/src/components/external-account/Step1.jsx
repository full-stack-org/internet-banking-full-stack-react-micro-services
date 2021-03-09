import React, { useEffect, useState } from "react";
import {
  Container,
  Row,
  Card,
  Form,
  FormGroup,
  FormControl,
  FormLabel,
  FormCheck,
  Button,
} from "react-bootstrap";
import { useForm } from "react-hook-form";
import * as Yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";
import { useNavigate } from "react-router-dom";
import Stepper from "react-stepper-horizontal";
import * as fectAPIData from "../redux/invoker/ServiceInvoker";
import serviceUrlData from "../common/service-url.json";
import PaymentsNav from "../nav-bar/PaymentsNav";

export default function Step1() {
  //Pulling the Data from local storage
  let jwtToken = localStorage.getItem("jwtToken");
  let profileData = JSON.parse(localStorage.getItem("profileData"));

  //Logic to set the current active Nav
  const currentNav = "/addBankStep1";
  useEffect(() => {
    localStorage.removeItem("bankStatus");
    localStorage.removeItem("externalAccountStep2")
    localStorage.setItem("activeNav", currentNav);
  }, [localStorage.setItem("activeNav", currentNav)]);

  //Setting up the error messages.
  const validationSchema = Yup.object({
    routingNumber: Yup.string().required("Routing Number is Mandatory"),
    accountNumber: Yup.string().required("Account Number is Mandatory"),
    repeatAccountNumber: Yup.string()
      .required("Repeat Account Number Required")
      .oneOf([Yup.ref("accountNumber"), null], "Account Numbers must match"),
    accountType: Yup.string().required("Account Type Mandatory"),
  });

  //Validating the From with Yup
  const { register, handleSubmit, errors } = useForm({
    resolver: yupResolver(validationSchema),
  });

  //setting up navigate functionality
  let navigate = useNavigate();

  //Submitting the service data
  const onSubmit = (values) => {
    let serviceInput = {
      customerId: profileData.customerId,
      accountNumber: values.accountNumber,
      accountNickName: bankName,
      accountType: values.accountType,
    };

    localStorage.setItem("externalAccountStep1", JSON.stringify(serviceInput));
    navigate("/addBankStep2");
  };

  let [bankName, setBankName] = useState("");

  //Method to Validate the routing number
  function validateRoutingNumber(e) {
    let serviceInput = {
      data: {
        routingNumber: e.target.value,
      },
      url: serviceUrlData.devURI + serviceUrlData.validateRoutingNumberURI,
      token: jwtToken,
      callbackSuccess: processSuccess,
      callbackFailure: processFailure,
    };

    //Invoking Service
    fectAPIData.fetchAPIDataPost(serviceInput);
  }

  //Processing the Success Response
  function processSuccess(response) {
    if (response.statusResponse.statusCode === 200) {
      setBankName(response.bankName);
    } else {
      setBankName(response.statusResponse.statusMessage);
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
        <Stepper
          steps={[
            { title: "Account Details" },
            { title: "Review Details" },
            { title: "Confirmation" },
          ]}
          activeStep={0}
        />
        <Row className="justify-content-center mt-4">
          <Card style={{ width: "30rem" }}>
            <Card.Body>
              <p className="text-center text-danger">
                {localStorage.getItem("bankStatus") !== null
                  ? localStorage.getItem("bankStatus")
                  : ""}
              </p>
              <Form onSubmit={handleSubmit(onSubmit)}>
                <FormGroup>
                  <FormLabel>Routing Number</FormLabel>
                  <FormControl
                    type="number"
                    placeholder="Enter Routing Number"
                    name="routingNumber"
                    ref={register}
                    onBlur={validateRoutingNumber}
                    className={errors.routingNumber ? " is-invalid" : ""}
                  />
                  <div className="invalid-feedback">
                    {errors.routingNumber?.message}
                  </div>
                  {<p className="mt-2 text-danger">{bankName}</p>}
                </FormGroup>
                <FormGroup>
                  <FormLabel>Account Number</FormLabel>
                  <FormControl
                    type="number"
                    placeholder="Enter Account Number"
                    name="accountNumber"
                    ref={register}
                    className={errors.accountNumber ? " is-invalid" : ""}
                  />
                  <div className="invalid-feedback">
                    {errors.accountNumber?.message}
                  </div>
                </FormGroup>
                <FormGroup>
                  <FormLabel>Repeat Account Number</FormLabel>
                  <FormControl
                    type="number"
                    placeholder="Enter Repeat Account Number"
                    name="repeatAccountNumber"
                    ref={register}
                    className={errors.repeatAccountNumber ? " is-invalid" : ""}
                  />
                  <div className="invalid-feedback">
                    {errors.repeatAccountNumber?.message}
                  </div>
                </FormGroup>
                <FormLabel>Account Type</FormLabel>
                <FormGroup>
                  <FormCheck
                    type="radio"
                    label="Savings"
                    value="Savings"
                    inline
                    name="accountType"
                    ref={register}
                    className={errors.accountType ? " is-invalid" : ""}
                  ></FormCheck>
                  <FormCheck
                    type="radio"
                    label="Current"
                    value="Current"
                    inline
                    name="accountType"
                    ref={register}
                    className={errors.accountType ? " is-invalid" : ""}
                  ></FormCheck>
                  <div className="invalid-feedback">
                    {errors.accountType?.message}
                  </div>
                </FormGroup>
                <Button type="submit" block variant="danger" className="mb-2">
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
