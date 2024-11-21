package vn.edu.usth.mcma.service.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.service.common.dao.Notification;

@Repository
public interface CommonNotificationRepository extends JpaRepository<Notification, Long> {
}
