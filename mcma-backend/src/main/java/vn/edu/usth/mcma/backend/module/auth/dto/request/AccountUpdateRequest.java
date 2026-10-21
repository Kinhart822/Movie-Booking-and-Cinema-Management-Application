package vn.edu.usth.mcma.backend.module.auth.dto.request;

import java.time.Instant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import vn.edu.usth.mcma.backend.common.validation.PhoneNumber;

import lombok.Data;

@Data
public class AccountUpdateRequest {
    @NotBlank(message = "firstName must be not blank")
    private String firstName;

    @NotNull(message = "lastName must be not null")
    private String lastName;

    private Integer sex;

    @NotNull(message = "dateOfBirth must be not null")
    private Instant dateOfBirth;

    @PhoneNumber(message = "phone invalid format")
    private String phone;

    @NotNull(message = "address must be not null")
    private String address;
}
