package com.ssafy.gitchecker.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StudentResponse {

    private Long id;
    private String name;

    @JsonProperty("username")
    private String username;

    @JsonProperty("group")
    private int grp;
    private String city;

    @JsonProperty("class")
    private String cls;

    @JsonProperty("team_id")
    private String teamId;

    @JsonProperty("avatar_url")
    private String avatarUrl;
}
