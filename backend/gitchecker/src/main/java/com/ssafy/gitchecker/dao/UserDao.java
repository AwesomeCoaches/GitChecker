package com.ssafy.gitchecker.dao;

import com.ssafy.gitchecker.dto.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, String>{
    
    
}
