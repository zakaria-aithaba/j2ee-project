package com.example.demo.web;

import com.example.demo.entities.Subject;
import com.example.demo.repositories.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SubjectController {

    @Autowired
    private SubjectRepository subjectRepository;


    @GetMapping(path = "/subjectList")
    public String subjects(Model model) {
        List<Subject> allSubjects = subjectRepository.findAll();
        model.addAttribute("allSubjects", allSubjects);
        return "subjects";
    }

    @GetMapping(path = "/pagedSubjectList")
    public String allSubjects(Model model,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "size", defaultValue = "3") int size,
                              @RequestParam(name = "search", defaultValue = "") String searchName) {
        Page<Subject> pageSubjects = subjectRepository.findByNameContains(searchName, PageRequest.of(page, size));

        int[] pages = new int[pageSubjects.getTotalPages()];
        for (int i = 0; i < pages.length; i++) {
            pages[i] = i;
        }
        model.addAttribute("pageSubjects", pageSubjects.getContent());
        model.addAttribute("tabPages", pages);
        model.addAttribute("size", size);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchName", searchName);

        if (pageSubjects.isEmpty()) {
            model.addAttribute("noResultsMessage", "No subjects found for the search term '" + searchName + "'.");
        }

        return "subjects";
    }

    @GetMapping(path = "/createSubject")
    public String createSubject(Model model) {
        Subject subject = new Subject();
        model.addAttribute("subject", subject);
        return "addsubject";
    }

    @PostMapping(path = "/saveSubject")
    public String saveSubject(Subject subject) {
        subjectRepository.save(subject);
        return "redirect:/pagedSubjectList";
    }

    @GetMapping(path = "/deleteSubject")
    public String deleteSubject(int page, int size, String search, @RequestParam(name = "id") Integer id) {
        subjectRepository.deleteById(id);
        return "redirect:/pagedSubjectList?page=" + page + "&size=" + size + "&search=" + search;
    }

    @GetMapping(path = "/subjectDetails")
    public String showSubjectDetails(@RequestParam(name = "id") Integer id, Model model) {
        Subject subject = subjectRepository.findById(id).orElse(null);

        if (subject == null) {
            return "redirect:/pagedSubjectList";
        }

        model.addAttribute("subject", subject);
        model.addAttribute("teacher", subject.getTeacher());

        return "subjectDetails";
    }
}
