package vn.edu.usth.mcma.backend.module.auth.entity;

import java.util.Date;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RefreshToken {
    @Id private String refreshToken;
    @Column private Integer status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column private Date createdDate;
    @Column private Date expirationDate;
    // future work: deviceId one or multiple active device
}
