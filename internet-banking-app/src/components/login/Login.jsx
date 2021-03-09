import React, { useState, useEffect } from "react";
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
import loginImage from "../common/images/login.jpg";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import serviceUrlData from "../common/service-url.json";
import authenticateUser from "../redux/invoker/LoginInvoker";
import * as fectAPIData from "../redux/invoker/ServiceInvoker";

export default function Login() {
  //Setting up the error messages.
  const validationSchema = Yup.object({
    customerId: Yup.string().required("Customer Id is Mandatory"),
    password: Yup.string().required("Password is Mandatory"),
  });

  //Validating the From with Yup
  const { register, handleSubmit, errors } = useForm({
    resolver: yupResolver(validationSchema),
  });

  //Clearing all the data persisted upon login page loading.
  useEffect(() => {
    localStorage.clear();
  }, [localStorage.clear()]);

  //setting up navigate functionality
  let navigate = useNavigate();

  //Setting up useDispatch for Redux hit
  let dispatch = useDispatch();

  //Setting up the error for authentication failure
  const [errorMessage, setErrorMessage] = useState(null);

  //Submitting Form Data
  const onSubmit = (values) => {
    setErrorMessage(null);
    let loginRequest = {
      url: serviceUrlData.devURI + serviceUrlData.loginURI,
      data: values,
      callbackSuccess: renderLoggedCustomer,
      callbackFailure: renderTheErrorPage,
    };
    //Invoking the API
    dispatch(authenticateUser(loginRequest));
  };

  //Rendering the authenticated Customer
  function renderLoggedCustomer(response) {
    if (response !== null) {
      localStorage.setItem("loggedUserData", JSON.stringify(response));
      if (
        response.statusResponse.statusCode === 200 &&
        response.authenticatedSuccessfully
      ) {
        localStorage.setItem("jwtToken", response.jwtToken);
        let profileRequest = {
          data: {
            customerId: response.customerId,
          },
          url: serviceUrlData.devURI + serviceUrlData.profileDataURI,
          token: response.jwtToken,
          callbackSuccess: renderSuccessPage,
          callbackFailure: renderTheErrorPage,
        };

        //Invoking Profile Invocation
        fectAPIData.fetchAPIDataPost(profileRequest);
      } else {
        setErrorMessage("Invalid Credentials");
      }
    }
  }

  //Rendering success page. If user already exists then redirect him to open account page
  function renderSuccessPage(response) {
    if (response !== null) {
      localStorage.setItem("profileData", JSON.stringify(response));
      if (localStorage.getItem("userType") === "existingUser") {
        navigate("/internalAccountStep1");
      } else {
        navigate("/transfer");
      }
    }
  }

  //Rendering the error page
  function renderTheErrorPage(error) {
    setErrorMessage("Experencing Service Issues.");
  }

  return (
    <div>
      <Container>
        <Row className="justify-content-center mt-4">
          <Card style={{ width: "22rem" }}>
            <Card.Img variant="top" src={loginImage} />
            {errorMessage !== null ? (
              <p className="error-meessage"> {errorMessage}.</p>
            ) : (
              " "
            )}
            <Card.Body>
              <Form onSubmit={handleSubmit(onSubmit)}>
                <FormGroup>
                  <FormLabel>Customer Id</FormLabel>
                  <FormControl
                    type="number"
                    placeholder="Enter your customer id"
                    name="customerId"
                    ref={register}
                    className={errors.customerId ? " is-invalid" : ""}
                  />
                  <div className="invalid-feedback">
                    {errors.customerId?.message}
                  </div>
                </FormGroup>
                <FormGroup>
                  <FormLabel>Password</FormLabel>
                  <FormControl
                    type="password"
                    placeholder="Enter your Password"
                    name="password"
                    ref={register}
                    className={errors.password ? " is-invalid" : ""}
                  />
                  <div className="invalid-feedback">
                    {errors.password?.message}
                  </div>
                </FormGroup>
                <Button type="submit" block variant="danger" className="mb-4">
                  Login
                </Button>
                <a href="/forgot" className="mr-2">
                  Forgot Password
                </a>
                <a href="/accounttemplate" className="ml-5">
                  Open New Acount
                </a>
              </Form>
            </Card.Body>
          </Card>
        </Row>
      </Container>
    </div>
  );
}
