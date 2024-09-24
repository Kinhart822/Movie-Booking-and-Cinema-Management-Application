package com.spring.dto.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.dto.validator.EnumValue;
import com.spring.dto.validator.GenderSubset;
import com.spring.dto.validator.PhoneNumber;
import com.spring.enums.Gender;
import com.spring.enums.Type;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class SignUpRequest {

    @NotBlank(message = "firstName must be not blank")
    private String firstName;

    @NotNull(message = "lastName must be not null")
    private String lastName;

    @PhoneNumber(message = "phone invalid format")
    private String phone;

    @NotNull(message = "dateOfBirth must be not null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date dateOfBirth;

    @GenderSubset(anyOf = {Gender.Male, Gender.Female, Gender.Other})
    private Gender gender;

    @NotNull(message = "type must be not null")
    @EnumValue(name = "type", enumClass = Type.class)
    private String type;

    @NotNull(message = "address must be not null")
    private String address;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotNull(message = "dateCreated must be not null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date dateCreated;
}
