package com.sda.j92.academy.modelDto;

import com.sda.j92.academy.model.AcademicGroup;
import com.sda.j92.academy.model.Grade;
import lombok.*;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class StudentDto {
    private Long id;

    private String name;
    private String surname;
    private String pesel;
    private Long phoneNumber;
    private String email;

    private Set<AcademicGroupDto> academicGroups;

}
