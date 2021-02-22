package com.ssafy.gitchecker.controller;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.ssafy.gitchecker.repository.ContributionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contributions")
public class ContributionController {
    
    @Autowired ContributionRepository cr;

    @GetMapping("/")
    public Object getContribution(
            @RequestParam(required = false, name = "group_id") @Size(min = 1, max = 10) Integer grp,
            @RequestParam(required = false, name = "city") @Pattern(regexp = "[a-zA-Z]{4, 7}") String city,
            @RequestParam(required = false, name = "class") @Size(min = 1, max = 10) String cls,
            @RequestParam(required = false, name = "team_id") @Pattern(regexp = "[a-zA-Z0-9]{4}") String teamId,
            @RequestParam(required = false, name = "username") @Pattern(regexp = "[a-zA-Z0-9]{4, 20}") String username) {
        if(grp != null) return cr.findByStudent_Grp(grp).stream().map(c -> c.toResponse());
        if(city != null) return cr.findByStudent_City(city).stream().map(c -> c.toResponse());
        if(cls != null) return cr.findByStudent_Cls(cls).stream().map(c -> c.toResponse());
        if(teamId != null) return cr.findByStudent_TeamId(teamId).stream().map(c -> c.toResponse());
        if(username != null) return cr.findByStudent_Username(username).stream().map(c -> c.toResponse());
        return cr.findAll().stream().map(c -> c.toResponse());
    }

}
