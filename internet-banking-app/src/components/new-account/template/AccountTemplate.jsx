import React from "react";
import {
  Container,
  Row,
  Card,
  CardGroup,
  FormGroup,
  FormCheck,
  Form,
  Button,
} from "react-bootstrap";
import { useForm } from "react-hook-form";
import * as Yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";
import { useNavigate } from "react-router-dom";

export default function AccountTemplate() {
  //Setting up the error messages.
  const validationSchema = Yup.object({
    user: Yup.string().required("User Type is Mandatory"),
  });

  //Validating the From with Yup
  const { register, handleSubmit, errors } = useForm({
    resolver: yupResolver(validationSchema),
  });

  //setting up navigate functionality
  let navigate = useNavigate();

  //Submitting the service data
  const onSubmit = (values) => {
    if(values.user === 'existingUser'){
      localStorage.setItem('userType',values.user)
      navigate('/')
    } else{
      navigate('/accountstep1')
    }
  };

  let userError = errors.user;
  
  return (
    <div>
      <Container>
        <Row className="justify-content-center mt-5">
          <Form onSubmit={handleSubmit(onSubmit)}>
            <CardGroup
              className={
                typeof userError !== "undefined" ? "card-error-style" : ""
              }
            >
              <Card style={{ width: "15rem" }}>
                <Card.Header className="text-center font-weight-bold">
                  Already Registered{" "}
                </Card.Header>
                <Card.Body>
                  <FormGroup>
                    <FormCheck
                      type="radio"
                      name="user"
                      className={
                        "radio-style" + (errors.user ? " is-invalid" : "")
                      }
                      value="existingUser"
                      ref={register}
                    />
                  </FormGroup>
                </Card.Body>
              </Card>
              <Card style={{ width: "15rem" }}>
                <Card.Header className="text-center font-weight-bold">
                  New User
                </Card.Header>
                <Card.Body>
                  <FormGroup>
                    <FormCheck
                      type="radio"
                      name="user"
                      className={
                        "radio-style" + (errors.user ? " is-invalid" : "")
                      }
                      value="newUser"
                      ref={register}
                    />
                  </FormGroup>
                </Card.Body>
              </Card>
            </CardGroup>
            {typeof userError !== "undefined" ? (
              <p className="text-center text-danger mt-2">
                {" "}
                Please select the User Type
              </p>
            ) : (
              ""
            )}
            <Row className="justify-content-center mt-4">
              <Button
                type="submit"
                variant="danger"
                className="account-button-style mb-5"
              >
                Submit
              </Button>
            </Row>
          </Form>
        </Row>
      </Container>
    </div>
  );
}
