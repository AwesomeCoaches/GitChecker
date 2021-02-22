package com.ssafy.gitchecker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;

import com.ssafy.gitchecker.payload.StudentResponse;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String name;

    @Column(nullable = false, updatable = false, unique = true)
    private String gitId;

    @Email
    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    @Column(nullable = false, updatable = false)
    private int grp;

    @Column(nullable = false, updatable = false)
    private String city;

    @Column(nullable = false, updatable = false)
    private String cls;

    @Column(nullable = false)
    private String teamId;

    private String imageUrl;

    public StudentResponse toResponse() {
        return StudentResponse.builder()
                .id(id)
                .name(name)
                .gitId(gitId)
                .email(email)
                .grp(grp)
                .city(city)
                .cls(cls)
                .teamId(teamId)
                .imageUrl(imageUrl)
                .build();
    }
}
