package com.sda.j92.academy.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double grades;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    private ApplicationUser universityLecturer;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    private AcademicSubject academicSubject;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    private Student student;

    //ManytoMany lub OnetoOne -- > mapped by jest po stronie rzeczy ktora sie dodaje

}
