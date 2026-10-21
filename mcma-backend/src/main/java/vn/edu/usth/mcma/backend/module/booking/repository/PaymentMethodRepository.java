package vn.edu.usth.mcma.backend.module.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.usth.mcma.backend.module.booking.entity.PaymentMethod;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, String> {}
