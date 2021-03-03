package com.ssafy.gitchecker.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.gitchecker.model.Student;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findById(Long id, Sort sort);

    Optional<Student> findByUsername(String username, Sort sort);

    List<Student> findByGrp(Integer grp, Sort sort);

    List<Student> findByCity(String city, Sort sort);

    List<Student> findByCls(String cls, Sort sort);

    List<Student> findByTeamId(String teamId, Sort sort);

    List<Student> findAll(Sort sort);
}
