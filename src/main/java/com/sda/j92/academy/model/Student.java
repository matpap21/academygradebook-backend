package com.sda.j92.academy.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String pesel;
    private Long phoneNumber;
    private String email;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "student") // czyli jedna pojedyncza osoba z klasy student, pole student
    private Set<Grade> grades;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference
    @ManyToMany
    private Set<AcademicGroup> academicGroups = new HashSet<> (  );

    //ManytoMany lub OnetoOne -- > mapped by jest po stronie rzeczy ktora sie dodaje

}
