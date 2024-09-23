package com.spring.entities;

import com.spring.enums.Status;
import com.spring.enums.Type;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "User_Type")
    @Enumerated(EnumType.ORDINAL)
    private Type userType;

    @Column(name = "Status")
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @Column(name = "Activation_Key", length = 20)
    private String activationKey;

    @Column(name = "Reset_Key", length = 20)
    private String resetKey;

    @Column(name = "Delete_Key", length = 20)
    private String deleteKey;

    @Column(name = "Date_Last_Login")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateLastLogin;

    @Column(name = "Date_Reset")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateReset;

    @Column(name = "Date_Delete")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDelete;

    @Column(name = "Created_By")
    @Enumerated(EnumType.ORDINAL)
    private Status createdBy;

    @Column(name = "Last_Modified_By")
    @Enumerated(EnumType.ORDINAL)
    private Status lastModifiedBy;

    @Column(name = "Date_Created", updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Column(name = "Date_Updated")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdated;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserCoupon userCoupon;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<MovieRespond> movieResponds = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
