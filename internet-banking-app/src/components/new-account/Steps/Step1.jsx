import React, { useState, useEffect } from "react";
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
import DatePicker from "react-datepicker";
import Stepper from "react-stepper-horizontal";
import moment from "moment";

export default function Step1() {
  //Setting up the current date to show in calendar
  const [startDate, setStartDate] = useState(new Date());

  //Setting up the error messages.
  const validationSchema = Yup.object({
    firstName: Yup.string().required("First Name is Mandatory"),
    lastName: Yup.string().required("Last Name is Mandatory"),
    gender: Yup.string().required("Gender is Mandatory"),
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

  //Setting up the initial Data
  let initialData = {
    firstName: "",
    lastName: "",
    gender: "",
    password: "",
    confirmPassword: "",
  };

  //Using State to store the customer selected data
  const [step1Data, setStep1Data] = useState(initialData);

  useEffect(() => {
    if (localStorage.getItem("step1Details") !== null) {
      let cacheData = JSON.parse(localStorage.getItem("step1Details"));
      setStep1Data(cacheData);
      setStartDate(moment(cacheData.paymentDate).toDate());
    } else {
      setStep1Data(initialData);
    }
  }, []);

  //Submitting the service data
  const onSubmit = (values) => {
    const storageData = {
      firstName: values.firstName,
      lastName: values.lastName,
      gender: values.gender,
      dateOfBirth: startDate,
      password: values.password,
      confirmPassword: values.confirmPassword
    };
    localStorage.setItem("step1Details", JSON.stringify(storageData));

    navigate("/accountstep2");
  };

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
        activeStep={0}
      />
      <Container>
        <Row className="justify-content-center mt-4">
          <Card style={{ width: "35rem" }}>
            <Card.Body>
              <Form onSubmit={handleSubmit(onSubmit)}>
                <FormGroup>
                  <FormLabel>First Name</FormLabel>
                  <FormControl
                    type="text"
                    placeholder="Enter your First Name"
                    name="firstName"
                    ref={register}
                    defaultValue={step1Data.firstName}
                    className={errors.firstName ? " is-invalid" : ""}
                  />
                  <div className="invalid-feedback">
                    {errors.firstName?.message}
                  </div>
                </FormGroup>
                <FormGroup>
                  <FormLabel>Last Name</FormLabel>
                  <FormControl
                    type="text"
                    placeholder="Enter your Last Name"
                    name="lastName"
                    ref={register}
                    defaultValue={step1Data.lastName}
                    className={errors.lastName ? " is-invalid" : ""}
                  />
                  <div className="invalid-feedback">
                    {errors.lastName?.message}
                  </div>
                </FormGroup>
                <FormLabel>Gender</FormLabel>
                <FormGroup>
                  <FormCheck
                    type="radio"
                    label="Male"
                    value="Male"
                    inline
                    name="gender"
                    defaultChecked={step1Data.gender === "Male"}
                    ref={register}
                    className={errors.gender ? " is-invalid" : ""}
                  ></FormCheck>
                  <FormCheck
                    type="radio"
                    label="FeMale"
                    value="FeMale"
                    inline
                    name="gender"                    
                    defaultChecked={step1Data.gender === "FeMale"}
                    ref={register}
                    className={errors.gender ? " is-invalid" : ""}
                  ></FormCheck>
                  <div className="invalid-feedback">
                    {errors.gender?.message}
                  </div>
                </FormGroup>
                <FormGroup>
                  <FormLabel>Date Of Birth</FormLabel>
                  <FormGroup>
                    <DatePicker
                      required
                      selected={startDate}
                      onChange={(date) => setStartDate(date)}
                    />
                  </FormGroup>
                </FormGroup>
                <FormGroup>
                  <FormLabel>Password</FormLabel>
                  <FormControl
                    type="password"
                    placeholder="Enter your Password"
                    name="password"
                    defaultValue={step1Data.password}
                    ref={register}
                    className={errors.password ? " is-invalid" : ""}
                  />
                  <div className="invalid-feedback">
                    {errors.password?.message}
                  </div>
                </FormGroup>
                <FormGroup>
                  <FormLabel>Password</FormLabel>
                  <FormControl
                    type="password"
                    placeholder="Enter your Confirm Password"
                    name="confirmPassword"
                    defaultValue={step1Data.confirmPassword}
                    ref={register}
                    className={errors.confirmPassword ? " is-invalid" : ""}
                  />
                  <div className="invalid-feedback">
                    {errors.confirmPassword?.message}
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
