package vn.edu.usth.mcma.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
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
