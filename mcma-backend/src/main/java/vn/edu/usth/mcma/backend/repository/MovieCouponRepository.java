package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.entity.MovieCoupon;
import vn.edu.usth.mcma.backend.entity.MovieCouponPK;

@Repository
public interface MovieCouponRepository extends JpaRepository<MovieCoupon, MovieCouponPK> {
}
