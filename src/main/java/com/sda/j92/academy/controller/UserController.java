package com.sda.j92.academy.controller;

import com.sda.j92.academy.model.ApplicationUser;
import com.sda.j92.academy.modelDto.ApplicationUserDto;
import com.sda.j92.academy.modelDto.LecturerDto;
import com.sda.j92.academy.modelDto.RegisterApplicationUserDto;
import com.sda.j92.academy.service.ApplicationUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final ApplicationUserService applicationUserService;

    @GetMapping("")
    public ApplicationUserDto get(Principal principal) {
        Optional<Long> userIdOptional = applicationUserService.getLoggedInUserId (principal);
        if (userIdOptional.isPresent ( )) {
            Long loggedInUserId = userIdOptional.get ( );
            return applicationUserService.getLoggedInUserDto (loggedInUserId);
        }
        throw new EntityNotFoundException ("Unable to find user.");
    }

    @GetMapping("/lecturers")
    public List<LecturerDto> getLecturers() {
        return applicationUserService.listLecturers ( );
    }

    @GetMapping("/students")
    public List<ApplicationUserDto> getStudents() {
        return applicationUserService.listStudents ( );
    }

    @CrossOrigin
    @PostMapping("/register")
    public void get(@RequestBody RegisterApplicationUserDto dto) {
        applicationUserService.register (dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        applicationUserService.removeUser (id);
    }
}
