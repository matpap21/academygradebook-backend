package com.sda.j92.academy.controller;

import com.sda.j92.academy.model.AcademicGroup;
import com.sda.j92.academy.model.FieldOfStudy;
import com.sda.j92.academy.modelDto.AcademicGroupDto;
import com.sda.j92.academy.modelDto.FieldOfStudyDto;
import com.sda.j92.academy.service.AcademicGroupService;
import com.sda.j92.academy.service.FieldOfStudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/fieldsofstudy")
@RequiredArgsConstructor
public class FieldOfStudyController {
    private final FieldOfStudyService fieldOfStudyService;
    private final AcademicGroupService academicGroupService;

    @CrossOrigin()
    @GetMapping
    public List<FieldOfStudyDto> get(){
        return fieldOfStudyService.findAll ();
    }

    @CrossOrigin()
    @PostMapping("")
    public void add(@RequestBody FieldOfStudyDto fieldOfStudy){
        fieldOfStudyService.add (fieldOfStudy);
    }

    @CrossOrigin()
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        fieldOfStudyService.delete (id);
    }

    @CrossOrigin()
    @PostMapping("/addgroup/{fieldofstudyid}")
    public void add(@PathVariable Long fieldofstudyid, @RequestBody AcademicGroupDto academicGroup){
        academicGroupService.add (fieldofstudyid, academicGroup);
    }
}
