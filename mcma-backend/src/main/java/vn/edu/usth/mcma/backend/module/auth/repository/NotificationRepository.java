package vn.edu.usth.mcma.backend.module.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.usth.mcma.backend.module.auth.entity.Notification;
import vn.edu.usth.mcma.backend.module.auth.entity.User;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUserOrderByCreatedDateDesc(User user);
}
