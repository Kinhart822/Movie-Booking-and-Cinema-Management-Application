package vn.edu.usth.mcma.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RefreshToken implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    private String refreshToken;
    @Column
    private Integer status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column
    private Date createdDate;
    @Column
    private Date expirationDate;
    //future work: deviceId one or multiple active device
}
