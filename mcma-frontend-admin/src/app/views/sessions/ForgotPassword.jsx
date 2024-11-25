import { useState } from "react";
import {NavLink, useNavigate} from "react-router-dom";
import Card from "@mui/material/Card";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import { styled } from "@mui/material/styles";
import StepperForm from "../material-kit/forms/StepperForm.jsx";
import {Paragraph} from "../../components/Typography.jsx";
import CustomStepperForm from "../material-kit/forms/CustomStepperForm.jsx";

// STYLED COMPONENTS
const StyledRoot = styled("div")(() => ({
  display: "flex",
  alignItems: "center",
  justifyContent: "center",
  backgroundColor: "#1A2038",
  minHeight: "100vh !important",
  "& .card": {
    maxWidth: 800,
    margin: "1rem",
    borderRadius: 12
  },
  ".img-wrapper": {
    display: "flex",
    padding: "2rem",
    alignItems: "center",
    justifyContent: "center"
  }
}));

const ContentBox = styled("div")(({ theme }) => ({
  padding: 32,
  background: theme.palette.background.default
}));

export default function ForgotPassword() {

  return (
      <StyledRoot>
        <Card className="card">
          <div className="img-wrapper">
            <img width="300" src="/assets/images/illustrations/dreamer.svg" alt="Illustration"/>
          </div>

          <ContentBox>
            <CustomStepperForm />
          </ContentBox>
        </Card>
      </StyledRoot>
  );
}
