package vn.edu.usth.mcma.backend.dao;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends AbstractAuditing implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column(columnDefinition = "TINYINT")
    private Integer sex;
    @Column(name = "dob")
    private Instant dateOfBirth;
    @Column
    private String email;
    @Column
    private String phone;
    @Column
    private String password;
    @Column
    private String address;
    @Column(columnDefinition = "SMALLINT")
    private Integer userType;
    @Column
    private String resetKey;
    @Column
    private Instant resetDate;
    @Column(columnDefinition = "TINYINT")
    private Integer status;
}
