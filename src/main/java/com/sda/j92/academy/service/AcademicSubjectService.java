package com.sda.j92.academy.service;

import com.sda.j92.academy.model.AcademicSubject;
import com.sda.j92.academy.model.ApplicationUser;
import com.sda.j92.academy.modelDto.AcademicSubjectDto;
import com.sda.j92.academy.repository.AcademicSubjectRepository;
import com.sda.j92.academy.repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AcademicSubjectService {
    private final AcademicSubjectRepository academicSubjectRepository;
    private final ApplicationUserRepository applicationUserRepository;

    public List<AcademicSubjectDto> findAll() {
        List<AcademicSubject> academicSubjects = academicSubjectRepository.findAll ( );
        log.info ("GetAll : " + academicSubjects);
        return academicSubjects.stream ( )
                .map (academicSubject -> AcademicSubjectDto.builder ( )
                        .academicSubject (academicSubject.getAcademicSubject ( ))
                        .id (academicSubject.getId ( ))
                        .build ( ))
                .collect (Collectors.toList ( ));

    }

    public void add(Long lecturerId, Long academicsubjectsid) {
        log.info ("Add : " + lecturerId + academicsubjectsid);
        Optional<ApplicationUser> applicationUserOptional = applicationUserRepository.findByUsername (String.valueOf (lecturerId));
        if (applicationUserOptional.isPresent ( )) {
            ApplicationUser applicationUser = applicationUserOptional.get ( );
            Optional<AcademicSubject> academicSubjectOptional = academicSubjectRepository.findById (academicsubjectsid);
            if (academicSubjectOptional.isPresent ( )) {
                AcademicSubject academicSubject = academicSubjectOptional.get ( );
                academicSubject.getLecturers ().add (applicationUser);
                academicSubjectRepository.save (academicSubject);
                return;
            }
            throw new EntityNotFoundException ( "Lecturer was not found" );
        }
        throw new EntityNotFoundException ( "Academic Subject ID was not found" );
    }

    public void add(AcademicSubjectDto dto) {
        log.info ("Add : " + dto);
        academicSubjectRepository.save (AcademicSubject.builder ().academicSubject (dto.getAcademicSubject ()).build ());
    }

    public void delete(Long id) {
        log.info ("Remove : " + id);
        academicSubjectRepository.deleteById (id);
    }
}
