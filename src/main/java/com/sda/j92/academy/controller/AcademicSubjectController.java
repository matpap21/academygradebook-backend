package com.sda.j92.academy.controller;

import com.sda.j92.academy.modelDto.AcademicSubjectDto;
import com.sda.j92.academy.service.AcademicSubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/academicsubjects")
@RequiredArgsConstructor
public class AcademicSubjectController {
    private final AcademicSubjectService academicSubjectService;


    @CrossOrigin()
    @GetMapping("")
    public List<AcademicSubjectDto> get(){
        return academicSubjectService.findAll ();
    }

    @CrossOrigin()
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        academicSubjectService.delete(id);
    }

    @CrossOrigin()
    @PostMapping("")
            public void add(@RequestBody AcademicSubjectDto academicSubjectDto){
        academicSubjectService.add (academicSubjectDto);

    }

    @CrossOrigin()
    @PostMapping("/addlecturer/{lecturerId}/{academicsubjectsid}")
    public void add(@PathVariable Long lecturerId, @PathVariable Long academicsubjectsid){
        // TODO: dopisać w academicSubjectService
        // lecturerId == userId
        academicSubjectService.add(lecturerId,academicsubjectsid);
    }
}
