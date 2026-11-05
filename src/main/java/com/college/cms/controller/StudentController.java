package com.college.cms.controller;

import com.college.cms.model.Student;
import com.college.cms.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // This handles the landing page link http://localhost:8081/
    @GetMapping("/")
    public String viewDashboard(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        return "dashboard"; // This must match your templates/dashboard.html file name exactly
    }

    @GetMapping("/add-student")
    public String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        return "add-student"; // This must match templates/add-student.html exactly
    }

    @PostMapping("/save-student")
    public String saveStudent(@ModelAttribute("student") Student student) {
        studentRepository.save(student);
        return "redirect:/";
    }
}
