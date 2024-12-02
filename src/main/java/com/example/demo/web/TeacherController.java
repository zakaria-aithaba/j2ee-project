package com.example.demo.web;

import com.example.demo.entities.Teacher;
import com.example.demo.repositories.TeacherRepository;
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
public class TeacherController {

    @Autowired
    private TeacherRepository teacherRepository;

    @GetMapping(path = "/teacherList")
    public String teachers(Model model) {
        List<Teacher> allTeachers = teacherRepository.findAll();
        model.addAttribute("allTeachers", allTeachers);
        return "teachers";
    }

    @GetMapping(path = "/pagedTeacherList")
    public String allTeachers(Model model,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "size", defaultValue = "3") int size,
                              @RequestParam(name = "search", defaultValue = "") String searchName) {
        Page<Teacher> pageTeachers = teacherRepository.findByNameContains(searchName, PageRequest.of(page, size));

        int[] pages = new int[pageTeachers.getTotalPages()];
        for (int i = 0; i < pages.length; i++) {
            pages[i] = i;
        }
        model.addAttribute("pageTeachers", pageTeachers.getContent());
        model.addAttribute("tabPages", pages);
        model.addAttribute("size", size);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchName", searchName);

        if (pageTeachers.isEmpty()) {
            model.addAttribute("noResultsMessage", "No teachers found for the search term '" + searchName + "'.");
        }

        return "teachers";
    }

    @GetMapping(path = "/createTeacher")
    public String createTeacher(Model model) {
        Teacher teacher = new Teacher();
        model.addAttribute("teacher", teacher);
        return "addteacher";
    }

    @PostMapping(path = "/saveTeacher")
    public String saveTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
        return "redirect:/pagedTeacherList";
    }

    @GetMapping(path = "/deleteTeacher")
    public String deleteTeacher(int page, int size, String search, @RequestParam(name = "id") Integer id) {
        teacherRepository.deleteById(id);
        return "redirect:/pagedTeacherList?page=" + page + "&size=" + size + "&search=" + search;
    }

    @GetMapping(path = "/teacherDetails")
    public String showTeacherSubjects(@RequestParam(name = "id") Integer id, Model model) {
        Teacher teacher = teacherRepository.findById(id).orElse(null);

        if (teacher == null) {
            return "redirect:/pagedTeacherList";
        }

        model.addAttribute("teacher", teacher);
        model.addAttribute("subjects", teacher.getSubjects());

        return "teacherDetails";
    }
}