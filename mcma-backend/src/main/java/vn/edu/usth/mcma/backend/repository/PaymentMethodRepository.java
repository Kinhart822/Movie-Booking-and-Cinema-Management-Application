package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.usth.mcma.backend.entity.PaymentMethod;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, String> {}
