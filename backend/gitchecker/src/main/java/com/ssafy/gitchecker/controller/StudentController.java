package com.ssafy.gitchecker.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.ssafy.gitchecker.exception.ResourceNotFoundException;
import com.ssafy.gitchecker.model.Student;
import com.ssafy.gitchecker.repository.StudentRepository;
import com.ssafy.gitchecker.util.CustomAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/students")
public class StudentController {

    private static final String TEAM_ID = "teamId";

    @Autowired
    private StudentRepository sr;

    private CustomAPI gitlab;

    @GetMapping("/")
    public Object getStudent(@RequestParam(required = false, name = "group") @Size(min = 1, max = 10) Integer grp,
            @RequestParam(required = false, name = "city") @Pattern(regexp = "[a-zA-Z]{4, 7}") String city,
            @RequestParam(required = false, name = "class") @Size(min = 1, max = 10) String cls,
            @RequestParam(required = false, name = "team_id") @Pattern(regexp = "[a-zA-Z0-9]{4}") String teamId,
            @RequestParam(required = false, name = "username") @Pattern(regexp = "[a-zA-Z0-9]{4, 20}") String username) {

        if(grp != null) return sr.findByGrp(grp, Sort.by(TEAM_ID)).stream().map(s -> s.toResponse());
        else if(city != null) return sr.findByCity(city, Sort.by(TEAM_ID)).stream().map(s -> s.toResponse());
        else if (cls != null) return sr.findByCls(cls, Sort.by(TEAM_ID)).stream().map(s -> s.toResponse());
        else if (teamId != null) return sr.findByTeamId(teamId, Sort.by(TEAM_ID)).stream().map(s -> s.toResponse());
        else if (username != null) return sr.findByUsername(username, Sort.by(TEAM_ID))
                .orElseThrow(() -> new ResourceNotFoundException("student", "username", username));
        return sr.findAll(Sort.by(TEAM_ID)).stream().map(s -> s.toResponse());
    }

    @GetMapping("/projects")
    public Object getProjects(@RequestParam(required = false, name = "search") @Size(min = 1, max = 10) String search) {
        gitlab = new CustomAPI();
        
        Map<String, String> params = new HashMap<>();

        params.put("simple", "true");
        params.put("per_page", "100");
        if(search != null) params.put("search", search);

        return gitlab.getProjects(params).toString();
    }

    @GetMapping("/members")
    public Object getMembers(@RequestParam(required = true, name = "projectID") @Size(min = 1, max = 10) String projectID){
        gitlab = new CustomAPI();

        return gitlab.getMembers(projectID).toString();
    }

    @GetMapping("/updateAll")
    public Object setMembers(@RequestParam(required = false, name = "search") @Size(min = 3, max = 10) String search){

        gitlab = new CustomAPI();
        
        Map<String, String> params = new HashMap<>();

        params.put("simple", "true");
        params.put("per_page", "100");
        if(search != null) params.put("search", search);

        Map<String, String> projects = gitlab.getProjects(params);

        Map<String, Student> res = new HashMap<>();
        projects.forEach((projectID, projectName) -> {
            int grp = Integer.parseInt(projectName.substring(1, 3));
            String city = "" + projectName.toUpperCase().charAt(6);
            String cls = projectName.substring(7, 8);
            String teamId = projectName.toUpperCase().substring(6, 10);

            Map<String, Student> members = gitlab.getMembers(projectID);
            members.forEach((key, student) -> {
                student.setGrp(grp);
                student.setCity(city);
                student.setCls(cls);
                student.setTeamId(teamId);
                
                if(sr.existsById(student.getId()) == false){
                    sr.save(student);
                }

                res.put(key, student);
            });
        });

        return res.toString();
    }

}
