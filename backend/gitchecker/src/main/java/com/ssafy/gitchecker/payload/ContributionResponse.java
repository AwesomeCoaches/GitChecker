package com.ssafy.gitchecker.payload;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ContributionResponse {
    
    private Long id;
    private StudentResponse student;
    private int cnt;
    private LocalDateTime date;
}
