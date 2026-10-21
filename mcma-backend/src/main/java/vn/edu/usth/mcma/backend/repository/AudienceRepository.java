package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.usth.mcma.backend.entity.Audience;

public interface AudienceRepository extends JpaRepository<Audience, String> {}
