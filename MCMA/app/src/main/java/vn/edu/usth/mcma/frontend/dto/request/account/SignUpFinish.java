package vn.edu.usth.mcma.frontend.dto.request.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpFinish {
    private String sessionId;
    private String email;
    private String password;
}
