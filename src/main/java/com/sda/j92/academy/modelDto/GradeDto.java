package com.sda.j92.academy.modelDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GradeDto {
    private Long id;
    private double grades;

    private String academicSubject;

    private String lecturerName;
    private String lecturerSurname;
    private String studentsName;
    private String studentsSurname;


}
