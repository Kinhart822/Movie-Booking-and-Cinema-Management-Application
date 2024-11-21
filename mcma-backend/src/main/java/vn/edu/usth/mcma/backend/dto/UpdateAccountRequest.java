package vn.edu.usth.mcma.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.dto.validator.GenderSubset;
import com.spring.dto.validator.PhoneNumber;
import com.spring.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class UpdateAccountRequest {
    @NotBlank
    @Email
    private String email;

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

    @NotNull(message = "address must be not null")
    private String address;

    private Date dateUpdated;
}
