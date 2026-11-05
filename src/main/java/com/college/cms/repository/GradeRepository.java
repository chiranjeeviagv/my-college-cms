package com.college.cms.repository;

import com.college.cms.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByCourseId(Long courseId);
    Optional<Grade> findByCourseIdAndStudentId(Long courseId, Long studentId);
}
