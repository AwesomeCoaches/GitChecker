package com.ssafy.gitchecker.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.gitchecker.model.Contribution;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution, Long> {

    Optional<Contribution> findById(Long id);

    List<Contribution> findByStudentId(Long studentId);
}
