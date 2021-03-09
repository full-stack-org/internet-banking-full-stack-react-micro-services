import React, { useState, useEffect } from "react";
import {
  Alert,
  Container,
  Row,
  Col,
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
import DatePicker from "react-datepicker";
import moment from "moment";
import { useNavigate } from "react-router-dom";
import * as fectAPIData from "../redux/invoker/ServiceInvoker";
import serviceUrlData from "../common/service-url.json";

export default function Profile() {
  //Setting up the current date to show in calendar
  const [startDate, setStartDate] = useState(new Date());

  //Pulling up the data
  let profileData = JSON.parse(localStorage.getItem("profileData"));
  let jwtToken = localStorage.getItem("jwtToken");

  useEffect(() => {
    if (profileData !== null) {
      setStartDate(moment(profileData.dateOfBirth).toDate());
    }
  }, []);

  //Setting up the error messages.
  const validationSchema = Yup.object({
    firstName: Yup.string().required("First Name is Mandatory"),
    lastName: Yup.string().required("Last Name is Mandatory"),
    gender: Yup.string().required("Gender is Mandatory"),
    aadharNumber: Yup.string().required("Aadhar Number is Mandatory"),
    panNumber: Yup.string().required("Pan Number is Mandatory"),
  });

  //Validating the From with Yup
  const { register, handleSubmit, errors } = useForm({
    resolver: yupResolver(validationSchema),
  });

  //Submitting the service data
  const onSubmit = (values) => {
    let date = moment(startDate).format("YYYY-MM-DD");
    console.log("Date Is ", date);
    let serviceInput = {
      data: {
        id: profileData.id,
        firstName: values.firstName,
        lastName: values.lastName,
        gender: values.gender,
        dateOfBirth: date,
        aadharNumber: values.aadharNumber,
        panNumber: values.panNumber,
      },
      url: serviceUrlData.devURI + serviceUrlData.updateProfileURI,
      token: jwtToken,
      callbackSuccess: processSuccess,
      callbackFailure: processFailure,
    };
    //Invoking Service
    fectAPIData.fetchAPIDataPost(serviceInput);
  };

  const [profileUpdated, setProfileUpdated] = useState(false);

  //Processing the Success Response
  function processSuccess(response) {
    if (response.statusResponse.statusCode === 200) {
      setProfileUpdated(true);

      //Getting the Latest profile after updation
      let profileRequest = {
        data: {
          customerId: profileData.customerId,
        },
        url: serviceUrlData.devURI + serviceUrlData.profileDataURI,
        token: jwtToken,
        callbackSuccess: renderSuccessPage,
        callbackFailure: processFailure,
      };
      //Invoking Profile Invocation
      fectAPIData.fetchAPIDataPost(profileRequest);
    }
  }

  //Updating the Profile Response after Profile Update
  function renderSuccessPage(response) {
    if (response !== null) {
      localStorage.setItem("profileData", JSON.stringify(response));
    }
  }

  let navigate = useNavigate();

  //Processing the Failure Response
  function processFailure(error) {
    if (error !== null) {
      navigate("/error");
    }
  }

  return (
    <div>
      <Container>
        <Row className="justify-content-center mt-2 h-50">
          {profileUpdated ? (
            <Alert
              variant="success"
              onClose={() => setProfileUpdated(false)}
              dismissible
            >
              <p className="text-center half-width">
                Profile Updated Successfully
              </p>
            </Alert>
          ) : (
            ""
          )}
        </Row>
        <Row className="justify-content-center mt-2">
          <Card style={{ width: "30rem" }}>
            <Card.Body>
              <Card.Title className="text-center text-danger mb-2">
                Edit your profile {profileData.firstName} {profileData.lastName}
              </Card.Title>
              <Form onSubmit={handleSubmit(onSubmit)}>
                <Row>
                  <Col>
                    <FormGroup>
                      <FormLabel>First Name</FormLabel>
                      <FormControl
                        type="text"
                        placeholder="Enter your First Name"
                        name="firstName"
                        ref={register}
                        defaultValue={profileData.firstName}
                        className={errors.firstName ? " is-invalid" : ""}
                      />
                      <div className="invalid-feedback">
                        {errors.firstName?.message}
                      </div>
                    </FormGroup>
                  </Col>
                  <Col>
                    <FormGroup>
                      <FormLabel>Last Name</FormLabel>
                      <FormControl
                        type="text"
                        placeholder="Enter your Last Name"
                        name="lastName"
                        ref={register}
                        defaultValue={profileData.lastName}
                        className={errors.lastName ? " is-invalid" : ""}
                      />
                      <div className="invalid-feedback">
                        {errors.lastName?.message}
                      </div>
                    </FormGroup>
                  </Col>
                </Row>

                <Row>
                  <Col>
                    <FormLabel>Gender</FormLabel>
                    <FormGroup>
                      <FormCheck
                        type="radio"
                        label="Male"
                        value="Male"
                        inline
                        name="gender"
                        defaultChecked={profileData.gender === "Male"}
                        ref={register}
                        className={errors.gender ? " is-invalid" : ""}
                      ></FormCheck>
                      <FormCheck
                        type="radio"
                        label="Female"
                        value="Female"
                        inline
                        name="gender"
                        defaultChecked={profileData.gender === "FeMale"}
                        ref={register}
                        className={errors.gender ? " is-invalid" : ""}
                      ></FormCheck>
                      <div className="invalid-feedback">
                        {errors.gender?.message}
                      </div>
                    </FormGroup>
                  </Col>
                  <Col>
                    <FormLabel>Date Of Birth</FormLabel>
                    <FormGroup>
                      <DatePicker
                        required
                        selected={startDate}
                        onChange={(date) => setStartDate(date)}
                      />
                    </FormGroup>
                  </Col>
                </Row>
                <FormGroup>
                  <FormLabel>Aadhar Number</FormLabel>
                  <FormControl
                    type="number"
                    placeholder="Enter your Aadhar Number"
                    name="aadharNumber"
                    ref={register}
                    defaultValue={profileData.aadharNumber}
                    className={errors.aadharNumber ? " is-invalid" : ""}
                  />
                  <div className="invalid-feedback">
                    {errors.aadharNumber?.message}
                  </div>
                </FormGroup>
                <FormGroup>
                  <FormLabel>Pan Number</FormLabel>
                  <FormControl
                    type="text"
                    placeholder="Enter your Pan Number"
                    name="panNumber"
                    ref={register}
                    defaultValue={profileData.panNumber}
                    className={errors.panNumber ? " is-invalid" : ""}
                  />
                  <div className="invalid-feedback">
                    {errors.panNumber?.message}
                  </div>
                </FormGroup>
                <Button type="submit" block variant="danger" className="mb-2">
                  Submit
                </Button>
              </Form>
            </Card.Body>
          </Card>
        </Row>
      </Container>
    </div>
  );
}
