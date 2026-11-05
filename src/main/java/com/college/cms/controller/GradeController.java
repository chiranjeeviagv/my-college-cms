package com.college.cms.controller;

import com.college.cms.model.Course;
import com.college.cms.model.Grade;
import com.college.cms.model.Student;
import com.college.cms.repository.CourseRepository;
import com.college.cms.repository.GradeRepository;
import com.college.cms.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/grades")
public class GradeController {

    private final GradeRepository gradeRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public GradeController(GradeRepository gradeRepository, CourseRepository courseRepository, StudentRepository studentRepository) {
        this.gradeRepository = gradeRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    // 1. Select a course to manage grades
    @GetMapping
    public String showGradePanel(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        return "grades-dashboard";
    }

    // 2. View students enrolled in a specific course and enter marks
    @GetMapping("/manage/{courseId}")
    public String manageCourseGrades(@PathVariable("courseId") Long courseId, Model model) {
        Course course = courseRepository.findById(courseId).orElseThrow();
        List<Grade> existingGrades = gradeRepository.findByCourseId(courseId);

        model.addAttribute("course", course);
        model.addAttribute("enrolledStudents", course.getStudents());
        model.addAttribute("existingGrades", existingGrades);
        return "enter-marks";
    }

    // 3. Process marks form submission
    @PostMapping("/save")
    public String saveGrade(@RequestParam("courseId") Long courseId,
                            @RequestParam("studentId") Long studentId,
                            @RequestParam("marks") int marks) {
        Course course = courseRepository.findById(courseId).orElseThrow();
        Student student = studentRepository.findById(studentId).orElseThrow();

        // Simple grading scale logic
        String letterGrade = "F";
        if (marks >= 90) letterGrade = "A";
        else if (marks >= 80) letterGrade = "B";
        else if (marks >= 70) letterGrade = "C";
        else if (marks >= 50) letterGrade = "D";

        // Check if grade record already exists, if yes update it, else create new
        Grade grade = gradeRepository.findByCourseIdAndStudentId(courseId, studentId)
                .orElse(new Grade(student, course, marks, letterGrade));
        
        grade.setMarks(marks);
        grade.setLetterGrade(letterGrade);
        
        gradeRepository.save(grade);

        return "redirect:/grades/manage/" + courseId;
    }
}
