package com.sda.j92.academy.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcademicSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String academicSubject;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "academicSubject")
    private Set<Grade> grades;

    // trainings - nazwa pola w klasie UniversityLecturer
    //  podajemy ją żeby Hibernate "wiedział" że jest to wzajemna relacja
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany
    private Set<ApplicationUser> lecturers;
    //ManytoMany lub OnetoOne -- > mapped by jest po stronie rzeczy ktora sie dodaje
}
