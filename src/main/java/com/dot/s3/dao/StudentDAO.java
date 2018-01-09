package com.dot.s3.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dot.s3.Student;
import com.dot.s3.repository.StudentRepository;

@Repository
public class StudentDAO {

	@Autowired
	private StudentRepository studentRepository;

	public void batchStore(List<Student> studentList) {
		studentRepository.save(studentList);
	}

	public List<Student> getStudents() {
		return studentRepository.findAll();
	}

}
