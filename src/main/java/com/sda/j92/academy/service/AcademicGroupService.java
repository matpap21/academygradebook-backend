package com.sda.j92.academy.service;

import com.sda.j92.academy.model.*;
import com.sda.j92.academy.modelDto.AcademicGroupDto;
import com.sda.j92.academy.repository.AcademicGroupRepository;
import com.sda.j92.academy.repository.ApplicationUserRepository;
import com.sda.j92.academy.repository.FieldOfStudyRepository;
import com.sda.j92.academy.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AcademicGroupService {
    private final ApplicationUserRepository applicationUserRepository;
    private final AcademicGroupRepository academicGroupRepository;
    private final FieldOfStudyRepository fieldOfStudyRepository;
    private final StudentRepository studentRepository;

    public List<AcademicGroupDto> findAll() {
        List<AcademicGroup> academicGroups = academicGroupRepository.findAll ( );
        log.info ("GetAll : " + academicGroups);
        return academicGroups.stream ( )
                .map (academicGroup -> AcademicGroupDto.builder ( )
                        .academicGroup (academicGroup.getAcademicGroup ( ))
                        .groupLength (academicGroup.getGroupLength ( ))
                        .fieldOfStudy (academicGroup.getFieldOfStudy ( ) == null ?
                                FieldOfStudyEnum.UNKNOWN : academicGroup.getFieldOfStudy ( ).getFieldOfStudyEnum ( ))
                        .startDate (academicGroup.getStartDate ( ))
                        .id (academicGroup.getId ( ))
                        .build ( ))
                .collect (Collectors.toList ( ));
    }


    public void add(Long fieldofstudyid, AcademicGroupDto dto) {
        log.info ("Add : " + dto + fieldofstudyid);
        Optional<FieldOfStudy> fieldOfStudyOptional = fieldOfStudyRepository.findById (fieldofstudyid);
        if (fieldOfStudyOptional.isPresent ( )) {
            FieldOfStudy fieldOfStudy = fieldOfStudyOptional.get ( );
            AcademicGroup academicGroup = AcademicGroup.builder ( )
                    .academicGroup (dto.getAcademicGroup ( ))
                    .groupLength (dto.getGroupLength ( ))
                    .startDate (dto.getStartDate ( ))
                    .fieldOfStudy (fieldOfStudy).build ( );

            academicGroupRepository.save (academicGroup);
        }
    }

    public void delete(Long id) {
        log.info ("Remove : " + id);
        academicGroupRepository.deleteById (id);
    }

    public List<AcademicGroupDto> academicGroupFindByStudent(Long studentId) {
        log.info ("academicGroupFindByStudent : " + studentId);
        Optional<Student> studentOptional = studentRepository.findById (studentId);
        if (studentOptional.isPresent ( )) {
            Student student = studentOptional.get ( );
            return student.getAcademicGroups ( ).stream ( )
                    .map (academicGroup -> AcademicGroupDto.builder ( )
                            .academicGroup (academicGroup.getAcademicGroup ( ))
                            .groupLength (academicGroup.getGroupLength ( ))
                            .fieldOfStudy (academicGroup.getFieldOfStudy ( ) == null ?
                                    FieldOfStudyEnum.UNKNOWN : academicGroup.getFieldOfStudy ( ).getFieldOfStudyEnum ( ))
                            .startDate (academicGroup.getStartDate ( ))
                            .id (academicGroup.getId ( ))
                            .build ( ))
                    .collect (Collectors.toList ( ));

        }
        throw new EntityNotFoundException ("Nie znaleziono studenta w tej grupie ");
    }

    public List<AcademicGroupDto> academicGroupFindByUser(Long userId) {
        log.info ("academicGroupFindByStudent : " + userId);
        Optional<ApplicationUser> applicationUserOptional = applicationUserRepository.findById (userId);
        if (applicationUserOptional.isPresent ( )) {
            ApplicationUser applicationUser = applicationUserOptional.get ( );
            return applicationUser.getStudent ().getAcademicGroups ( ).stream ( )
                    .map (academicGroup -> AcademicGroupDto.builder ( )
                            .academicGroup (academicGroup.getAcademicGroup ( ))
                            .groupLength (academicGroup.getGroupLength ( ))
                            .fieldOfStudy (academicGroup.getFieldOfStudy ( ) == null ?
                                    FieldOfStudyEnum.UNKNOWN : academicGroup.getFieldOfStudy ( ).getFieldOfStudyEnum ( ))
                            .startDate (academicGroup.getStartDate ( ))
                            .id (academicGroup.getId ( ))
                            .build ( ))
                    .collect (Collectors.toList ( ));

        }
        throw new EntityNotFoundException ("Nie znaleziono studenta academicGroupFindByUser");
    }
}
