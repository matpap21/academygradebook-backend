package com.sda.j92.academy.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
// Nie nazywać "User" ponieważ mysql ma tą tabelę zarezerwowaną
public class ApplicationUser implements UserDetails {
    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String password;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne
    private Student student;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<ApplicationUserRole> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Część Lecturer
    private String name;
    private String surname;
    private String pesel;

    private UniversityLecturerDegreesEnum universityLecturerDegreesEnum;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = "lecturers")
    private Set<AcademicSubject> academicSubjects;

    @OneToMany(mappedBy = "universityLecturer")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Grade> grades;
    //ManytoMany lub OnetoOne -- > mapped by jest po stronie rzeczy ktora sie dodaje
}
