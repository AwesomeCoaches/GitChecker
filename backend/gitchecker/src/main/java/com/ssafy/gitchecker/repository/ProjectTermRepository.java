package com.ssafy.gitchecker.repository;

import java.util.Optional;

import com.ssafy.gitchecker.model.ProjectTerm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTermRepository extends JpaRepository<ProjectTerm, Long> {

    Optional<ProjectTerm> findById(Long id);

    Optional<ProjectTerm> findByTypeAndGrp(Integer type, String grp);
}
