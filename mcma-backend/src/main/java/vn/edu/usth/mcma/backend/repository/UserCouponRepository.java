package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.dao.UserCoupon;
import vn.edu.usth.mcma.backend.dao.UserCouponPK;

@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon, UserCouponPK> {
}
