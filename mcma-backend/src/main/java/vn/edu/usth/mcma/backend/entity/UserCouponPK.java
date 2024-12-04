package vn.edu.usth.mcma.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Data
public class UserCouponPK implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Column
    private Long userId;
    @Column
    private Long couponId;
}
