package com.ssafy.gitchecker.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.ssafy.gitchecker.model.Contribution;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution, Long> {

    Optional<Contribution> findById(Long id);

    List<Contribution> findByStudent_Grp(int grp);

    List<Contribution> findByStudent_City(String city);

    List<Contribution> findByStudent_Cls(String cls);

    List<Contribution> findByStudent_TeamId(String teamId);

    List<Contribution> findByStudent_Username(String username);

    Optional<Contribution> findByStudent_UsernameAndDate(String username, LocalDateTime date);

}
