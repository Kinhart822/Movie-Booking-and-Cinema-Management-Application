package vn.edu.usth.mcma.backend.module.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.usth.mcma.backend.module.movie.entity.Performer;

public interface PerformerRepository extends JpaRepository<Performer, Long> {}
