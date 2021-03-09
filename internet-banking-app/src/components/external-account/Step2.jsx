import React, { useEffect } from "react";
import {
  Container,
  Row,
  Card,
  Form,
  FormGroup,
  FormLabel,
  FormCheck,
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

export default function Step2() {
  //Getting the Cached Data
  let cacheData = JSON.parse(localStorage.getItem("externalAccountStep1"));
  let jwtToken = localStorage.getItem("jwtToken");

  //Logic to set the current active Nav
  const currentNav = "/addBankStep1";
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

  //setting up navigate functionality
  let navigate = useNavigate();

  //Submitting the service data
  const onSubmit = (values) => {
    if (values.terms !== null) {
      let serviceInput = {
        data: cacheData,
        url: serviceUrlData.devURI + serviceUrlData.addExternalAccountURI,
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
      localStorage.setItem("externalAccountStep2", JSON.stringify(response));
      navigate("/addBankStep3");
    } else {
      if (response.statusResponse.statusMessage === 500) {
        navigate("/error");
      }

      localStorage.setItem("bankStatus", response.statusResponse.statusMessage);
      navigate("/addBankStep1");
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
          activeStep={1}
        />
        <Row className="justify-content-center mt-4">
          <Card style={{ width: "32rem" }}>
            <Card.Body>
              <Form onSubmit={handleSubmit(onSubmit)}>
                <FormGroup>
                  <FormLabel>
                    <b>Bank Name</b>
                  </FormLabel>
                  <p>{cacheData.accountNickName}</p>
                </FormGroup>
                <FormGroup>
                  <FormLabel>
                    <b>Account Number</b>
                  </FormLabel>
                  <p>{cacheData.accountNumber}</p>
                </FormGroup>
                <FormGroup>
                  <FormLabel>
                    <b>Account Type</b>
                  </FormLabel>
                  <p>{cacheData.accountType}</p>
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
                  onClick={() => navigate("/addBankStep1")}
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
