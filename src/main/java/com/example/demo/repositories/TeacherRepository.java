package com.example.demo.repositories;

import com.example.demo.entities.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;



public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Page<Teacher> findByNameContains(String name, Pageable pageable);
}


