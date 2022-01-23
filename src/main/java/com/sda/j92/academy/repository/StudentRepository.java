package com.sda.j92.academy.repository;

import com.sda.j92.academy.model.ApplicationUser;
import com.sda.j92.academy.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}

