package com.ssafy.gitchecker.repository;

import java.util.Optional;

import com.ssafy.gitchecker.model.ProjectType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTypeRepository extends JpaRepository<ProjectType, Long> {

    Optional<ProjectType> findById(Long id);

    Optional<ProjectType> findByName(String name);
}
