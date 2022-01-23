package com.sda.j92.academy.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcademicGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String academicGroup;
    private LocalDate startDate;
    private int groupLength;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JsonBackReference
    private FieldOfStudy fieldOfStudy;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = "academicGroups")
    @JsonManagedReference
    private Set<Student> students;
    //ManytoMany lub OnetoOne -- > mapped by jest po stronie rzeczy ktora sie dodaje
}
