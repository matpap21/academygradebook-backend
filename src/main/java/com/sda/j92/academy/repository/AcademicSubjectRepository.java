package com.sda.j92.academy.repository;

import com.sda.j92.academy.model.AcademicSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicSubjectRepository extends JpaRepository<AcademicSubject,Long> {
}
