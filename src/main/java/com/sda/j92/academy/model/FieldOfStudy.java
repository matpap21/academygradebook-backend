package com.sda.j92.academy.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FieldOfStudy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private FieldOfStudyEnum fieldOfStudyEnum;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "fieldOfStudy")
    private Set<AcademicGroup> academicGroups;
    //ManytoMany lub OnetoOne -- > mapped by jest po stronie rzeczy ktora sie dodaje
}
