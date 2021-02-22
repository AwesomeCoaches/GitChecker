package com.ssafy.gitchecker.controller;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.ssafy.gitchecker.exception.ResourceNotFoundException;
import com.ssafy.gitchecker.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            @RequestParam(required = false, name = "git_id") @Pattern(regexp = "[a-zA-Z0-9]{4, 20}") String username) {

        if(grp != null) return sr.findByGrp(grp).stream().map(s -> s.toResponse());
        else if(city != null) return sr.findByCity(city).stream().map(s -> s.toResponse());
        else if (cls != null) return sr.findByCls(cls).stream().map(s -> s.toResponse());
        else if (teamId != null) return sr.findByTeamId(teamId).stream().map(s -> s.toResponse());
        else if (username != null) return sr.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("student", "git_id", username));
        return sr.findAll().stream().map(s -> s.toResponse());
    }

}
