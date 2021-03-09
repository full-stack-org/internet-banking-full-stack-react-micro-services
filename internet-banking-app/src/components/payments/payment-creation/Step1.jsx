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
import { useNavigate } from "react-router-dom";
import Stepper from "react-stepper-horizontal";
import DatePicker from "react-datepicker";
import PaymentsNav from "../../nav-bar/PaymentsNav";
import moment from "moment";
import * as asyncDataAPI from "../../redux/invoker/AsyncCallsInvoker";

export default function Step1() {
  //Pulling up the data
  let profileData = JSON.parse(localStorage.getItem("profileData"));
  let jwtToken = localStorage.getItem("jwtToken");

  //setting up navigate functionality
  let navigate = useNavigate();

  let [internalAccounts, setInternalAccounts] = useState(null);
  let [externalAccounts, setExternalAccounts] = useState(null);

  //Settingup the current date on page load
  const [startDate, setStartDate] = useState(new Date());

  //Setting up the initial Data
  let initialData = {
    fromAccount: "",
    toAccount: "",
    paymentDate: "",
    paymentAmount: "",
  };

  //Using State to store the customer selected data
  const [step1Data, setStep1Data] = useState(initialData);

  //Logic to set the current active Nav
  let currentNav = "/transfer";

  useEffect(() => {
    localStorage.setItem("activeNav", currentNav);

    if (profileData !== null) {
      //Invoking Internal and External Accounts Call
      let serviceInput = {
        data: {
          customerId: profileData.customerId,
        },
        token: jwtToken,
        callbackSuccess: renderSuccessPage,
        callbackFailure: renderTheErrorPage,
      };

      //Invoking Async Calls
      asyncDataAPI.getCustomerAccounts(serviceInput);

      localStorage.removeItem("step3Details");
      localStorage.removeItem("step1Details");

      if (localStorage.getItem("step1Details") !== null) {
        let cacheData = JSON.parse(localStorage.getItem("step1Details"));
        setStep1Data(cacheData);
        setStartDate(moment(cacheData.paymentDate).toDate());
      } else {
        setStep1Data(initialData);
      }
    } else {
      navigate("/");
    }
  }, [localStorage.setItem("activeNav", currentNav)]);

  //Rendering success page
  function renderSuccessPage(results) {
    if (results !== null) {
      let [internalAccounts, externalAccounts] = results;
      setInternalAccounts(internalAccounts.data);
      setExternalAccounts(externalAccounts.data);
    }
  }

  //Rendering the error page
  function renderTheErrorPage(error) {
    if (error !== null) {
      navigate("/error");
    }
  }

  //Setting up the error messages.
  const validationSchema = Yup.object({
    fromAccount: Yup.string().required("From Account is Required"),
    toAccount: Yup.string().required("From Account is Required"),
    paymentAmount: Yup.string().required("Payment Amount is Required"),
  });

  //Validating the From with Yup
  const { register, handleSubmit, errors } = useForm({
    resolver: yupResolver(validationSchema),
  });

  //Submitting the service data
  const onSubmit = (values) => {
    const storageData = {
      fromAccount: values.fromAccount,
      toAccount: values.toAccount,
      paymentDate: startDate,
      paymentAmount: values.paymentAmount,
    };
    localStorage.setItem("step1Details", JSON.stringify(storageData));
    navigate("/transferstpe2");
  };

  //Method to set the selected Data
  function handleChange(evt) {
    const value = evt.target.value;
    setStep1Data({
      ...step1Data,
      [evt.target.name]: value,
    });
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
              <Form onSubmit={handleSubmit(onSubmit)}>
                <FormGroup>
                  <FormLabel>From Account</FormLabel>
                  <FormControl
                    as="select"
                    name="fromAccount"
                    ref={register}
                    value={step1Data.fromAccount}
                    onChange={handleChange}
                    className={errors.fromAccount ? " is-invalid" : ""}
                  >
                    <option value="">Select From Account</option>
                    {externalAccounts !== null &&
                    externalAccounts.statusResponse.statusCode == 200
                      ? externalAccounts.accountNumbersList.map((account) => (
                          <option value={account} key={account}>
                            {account}
                          </option>
                        ))
                      : ""}
                  </FormControl>
                  <div className="invalid-feedback">
                    {errors.fromAccount?.message}
                  </div>
                </FormGroup>
                <FormGroup>
                  <FormLabel>To Account</FormLabel>
                  <FormControl
                    as="select"
                    name="toAccount"
                    ref={register}
                    value={step1Data.toAccount}
                    onChange={handleChange}
                    className={errors.toAccount ? " is-invalid" : ""}
                  >
                    <option value="">Select To Account</option>
                    {internalAccounts !== null &&
                    internalAccounts.statusResponse.statusCode == 200
                      ? internalAccounts.accountsList.map((account) => (
                          <option value={account} key={account}>
                            {account}
                          </option>
                        ))
                      : ""}
                  </FormControl>
                  <div className="invalid-feedback">
                    {errors.toAccount?.message}
                  </div>
                </FormGroup>
                <FormLabel>Transfer Date</FormLabel>
                <FormGroup>
                  <DatePicker
                    required
                    selected={startDate}
                    onChange={(date) => setStartDate(date)}
                    minDate={startDate}
                  />
                </FormGroup>
                <FormGroup>
                  <FormLabel>Payment Amount</FormLabel>
                  <FormControl
                    type="number"
                    placeholder="Enter Payment Amount"
                    name="paymentAmount"
                    ref={register}
                    defaultValue={step1Data.paymentAmount}
                    className={errors.paymentAmount ? " is-invalid" : ""}
                  />
                  <div className="invalid-feedback">
                    {errors.paymentAmount?.message}
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
