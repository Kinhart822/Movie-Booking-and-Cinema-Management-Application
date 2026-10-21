package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.usth.mcma.backend.entity.Performer;

public interface PerformerRepository extends JpaRepository<Performer, Long> {}
