package vn.edu.usth.mcma.backend.dto.unsorted;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordRequest {
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String newPassword;
}
