package com.spring.entities;

import com.spring.enums.Gender;
import com.spring.enums.Type;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "First_Name", length = 50)
    private String firstName;

    @Column(name = "Last_Name", length = 50)
    private String lastName;

    @Column(name = "Address")
    private String address;

    @Column(name = "Gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "Date_of_birth")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @Column(name = "Phone", nullable = false)
    private String phoneNumber;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "User_Type")
    @Enumerated(EnumType.ORDINAL)
    private Type userType;

    @Column(name = "Date_Created", updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateCreated;

    @Column(name = "Date_Updated")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateUpdated;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Token> tokens;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Booking> bookings;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Notification> notifications;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<MovieRespond> movieResponds;

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
