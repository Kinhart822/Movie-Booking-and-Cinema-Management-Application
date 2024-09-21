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
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Code", length = 20)
    private String authorityCode;

    @Column(name = "Name")
    private String name;

    @Column(name = "Type")
    @Enumerated(EnumType.ORDINAL)
    private Type type;

    @Column(name = "Status")
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @Column(name = "Date_Created")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Column(name = "Date_Updated")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdated;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "authority")
    private List<UserAuthority> userAuthorityList = new ArrayList<>();
}
