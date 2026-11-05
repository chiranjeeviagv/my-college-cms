package com.college.cms.controller;

import com.college.cms.model.Course;
import com.college.cms.model.Student;
import com.college.cms.repository.CourseRepository;
import com.college.cms.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public CourseController(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    // 1. List all available courses
    @GetMapping
    public String listCourses(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        return "courses-list";
    }

    // 2. Show Add Course Form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("course", new Course());
        return "add-course";
    }

    // 3. Process new course creation
    @PostMapping("/save")
    public String saveCourse(@ModelAttribute("course") Course course) {
        courseRepository.save(course);
        return "redirect:/courses";
    }

    // 4. Show Enrollment View Screen
    @GetMapping("/enroll")
    public String showEnrollForm(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        model.addAttribute("students", studentRepository.findAll());
        return "enroll-student";
    }

    // 5. Save the many-to-many connection record map
    @PostMapping("/enroll/save")
    public String enrollStudent(@RequestParam("courseId") Long courseId, 
                                @RequestParam("studentId") Long studentId) {
        Course course = courseRepository.findById(courseId).orElseThrow();
        Student student = studentRepository.findById(studentId).orElseThrow();
        
        course.addStudent(student);
        courseRepository.save(course); // Saves the relation mapping row instantly
        
        return "redirect:/courses";
    }
}
