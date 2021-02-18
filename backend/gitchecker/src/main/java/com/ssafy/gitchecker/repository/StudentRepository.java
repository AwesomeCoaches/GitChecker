package com.ssafy.gitchecker.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.gitchecker.model.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findById(Long id);

    Optional<Student> findByGitId(String gitId);

    Optional<Student> findByEmail(String email);

    List<Student> findByGrp(Integer grp);

    List<Student> findByCity(String city);

    List<Student> findByCls(String cls);

    List<Student> findByTeamId(String teamId);

    List<Student> findAll();
}
