package vn.edu.usth.mcma.service.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.service.common.dao.UserCoupon;
import vn.edu.usth.mcma.service.common.dao.UserCouponPK;

@Repository
public interface CommonUserCouponRepository extends JpaRepository<UserCoupon, UserCouponPK> {
}
