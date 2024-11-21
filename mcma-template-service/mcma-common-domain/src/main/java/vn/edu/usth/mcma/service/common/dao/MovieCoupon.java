package vn.edu.usth.mcma.service.common.dao;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "movie_coupon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieCoupon implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private MovieCouponPK id;
}
