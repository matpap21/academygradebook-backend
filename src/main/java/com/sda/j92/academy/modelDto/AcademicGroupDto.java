package com.sda.j92.academy.modelDto;

import com.sda.j92.academy.model.FieldOfStudy;
import com.sda.j92.academy.model.FieldOfStudyEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.concurrent.ScheduledExecutorTask;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcademicGroupDto {
    private Long id;
    private String academicGroup;
    private LocalDate startDate;
    private int groupLength;

    private FieldOfStudyEnum fieldOfStudy;

}
