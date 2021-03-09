import React, { useState } from "react";
import {
  Container,
  Row,
  Card,
  Form,
  FormGroup,
  FormControl,
  FormLabel,
  Button,
} from "react-bootstrap";
import { useForm } from "react-hook-form";
import * as Yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";
import { useNavigate } from "react-router-dom";
import serviceUrlData from "../common/service-url.json";
import * as fectAPIData from "../redux/invoker/ServiceInvoker";

export default function ForgotPasswordStep1() {
  //Setting up the error messages.
  const validationSchema = Yup.object({
    custId: Yup.string()
      .required("Cust Id is Mandatory")
      .min(5, "Must be exactly 5 digits")
      .max(9, "Must be exactly 9 digits"),
  });

  //Validating the From with Yup
  const { register, handleSubmit, errors } = useForm({
    resolver: yupResolver(validationSchema),
  });

  //setting up navigate functionality
  let navigate = useNavigate();

  //Submitting the service data
  const onSubmit = (values) => {
    if (values.custId !== null) {
      let profileRequest = {
        data: {
          customerId: values.custId,
        },
        url: serviceUrlData.devURI + serviceUrlData.findByUserIdURI,
        callbackSuccess: renderSuccessPage,
        callbackFailure: renderTheErrorPage,
      };

      //Invoking Profile Invocation
      fectAPIData.fetchAPIDataPostWithOutJWTToken(profileRequest);
    }
  };

  const [message, setMessage] = useState("");

  //Rendering success page
  function renderSuccessPage(response) {
    if (response !== null && response.statusResponse.statusCode === 200) {
      localStorage.setItem('cif',response.customerId)
      navigate("/forgotStep2");
    } else {
      setMessage(response.statusResponse.statusMessage);
    }
  }

  //Rendering the error page
  function renderTheErrorPage(error) {
    if (error !== null) {
      navigate("/forgotFailure");
    }
  }

  return (
    <div>
      <Container>
        <Row className="justify-content-center mt-4">
          <Card style={{ width: "22rem" }}>
            <p className="text-center text-danger">{message}</p>
            <Card.Title className="text-center mt-3">
              Forgot Password
            </Card.Title>
            <Card.Body>
              <Form onSubmit={handleSubmit(onSubmit)}>
                <FormGroup>
                  <FormLabel>Customer Id</FormLabel>
                  <FormControl
                    type="number"
                    placeholder="Enter your customer id"
                    name="custId"
                    ref={register}
                    className={errors.custId ? " is-invalid" : ""}
                  />
                  <div className="invalid-feedback">
                    {errors.custId?.message}
                  </div>
                </FormGroup>
                <Button type="submit" block variant="danger" className="mb-4">
                  Verify
                </Button>
              </Form>
            </Card.Body>
          </Card>
        </Row>
      </Container>
    </div>
  );
}
