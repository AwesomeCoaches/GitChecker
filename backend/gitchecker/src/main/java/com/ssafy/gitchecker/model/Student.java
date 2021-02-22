package com.ssafy.gitchecker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.ssafy.gitchecker.payload.StudentResponse;

import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Student {

    @Id
    private Long id;

    @Column(nullable = false, updatable = false)
    private String name;

    // git id
    @Column(nullable = false, updatable = false, unique = true)
    private String username;
    private int grp;
    private String city;
    private String cls;
    private String teamId;
    private String avatarUrl;

    public StudentResponse toResponse() {
        return StudentResponse.builder()
                .id(id)
                .name(name)
                .username(username)
                .grp(grp)
                .city(city)
                .cls(cls)
                .teamId(teamId)
                .avatarUrl(avatarUrl)
                .build();
    }
}
