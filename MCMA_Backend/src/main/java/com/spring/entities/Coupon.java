package com.spring.entities;

import com.spring.enums.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Name", length = 20)
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "Discount", precision = 10, scale = 2)
    private BigDecimal discount;

    @Column(name = "MIN_SPEND_REQ")
    private Integer minSpendReq;

    @Column(name = "DISCOUNT_LIMIT")
    private Integer discountLimit;

    @Column(name = "DATE_AVAILABLE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateAvailable;

    @Column(name = "DATE_EXPIRED")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateExpired;

    @Column(name = "Created_By")
    @Enumerated(EnumType.ORDINAL)
    private Type createdBy;

    @Column(name = "Last_Modified_By")
    @Enumerated(EnumType.ORDINAL)
    private Type lastModifiedBy;

    @Column(name = "Date_Created", updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateCreated;

    @Column(name = "Date_Updated")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateUpdated;

    @ManyToMany(mappedBy = "userCoupons")
    private Set<User> userSet;

    @ManyToMany(mappedBy = "movieCouponSet")
    private Set<Movie> movieSet;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "coupon")
    private List<BookingCoupon> coupons;
}
