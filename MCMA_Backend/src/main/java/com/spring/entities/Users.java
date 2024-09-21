package com.spring.entities;

import com.spring.enums.Status;
import com.spring.enums.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Email")
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

    @Column(name = "Date_Created")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Column(name = "Date_Updated")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdated;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private List<UserAuthority> userAuthorityList = new ArrayList<>();

    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL)
    private UserCoupon userCoupon;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private List<MovieRespond> movieResponds = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drink_id", nullable = false)
    private Drink drink;
}
