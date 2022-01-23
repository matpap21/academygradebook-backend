package com.sda.j92.academy.service;

import com.sda.j92.academy.model.*;
import com.sda.j92.academy.modelDto.CreateGradeDto;
import com.sda.j92.academy.modelDto.GradeDto;
import com.sda.j92.academy.repository.*;
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
public class GradeService {
    private final ApplicationUserRepository applicationUserRepository;
    private final AcademicSubjectRepository academicSubjectRepository;
    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;

    public List<GradeDto> findAll() {
        List<Grade> grades = gradeRepository.findAll ( );
        log.info("GetAll : " + grades);
        return grades.stream ()
                .map (grade -> GradeDto.builder ()
                        .id (grade.getId ())
                        .grades (grade.getGrades ())
                        .academicSubject (grade.getAcademicSubject ().getAcademicSubject ())
                        .lecturerName (grade.getUniversityLecturer ().getName ())
                        .lecturerSurname (grade.getUniversityLecturer ().getSurname ())
                        .studentsName (grade.getStudent ().getName ())
                        .studentsSurname (grade.getStudent ().getSurname ())
                        .build ())
                .collect(Collectors.toList());
    }

    public void add(CreateGradeDto dto, Long lecturerId){
        log.info ("Add " + dto);

        ApplicationUser universityLecturer = null;
        AcademicSubject academicSubject = null;
        Student student = null;

        Optional<ApplicationUser> lecturerOptional = applicationUserRepository.findById (lecturerId);
        if (lecturerOptional.isPresent ()){
            universityLecturer = lecturerOptional.get ();
        }else{
            throw new EntityNotFoundException ( "Nie znaleziono profesora o danym ID " );
        }

        Optional<AcademicSubject> academicSubjectOptional = academicSubjectRepository.findById (dto.getSubjectId ());
        if (academicSubjectOptional.isPresent ()){
            academicSubject = academicSubjectOptional.get ();
        }else{
            throw new EntityNotFoundException ( "Nie znaleziono przedmiotu o danym ID " );
        }

        Optional<Student> studentOptional = studentRepository.findById (dto.getStudentId ());
        if (studentOptional.isPresent ()){
             student = studentOptional.get ();
        }else{
            throw new EntityNotFoundException ( "Nie znaleziono studenta o danym ID " );
        }

        Grade grade = Grade.builder ()
                .academicSubject (academicSubject)
                .student (student)
                .universityLecturer (universityLecturer)
                .grades (dto.getGrades ())
                .build ();

        gradeRepository.save (grade);
    }

    public void delete(Long id){
        log.info ("Remove: " + id);
        gradeRepository.deleteById (id);
    }

    public void addGradeToStudent(Grade grade, Student student){
        log.info ("Add: " + grade + " to Student" + student);
        //studentRepository.save (grade, student);
    }

    public List<GradeDto> gradesByStudentId(Long studentId) {
        log.info ("gradesByStudentId : " +studentId );
        Optional<Student> studentOptional = studentRepository.findById (studentId);
        if (studentOptional.isPresent ()){
            Student student = studentOptional.get ();
            return student.getGrades ().stream ()
                    .map (grade -> GradeDto.builder ()
                            .id (grade.getId ())
                            .grades (grade.getGrades ())
                            .academicSubject (grade.getAcademicSubject ().getAcademicSubject ())
                            .lecturerName (grade.getUniversityLecturer ().getName ())
                            .lecturerSurname (grade.getUniversityLecturer ().getSurname ())
                            .studentsName (grade.getStudent ().getName ())
                            .studentsSurname (grade.getStudent ().getSurname ())
                            .build ())
                    .collect(Collectors.toList());
        }
        throw new EntityNotFoundException ( "Nie znaleziono studenta w tej grupie " );
    }

    public List<GradeDto> gradesByUserId(Long userId) {
        log.info ("gradesByUserId : " +userId );
        Optional<ApplicationUser> applicationUserOptional = applicationUserRepository.findById (userId);
        if (applicationUserOptional.isPresent ()){
            ApplicationUser applicationUser = applicationUserOptional.get ();
            return applicationUser.getStudent ().getGrades ().stream ()
                    .map (grade -> GradeDto.builder ()
                            .id (grade.getId ())
                            .grades (grade.getGrades ())
                            .academicSubject (grade.getAcademicSubject ().getAcademicSubject ())
                            .lecturerName (grade.getUniversityLecturer ().getName ())
                            .lecturerSurname (grade.getUniversityLecturer ().getSurname ())
                            .studentsName (grade.getStudent ().getName ())
                            .studentsSurname (grade.getStudent ().getSurname ())
                            .build ())
                    .collect(Collectors.toList());
        }
        throw new EntityNotFoundException ( "Nie znaleziono studenta w tej grupie " );
    }
}
