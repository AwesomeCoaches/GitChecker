package com.ssafy.gitchecker.dao;

import com.ssafy.gitchecker.dto.Student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDao extends JpaRepository<Student, String>{
    
}
