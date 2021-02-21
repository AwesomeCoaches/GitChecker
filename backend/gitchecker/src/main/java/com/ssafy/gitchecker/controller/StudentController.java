package com.ssafy.gitchecker.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ssafy.gitchecker.exception.ResourceNotFoundException;
import com.ssafy.gitchecker.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository sr;

    @GetMapping("/")
    public Object getStudent(@RequestParam(required = false, name = "group") @Size(min = 1, max = 10) Integer grp,
            @RequestParam(required = false, name = "city") @Pattern(regexp = "[a-zA-Z]{4, 7}") String city,
            @RequestParam(required = false, name = "class") @Size(min = 1, max = 10) String cls,
            @RequestParam(required = false, name = "team_id") @Pattern(regexp = "[a-zA-Z0-9]{4}") String teamId,
            @RequestParam(required = false, name = "git_id") @Pattern(regexp = "[a-zA-Z0-9]{4, 20}") String gitId) {

        if (grp != null)
            return sr.findByGrp(grp).stream().map(s -> s.toResponse());
        else if (city != null)
            return sr.findByCity(city).stream().map(s -> s.toResponse());
        else if (cls != null)
            return sr.findByCls(cls).stream().map(s -> s.toResponse());
        else if (teamId != null)
            return sr.findByTeamId(teamId).stream().map(s -> s.toResponse());
        else if (gitId != null)
            return sr.findByGitId(gitId).orElseThrow(() -> new ResourceNotFoundException("student", "git_id", gitId));
        return sr.findAll().stream().map(s -> s.toResponse());
    }

    @GetMapping("/projects")
    public Object getInfo(@RequestParam(required = false, name = "search") @Size(min = 1, max = 10) String search) {
        

        return "projects";
    }
}
