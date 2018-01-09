package com.dot.s3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dot.s3.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

}
