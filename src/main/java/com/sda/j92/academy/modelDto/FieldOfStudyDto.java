package com.sda.j92.academy.modelDto;

import com.sda.j92.academy.model.AcademicGroup;
import com.sda.j92.academy.model.FieldOfStudyEnum;
import lombok.*;

import javax.persistence.OneToMany;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldOfStudyDto {
    private Long id;
    private FieldOfStudyEnum fieldOfStudyEnum;
}
