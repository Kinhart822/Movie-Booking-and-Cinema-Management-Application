package vn.edu.usth.mcma.backend.dto.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshRequest {
    private String refreshToken;
    private String email;
}
