package vn.edu.usth.mcma.backend.dto.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import vn.edu.usth.mcma.backend.dto.validator.PhoneNumber;

import java.time.Instant;

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
