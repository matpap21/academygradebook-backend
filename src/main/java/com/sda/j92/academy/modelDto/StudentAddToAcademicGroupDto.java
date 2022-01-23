package com.sda.j92.academy.modelDto;

import com.sda.j92.academy.model.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentAddToAcademicGroupDto {
    Set<StudentDto> studentDtoSet;
    Set<AcademicGroupDto> academicGroupDtoSet;
}
