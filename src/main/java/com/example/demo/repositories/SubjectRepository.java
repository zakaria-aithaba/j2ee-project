package com.example.demo.repositories;

import com.example.demo.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    Page<Subject>findByNameContains(String name, Pageable pageable);
}

