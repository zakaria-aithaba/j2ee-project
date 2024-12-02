package com.example.demo.services;

import com.example.demo.entities.Teacher;

import java.util.List;

public interface TeacherService {
    List<Teacher> getAllTeachers();
    Teacher getTeacherById(Integer id);
    Teacher saveTeacher(Teacher teacher);
    void deleteTeacher(Integer id);
}
