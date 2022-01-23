package com.sda.j92.academy.service;

import com.sda.j92.academy.model.*;
import com.sda.j92.academy.modelDto.AcademicGroupDto;
import com.sda.j92.academy.modelDto.CreateStudentDto;
import com.sda.j92.academy.modelDto.GradeDto;
import com.sda.j92.academy.modelDto.StudentDto;
import com.sda.j92.academy.repository.AcademicGroupRepository;
import com.sda.j92.academy.repository.ApplicationUserRepository;
import com.sda.j92.academy.repository.GradeRepository;
import com.sda.j92.academy.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final AcademicGroupRepository academicGroupRepository;
    private final GradeRepository gradeRepository;
    private final ApplicationUserRepository applicationUserRepository;


    public List<StudentDto> findAll() {
        List<Student> students = studentRepository.findAll ( );
        log.info ("GetAll : " + students);
        return students.stream ( )
                .map (student -> StudentDto.builder ( )
                        .id (student.getId ( ))
                        .pesel (student.getPesel ( ))
                        .name (student.getName ( ))
                        .surname (student.getSurname ( ))
                        .phoneNumber (student.getPhoneNumber ( ))
                        .email (student.getEmail ( ))
                        .academicGroups (student.getAcademicGroups ( ).stream ( )
                                .map (academicGroup -> AcademicGroupDto.builder ( )
                                        .academicGroup (academicGroup.getAcademicGroup ( ))
                                        .groupLength (academicGroup.getGroupLength ( ))
                                        .fieldOfStudy (academicGroup.getFieldOfStudy ( ) == null ?
                                                FieldOfStudyEnum.UNKNOWN : academicGroup.getFieldOfStudy ( ).getFieldOfStudyEnum ( ))
                                        .startDate (academicGroup.getStartDate ( ))
                                        .build ( ))
                                .collect (Collectors.toSet ( )))
                        .build ( ))
                .collect (Collectors.toList ( ));
    }

    public void add(Long academicGroupsId, CreateStudentDto student) {
        log.info ("Add : " + student + academicGroupsId);

        Optional<ApplicationUser> applicationUserOptional = applicationUserRepository.findById (student.getUserId ( ));
        if (applicationUserOptional.isPresent ( )) {
            ApplicationUser applicationUser = applicationUserOptional.get ( );

            Optional<AcademicGroup> academicGroupOptional = academicGroupRepository.findById (academicGroupsId);
            if (academicGroupOptional.isPresent ( )) {
                AcademicGroup academicGroups = academicGroupOptional.get ( );
                Student createdStudent;
                if(applicationUser.getStudent ()== null) {
                    createdStudent = Student.builder ( ).pesel (student.getPesel ( )).name (student.getName ( )).surname (student.getSurname ( ))
                            .phoneNumber (student.getPhoneNumber ( )).email (student.getEmail ( )).academicGroups (new HashSet<> ( )).build ( );

                }else{
                    createdStudent = applicationUser.getStudent ();
                }

                createdStudent.setPesel (student.getPesel ( ));
                createdStudent.setName (student.getName ( ));
                createdStudent.setSurname (student.getSurname ( ));
                createdStudent.setPhoneNumber (student.getPhoneNumber ( ));
                createdStudent.setEmail (student.getEmail ( ));
                createdStudent.getAcademicGroups ( ).add (academicGroups);

                createdStudent = studentRepository.save (createdStudent);
                applicationUser.setStudent (createdStudent);
                applicationUserRepository.save (applicationUser);
                return;
            }
            throw new EntityNotFoundException ("Academic group ID was not found: " + academicGroupsId);
        }
        throw new EntityNotFoundException ("User with provided Id was not found: " + student.getUserId ( ));
    }

    public void add(Long academicGroupsId, Long studentid) {
        log.info ("Add : " + studentid + " " + academicGroupsId);
        Optional<AcademicGroup> academicGroupOptional = academicGroupRepository.findById (academicGroupsId);
        if (academicGroupOptional.isPresent ( )) {
            AcademicGroup academicGroups = academicGroupOptional.get ( );
            Optional<Student> studentOptional = studentRepository.findById (studentid);
            if (studentOptional.isPresent ( )) {
                Student student = studentOptional.get ( );
                student.getAcademicGroups ( ).add (academicGroups);
                studentRepository.save (student);
                return;

            }
            throw new EntityNotFoundException ("Student with provided Id was not found: " + studentid);

        }
        throw new EntityNotFoundException ("Academic group ID was not found: " + academicGroupsId);
    }

    public void delete(Long id) {
        log.info ("Remove : " + id);
        studentRepository.deleteById (id);
    }


    public StudentDto findStudent(Long id) {
        Optional<Student> studentDtoOptional = studentRepository.findById (id);
        if (studentDtoOptional.isPresent ( )) {
            Student student = studentDtoOptional.get ( );
            return
                    StudentDto.builder ( )
                            .id (student.getId ( ))
                            .pesel (student.getPesel ( ))
                            .name (student.getName ( ))
                            .surname (student.getSurname ( ))
                            .phoneNumber (student.getPhoneNumber ( ))
                            .email (student.getEmail ( ))
                            .academicGroups (student.getAcademicGroups ( ).stream ( )
                                    .map (academicGroup -> AcademicGroupDto.builder ( )
                                            .academicGroup (academicGroup.getAcademicGroup ( ))
                                            .groupLength (academicGroup.getGroupLength ( ))
                                            .fieldOfStudy (academicGroup.getFieldOfStudy ( ) == null ?
                                                    FieldOfStudyEnum.UNKNOWN : academicGroup.getFieldOfStudy ( ).getFieldOfStudyEnum ( ))
                                            .startDate (academicGroup.getStartDate ( ))
                                            .build ( ))
                                    .collect (Collectors.toSet ( )))
                            .build ( );


        }
        throw new EntityNotFoundException ( );
    }

    public StudentDto findApplicationUser(Long id) {
        Optional<ApplicationUser> applicationUserOptional = applicationUserRepository.findById (id);
        if (applicationUserOptional.isPresent ( )) {
            ApplicationUser applicationUser = applicationUserOptional.get ( );
            return
                    StudentDto.builder ( )
                            .id (applicationUser.getStudent ( ).getId ( ))
                            .pesel (applicationUser.getStudent ( ).getPesel ( ))
                            .name (applicationUser.getStudent ( ).getName ( ))
                            .surname (applicationUser.getStudent ( ).getSurname ( ))
                            .phoneNumber (applicationUser.getStudent ( ).getPhoneNumber ( ))
                            .email (applicationUser.getStudent ( ).getEmail ( ))
                            .academicGroups (applicationUser.getStudent ( ).getAcademicGroups ( ).stream ( )
                                    .map (academicGroup -> AcademicGroupDto.builder ( )
                                            .academicGroup (academicGroup.getAcademicGroup ( ))
                                            .groupLength (academicGroup.getGroupLength ( ))
                                            .fieldOfStudy (academicGroup.getFieldOfStudy ( ) == null ?
                                                    FieldOfStudyEnum.UNKNOWN : academicGroup.getFieldOfStudy ( ).getFieldOfStudyEnum ( ))
                                            .startDate (academicGroup.getStartDate ( ))
                                            .build ( ))
                                    .collect (Collectors.toSet ( )))
                            .build ( );


        }
        throw new EntityNotFoundException ( );
    }

    public List<StudentDto> findStudentsToBeRemoved(Long id){
        List<Student> studentDtoOptional = studentRepository.findAll ();
        return  studentDtoOptional.stream().filter (student -> student.getGrades ().stream ().map (Grade::getGrades).anyMatch (grade->grade<3)).map (student -> StudentDto.builder ( )
                .id (student.getId ( ))
                .pesel (student.getPesel ( ))
                .name (student.getName ( ))
                .surname (student.getSurname ( ))
                .phoneNumber (student.getPhoneNumber ( ))
                .email (student.getEmail ( ))
                .academicGroups (student.getAcademicGroups ( ).stream ( )
                        .map (academicGroup -> AcademicGroupDto.builder ( )
                                .academicGroup (academicGroup.getAcademicGroup ( ))
                                .groupLength (academicGroup.getGroupLength ( ))
                                .fieldOfStudy (academicGroup.getFieldOfStudy ( ) == null ?
                                        FieldOfStudyEnum.UNKNOWN : academicGroup.getFieldOfStudy ( ).getFieldOfStudyEnum ( ))
                                .startDate (academicGroup.getStartDate ( ))
                                .build ( ))
                        .collect (Collectors.toSet ( )))
                .build ( ))
                .collect (Collectors.toList ( ));

        }
    }
