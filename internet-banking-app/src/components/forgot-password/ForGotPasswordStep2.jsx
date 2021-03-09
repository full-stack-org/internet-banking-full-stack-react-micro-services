import React from "react";
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

export default function ForGotPasswordStep2() {
  //Setting up the error messages.
  const validationSchema = Yup.object({
    password: Yup.string().required("Password is Required"),
    confirmPassword: Yup.string()
      .required("Confirm Password Required")
      .oneOf([Yup.ref("password"), null], "Passwords must match"),
  });

  //Validating the From with Yup
  const { register, handleSubmit, errors } = useForm({
    resolver: yupResolver(validationSchema),
  });

  //setting up navigate functionality
  let navigate = useNavigate();

  //Submitting the service data
  const onSubmit = (values) => {
    let profileRequest = {
      data: {
        customerId: localStorage.getItem('cif'),
        password: values.confirmPassword
      },
      url: serviceUrlData.devURI + serviceUrlData.updatedPasswordURI,
      callbackSuccess: renderSuccessPage,
      callbackFailure: renderTheErrorPage,
    };

    //Invoking Profile Invocation
    fectAPIData.fetchAPIDataPostWithOutJWTToken(profileRequest);
  }

  //Rendering success page
  function renderSuccessPage(response) {
    if (response !== null && response.statusResponse.statusCode === 200) {
      navigate("/forgotSuccess");
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
            <Card.Body>
              <Form onSubmit={handleSubmit(onSubmit)}>
                <FormGroup>
                  <FormLabel>New Password</FormLabel>
                  <FormControl
                    type="password"
                    placeholder="Enter your New Password"
                    name="password"
                    ref={register}
                    className={errors.password ? " is-invalid" : ""}
                  />
                  <div className="invalid-feedback">
                    {errors.password?.message}
                  </div>
                </FormGroup>
                <FormGroup>
                  <FormLabel>Re-Type New Password</FormLabel>
                  <FormControl
                    type="password"
                    placeholder="Enter your Confirmation Password"
                    name="confirmPassword"
                    ref={register}
                    className={errors.confirmPassword ? " is-invalid" : ""}
                  />
                  <div className="invalid-feedback">
                    {errors.confirmPassword?.message}
                  </div>
                </FormGroup>
                <Button type="submit" block variant="danger" className="mb-4">
                  Update Password
                </Button>
              </Form>
            </Card.Body>
          </Card>
        </Row>
      </Container>
    </div>
  );
}
