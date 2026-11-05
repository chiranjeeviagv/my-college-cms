package com.college.cms.repository;

import com.college.cms.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    // Spring Data JPA handles all standard CRUD operations automatically
}
