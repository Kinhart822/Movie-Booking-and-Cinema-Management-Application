package vn.edu.usth.mcma.backend.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "user_coupon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCoupon implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private UserCouponPK id;
}
