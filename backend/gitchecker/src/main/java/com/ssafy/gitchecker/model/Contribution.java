package com.ssafy.gitchecker.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ssafy.gitchecker.payload.ContributionResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(nullable = false, updatable = false)
    private Integer cnt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime date;

    public ContributionResponse toResponse() {
        return ContributionResponse.builder()
                .id(id)
                .student(student.toResponse())
                .cnt(cnt)
                .date(date)
                .build();
    }

    public Contribution updateCnt(Integer count) {
        this.cnt = count.intValue();
        return this;
    }
}
