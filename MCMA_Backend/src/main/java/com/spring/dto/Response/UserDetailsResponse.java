package com.spring.dto.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.dto.validator.EnumValue;
import com.spring.dto.validator.GenderSubset;
import com.spring.dto.validator.PhoneNumber;
import com.spring.enums.Gender;
import com.spring.enums.Status;
import com.spring.enums.Type;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
public class UserDetailsResponse implements Serializable {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private Date dateOfBirth;

    private Gender gender;

    private Type type;

    private Status status;

    private String address;

    private Date dateCreated;

    private Date dateUpdated;
}
