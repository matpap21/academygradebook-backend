package com.sda.j92.academy.repository;

import com.sda.j92.academy.model.FieldOfStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldOfStudyRepository extends JpaRepository<FieldOfStudy,Long> {
}
