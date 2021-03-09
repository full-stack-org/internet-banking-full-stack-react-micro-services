import React,{ useState, useEffect } from "react";
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
import Stepper from "react-stepper-horizontal";
import { useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import * as Yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";

export default function Step3() {
  //Setting up the error messages.
  const validationSchema = Yup.object({
    accountType: Yup.string().required("Account Type is Mandatory"),
  });

  //Validating the From with Yup
  const { register, handleSubmit, errors } = useForm({
    resolver: yupResolver(validationSchema),
  });

  //setting up navigate functionality
  let navigate = useNavigate();

   //Setting up the initial Data
   let initialData = {
    accountType: "",
  };

   //Using State to store the customer selected data
   const [step3Data, setStep3Data] = useState(initialData)

  useEffect(() => {
    if (localStorage.getItem("step3Details") !== null) {
      let cacheData = JSON.parse(localStorage.getItem("step3Details"));
      setStep3Data(cacheData);
    } else {
      setStep3Data(initialData);
    }
  }, []);

  //Submitting the service data
  const onSubmit = (values) => {
    const storageData = {
      accountType: values.accountType,
    };
    localStorage.setItem("step3Details", JSON.stringify(storageData));
    navigate("/accountstep4");
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
        activeStep={2}
      />
      <Container>
        <Row className="justify-content-center mt-4">
          <Card style={{ width: "35rem" }}>
            <Card.Body>
              <Form onSubmit={handleSubmit(onSubmit)}>
                <FormLabel>Account Type</FormLabel>
                <FormGroup>
                  <FormCheck
                    type="radio"
                    label="Savings"
                    value="Savings"
                    inline
                    name="accountType"
                    defaultChecked={step3Data.accountType === "Savings"}
                    ref={register}
                  ></FormCheck>
                  <FormCheck
                    type="radio"
                    label="Current"
                    value="Current"
                    inline
                    name="accountType"
                    ref={register}
                    defaultChecked={step3Data.accountType === "Current"}
                    className={errors.accountType ? " is-invalid" : ""}
                  ></FormCheck>
                  <div className="invalid-feedback">
                    {errors.accountType?.message}
                  </div>
                </FormGroup>

                <Button
                  type="button"
                  variant="danger"
                  className="mb-2 mr-5"
                  onClick={() => navigate("/accountstep2")}
                >
                  Previous
                </Button>
                <Button type="submit" variant="danger" className="mb-2 ml-5">
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
