package com.sda.j92.academy.repository;

import com.sda.j92.academy.model.AcademicGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicGroupRepository extends JpaRepository<AcademicGroup,Long> {
}
