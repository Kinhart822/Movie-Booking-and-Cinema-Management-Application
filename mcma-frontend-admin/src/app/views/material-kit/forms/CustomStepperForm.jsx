import { Box } from "@mui/material";
import Button from "@mui/material/Button";
import Step from "@mui/material/Step";
import StepLabel from "@mui/material/StepLabel";
import Stepper from "@mui/material/Stepper";
import Typography from "@mui/material/Typography";
import React, {useState} from "react";
import TextField from "@mui/material/TextField";
import {Paragraph} from "../../../components/Typography.jsx";
import {NavLink} from "react-router-dom";
import {useTheme} from "@mui/material/styles";

function getSteps() {
  return ["Enter your email", "Enter OTP", "Enter new password"];
}

function getStepContent(stepIndex) {
  switch (stepIndex) {
      case 0:
          return (
              <>
                  <form onSubmit={handleFormSubmit}>
                      <TextField
                          type="email"
                          name="email"
                          size="small"
                          label="Email"
                          value={email}
                          variant="outlined"
                          onChange={(e) => setEmail(e.target.value)}
                          sx={{mb: 3, width: "100%"}}
                      />

                      <Button fullWidth variant="contained" color="primary" type="submit">
                          Reset Password
                      </Button>
                  </form>
                  <Paragraph sx={{mt:3}}>
                      Suddenly remember your password?
                      <NavLink
                          to="/session/signin"
                          style={{color: theme.palette.primary.main, marginLeft: 5}}>
                          Login
                      </NavLink>
                  </Paragraph>
              </>);
      case 1:
          return ("<p>content 2</p>");
      case 2:
          return ("<p>content 2</p>");
      default:
          return ("<p>unknown step</p>");
  }
}

export default function CustomStepperForm() {
    const [email, setEmail] = useState("");

    const handleFormSubmit = () => {
        console.log(email);
    };
    const [activeStep, setActiveStep] = React.useState(0);
    const steps = getSteps();

    const handleNext = () => setActiveStep((prevActiveStep) => prevActiveStep + 1);

    const handleBack = () => setActiveStep((prevActiveStep) => prevActiveStep - 1);

    const handleReset = () => setActiveStep(0);

    return (
        <Box>
            <Stepper activeStep={activeStep} alternativeLabel>
                {steps.map((label) => (
                    <Step key={label}>
                        <StepLabel>{label}</StepLabel>
                    </Step>
                ))}
            </Stepper>

            <Box mt={4}>
                {activeStep === steps.length ? (
                    <Box>
                        <Typography>All steps completed</Typography>

                        <Button sx={{ mt: 2 }} variant="contained" color="secondary" onClick={handleReset}>
                            Reset
                        </Button>
                    </Box>
                ) : (
                    <Box>
                        <Typography>{getStepContent(activeStep)}</Typography>

                        <Box pt={2}>
                            <Button
                                variant="contained"
                                color="secondary"
                                disabled={activeStep === 0}
                                onClick={handleBack}
                            >
                                Back
                            </Button>

                            <Button sx={{ ml: 2 }} variant="contained" color="primary" onClick={handleNext}>
                                {activeStep === steps.length - 1 ? "Finish" : "Next"}
                            </Button>
                        </Box>
                    </Box>
                )}
            </Box>
        </Box>
    );
}
