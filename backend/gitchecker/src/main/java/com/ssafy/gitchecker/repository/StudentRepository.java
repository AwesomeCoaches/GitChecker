package com.ssafy.gitchecker.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.gitchecker.model.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findById(Long id);

    Optional<Student> findByUsername(String username);

    List<Student> findByGrp(Integer grp);

    List<Student> findByCity(String city);

    List<Student> findByCls(String cls);

    List<Student> findByTeamId(String teamId);

    List<Student> findAll();
}
