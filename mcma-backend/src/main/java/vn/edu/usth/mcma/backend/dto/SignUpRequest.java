package vn.edu.usth.mcma.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vn.edu.usth.mcma.backend.dto.validator.PhoneNumber;

import java.time.Instant;
import java.util.Date;

@Data
public class SignUpRequest {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
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
