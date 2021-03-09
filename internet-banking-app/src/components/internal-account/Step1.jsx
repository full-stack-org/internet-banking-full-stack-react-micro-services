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
import PaymentsNav from "../nav-bar/PaymentsNav";
import * as fectAPIData from "../redux/invoker/ServiceInvoker";
import serviceUrlData from "../common/service-url.json";

export default function Step1() {

  //Pulling the Data from local storage
  let profileData = JSON.parse(localStorage.getItem("profileData"));
  let jwtToken = localStorage.getItem("jwtToken");

  //setting up navigate functionality
  let navigate = useNavigate();


  //Logic to set the current active Nav
  const currentNav = "/internalAccountStep1";
  useEffect(() => {
    if (profileData !== null) {
    localStorage.removeItem('internalAccountResponse')
    localStorage.setItem("activeNav", currentNav);
    } else{
      navigate('/')
    }
  }, [localStorage.setItem("activeNav", currentNav)]);

  //Setting up the error messages.
  const validationSchema = Yup.object({
    accountType: Yup.string().required('Please select Account Type.'),
  });

  //Validating the From with Yup
  const { register, handleSubmit, errors } = useForm({
    resolver: yupResolver(validationSchema),
  });
  
  //Submitting the service data
  const onSubmit = (values) => {
    if (values.accountType !== null) {
      let serviceInput = {
        data: {
          firstName:profileData.firstName,
          lastName: profileData.lastName,
          gender:profileData.gender,
          dateOfBirth:profileData.dateOfBirth,
          aadharNumber:profileData.aadharNumber,
          panNumber:profileData.panNumber,
          accountType:values.accountType,
        },
        url: serviceUrlData.devURI + serviceUrlData.openInternalAccountURI,
        token: jwtToken,
        callbackSuccess: processSuccess,
        callbackFailure: processFailure,
      };
  
      //Invoking Service
      fectAPIData.fetchAPIDataPost(serviceInput);      
    }
  };

  //Processing the Success Response
  function processSuccess(response) {
    if (response.statusResponse.statusCode === 200) {   
      localStorage.setItem('internalAccountResponse',JSON.stringify(response))  
      navigate("/internalAccountStep2");
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
          steps={[{ title: "Account Details" }, { title: "Confirmation" }]}
          activeStep={0}
        />

        <Row className="justify-content-center mt-4">
          <Card style={{ width: "35rem" }}>
            <Card.Body>
              <Form onSubmit={handleSubmit(onSubmit)}>
                <FormGroup>
                  <FormLabel>
                    <b>Aadhar Number</b>
                  </FormLabel>
                  <p>{profileData !== null ?profileData.aadharNumber:''}</p>
                </FormGroup>
                <FormGroup>
                  <FormLabel>
                    <b>Pan Number</b>
                  </FormLabel>
                  <p>{profileData !== null ? profileData.panNumber:''}</p>
                </FormGroup>
                <FormLabel className="font-weight-bold">Account Type</FormLabel>
                <FormGroup>
                  <FormCheck
                    type="radio"
                    label="Savings"
                    value="savings"
                    inline
                    name="accountType"
                    ref={register}
                    className={errors.accountType ? " is-invalid" : ""}
                  ></FormCheck>
                  <FormCheck
                    type="radio"
                    label="Current"
                    value="current"
                    inline
                    name="accountType"
                    ref={register}
                    className={errors.accountType ? " is-invalid" : ""}
                  ></FormCheck>
                   <div className="invalid-feedback">
                    {errors.accountType?.message}
                  </div>
                </FormGroup>

                <Button
                  type="submit"
                  variant="danger"
                  className="mb-2 ml-5"
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
