package vn.edu.usth.mcma.backend.module.auth.entity;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Otp {
    private String email;
    private String otp;
    private Instant dueDate;
}
