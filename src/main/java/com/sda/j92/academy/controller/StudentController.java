package com.sda.j92.academy.controller;

import com.sda.j92.academy.exception.UnauthorizedException;
import com.sda.j92.academy.model.Student;
import com.sda.j92.academy.modelDto.StudentDto;
import com.sda.j92.academy.service.ApplicationUserService;
import com.sda.j92.academy.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@CrossOrigin()
@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final ApplicationUserService applicationUserService;

    @CrossOrigin()
    @GetMapping("")
    public List<StudentDto> get() {
        return studentService.findAll ( );
    }

    @CrossOrigin()
    @GetMapping("/{id}")
    public StudentDto getById(@PathVariable Long id) {
        return studentService.findStudent (id);
    }

    @CrossOrigin()
    @GetMapping("/byuser")
    public StudentDto getByUserId(Principal principal) {
        Optional<Long> optionalUserId = applicationUserService.getLoggedInUserId (principal);
        if (optionalUserId.isPresent ()) {
            return studentService.findApplicationUser (optionalUserId.get ());
        }
        throw new UnauthorizedException ("User is unauthorized ");
    }


    @CrossOrigin()
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        studentService.delete (id);
    }


}
