package com.college.cms.controller;

import com.college.cms.model.Professor;
import com.college.cms.repository.ProfessorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/professors")
public class ProfessorController {

    private final ProfessorRepository professorRepository;

    public ProfessorController(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    // 1. List all professors
    @GetMapping
    public String listProfessors(Model model) {
        model.addAttribute("professors", professorRepository.findAll());
        return "professors-list";
    }

    // 2. Show Add Professor Form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("professor", new Professor());
        return "add-professor";
    }

    // 3. Process Form Submission
    @PostMapping("/save")
    public String saveProfessor(@ModelAttribute("professor") Professor professor) {
        professorRepository.save(professor);
        return "redirect:/professors";
    }
}
