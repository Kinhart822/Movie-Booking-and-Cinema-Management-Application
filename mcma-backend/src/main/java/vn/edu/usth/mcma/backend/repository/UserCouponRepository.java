package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.entity.UserCoupon;
import vn.edu.usth.mcma.backend.entity.UserCouponPK;

@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon, UserCouponPK> {
}
