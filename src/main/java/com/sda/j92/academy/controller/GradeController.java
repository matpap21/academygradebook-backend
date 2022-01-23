package com.sda.j92.academy.controller;

import com.sda.j92.academy.model.Grade;
import com.sda.j92.academy.modelDto.CreateGradeDto;
import com.sda.j92.academy.modelDto.GradeDto;
import com.sda.j92.academy.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@CrossOrigin()
@RestController
@RequestMapping("/grades")
@RequiredArgsConstructor

public class GradeController {
    private final GradeService gradeService;
    private final StudentService studentService;
    private final AcademicSubjectService academicSubjectService;
    private final ApplicationUserService applicationUserService;

    @CrossOrigin()
    @GetMapping("")
    public List<GradeDto> get (){
        return gradeService.findAll ();
    }

    @CrossOrigin
    @PostMapping("")
    public void add(@RequestBody CreateGradeDto dto, Principal principal) {
        Optional<Long> userIdOptional = applicationUserService.getLoggedInUserId (principal);
        if( userIdOptional.isPresent () ) {
            gradeService.add (dto, userIdOptional.get ( ));
        }
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        gradeService.delete (id);
    }

    @CrossOrigin()
    @GetMapping("/bystudent/{studentId}")
    public List<GradeDto> get(@PathVariable Long studentId){
        return gradeService.gradesByStudentId (studentId);
    }

    @CrossOrigin()
    @GetMapping("/byuser")
    public List<GradeDto> get(Principal principal){
        Optional<Long> userIdOptional = applicationUserService.getLoggedInUserId (principal);
        if( userIdOptional.isPresent () ) {
            return gradeService.gradesByUserId (userIdOptional.get ());
        }
        throw new EntityNotFoundException ( "Nie znaleziono studenta List<GradeDto> get" );
    }
}
