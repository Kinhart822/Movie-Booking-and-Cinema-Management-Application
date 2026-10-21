package vn.edu.usth.mcma.backend.dto.response.account;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPresentation {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private Integer sex;
    private Instant dateOfBirth;
    private String phone;
    private String address;
    private Instant createdDate;
    private Instant lastModifiedDate;
}
