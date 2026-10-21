package vn.edu.usth.mcma.backend.module.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.usth.mcma.backend.module.booking.entity.Audience;

public interface AudienceRepository extends JpaRepository<Audience, String> {}
