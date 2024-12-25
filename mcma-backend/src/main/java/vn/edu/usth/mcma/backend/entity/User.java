package vn.edu.usth.mcma.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
public class User implements Serializable {
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
    @JsonIgnore
    private String password;
    @Column
    private String address;
    @Column(columnDefinition = "SMALLINT")
    private Integer userType;
    @Column
    @JsonIgnore
    private String resetKey;
    @Column
    @JsonIgnore
    private Instant resetDate;
    @Column(columnDefinition = "TINYINT")
    @JsonIgnore
    private Integer status;
    @Column(updatable = false)
    private Long createdBy;
    private Long lastModifiedBy;
    @CreatedDate
    @Column(updatable = false)
    private Instant createdDate;
    @LastModifiedDate
    @Column
    private Instant lastModifiedDate;
}
