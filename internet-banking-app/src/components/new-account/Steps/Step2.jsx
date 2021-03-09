import React,{ useState, useEffect } from "react";
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
import Stepper from "react-stepper-horizontal";
import { useForm } from "react-hook-form";
import * as Yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";
import { useNavigate } from "react-router-dom";

export default function Step2() {
  //Setting up the error messages.
  const validationSchema = Yup.object({
    aadhar: Yup.string().required("Aadhar Number is Mandatory"),
    pan: Yup.string().required("Pan Number is Mandatory"),
  });

  //Validating the From with Yup
  const { register, handleSubmit, errors } = useForm({
    resolver: yupResolver(validationSchema),
  });

  //setting up navigate functionality
  let navigate = useNavigate();

   //Setting up the initial Data
   let initialData = {
    aadhar: "",
    pan: "",
  };

   //Using State to store the customer selected data
   const [step2Data, setStep2Data] = useState(initialData)

  useEffect(() => {
    if (localStorage.getItem("step2Details") !== null) {
      let cacheData = JSON.parse(localStorage.getItem("step2Details"));
      setStep2Data(cacheData);
    } else {
      setStep2Data(initialData);
    }
  }, []);


  //Submitting the service data
  const onSubmit = (values) => {
    const storageData = {
      aadhar: values.aadhar,
      pan: values.pan,
    };
    localStorage.setItem("step2Details", JSON.stringify(storageData));

    navigate("/accountstep3");
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
        activeStep={1}
      />

      <Container>
        <Row className="justify-content-center mt-4">
          <Card style={{ width: "35rem" }}>
            <Card.Body>
              <Form onSubmit={handleSubmit(onSubmit)}>
                <FormGroup>
                  <FormLabel>Aadhar Number</FormLabel>
                  <FormControl
                    type="number"
                    placeholder="Enter your Aadhar Number"
                    name="aadhar"
                    ref={register}
                    defaultValue={step2Data.aadhar}
                    className={errors.aadhar ? " is-invalid" : ""}
                  />
                  <div className="invalid-feedback">
                    {errors.aadhar?.message}
                  </div>
                </FormGroup>
                <FormGroup>
                  <FormLabel>Pan Card Number</FormLabel>
                  <FormControl
                    type="text"
                    placeholder="Enter your Pan Card Number"
                    name="pan"
                    ref={register}
                    defaultValue={step2Data.pan}
                    className={errors.pan ? " is-invalid" : ""}
                  />
                  <div className="invalid-feedback">{errors.pan?.message}</div>
                </FormGroup>

                <Button
                  type="button"
                  variant="danger"
                  className="mb-2 mr-5"
                  onClick={() => navigate("/accountstep1")}
                >
                  Previous
                </Button>
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
