package com.sda.j92.academy.controller;

import com.sda.j92.academy.exception.UnauthorizedException;
import com.sda.j92.academy.model.AcademicGroup;
import com.sda.j92.academy.model.Student;
import com.sda.j92.academy.modelDto.AcademicGroupDto;
import com.sda.j92.academy.modelDto.CreateStudentDto;
import com.sda.j92.academy.modelDto.StudentDto;
import com.sda.j92.academy.service.AcademicGroupService;
import com.sda.j92.academy.service.ApplicationUserService;
import com.sda.j92.academy.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@CrossOrigin()
@RestController
@RequestMapping("/academicgroups")
@RequiredArgsConstructor
public class AcademicGroupController {
    private final ApplicationUserService applicationUserService;
    private final AcademicGroupService academicGroupService;
    private final StudentService studentService;

    @CrossOrigin()
    @GetMapping("")
    public List<AcademicGroupDto> get(){
        return academicGroupService.findAll ();
    }

    @CrossOrigin()
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        academicGroupService.delete (id);
    }

    @CrossOrigin()
    @PostMapping("/addstudent/{academicGroupsId}")
    public void add(@PathVariable Long academicGroupsId, @RequestBody CreateStudentDto student){
        studentService.add (academicGroupsId, student);
    }

    @CrossOrigin()
    @PostMapping("/addstudent/{academicGroupsId}/{studentid}")
        public void add(@PathVariable Long academicGroupsId,@PathVariable Long studentid, Principal principal){
    //lista academic groupDto
        // api/academicgroups/bystudent/${props.studentId}`)
        if(applicationUserService.isAdminOrLecturer(principal)) {
            studentService.add (academicGroupsId, studentid);
        }
    }

    @CrossOrigin()
    @GetMapping("/bystudent/{studentId}")
    public List<AcademicGroupDto> get(@PathVariable Long studentId, Principal principal) {
        Optional<Long> optionalUserId = applicationUserService.getLoggedInUserId (principal);
        if (applicationUserService.isAdmin (principal) ||
                applicationUserService.isLecturer (principal) ||
                // jesteś użytkownikiem zalogowanym i pobierasz SWOJE informacje (grupy)
                (optionalUserId.isPresent ( ) && optionalUserId.get ( ).equals (studentId))) {
            return academicGroupService.academicGroupFindByStudent (studentId);
        }
        throw new UnauthorizedException ("User is unauthorized ");
    }


    @CrossOrigin()
    @GetMapping("/byuser")
    public List<AcademicGroupDto> get(Principal principal) {
        Optional<Long> optionalUserId = applicationUserService.getLoggedInUserId (principal);
        if (optionalUserId.isPresent ()) {
            return academicGroupService.academicGroupFindByUser (optionalUserId.get ());
        }
        throw new UnauthorizedException ("User is unauthorized ");
    }


}
