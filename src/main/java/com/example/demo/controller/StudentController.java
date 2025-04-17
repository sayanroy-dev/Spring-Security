package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Student;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class StudentController {
	List<Student> students = new ArrayList<>(List.of(
			new Student(1,"Sayan",87),
			new Student(2,"Arnab",36)
			));

	@GetMapping("/students")
	public List<Student> getStudents() {
		System.out.println(students.toString());
		return students;
	}
	
	@GetMapping("/csrf-token")
	public CsrfToken getCsrfToken(HttpServletRequest request) {
		return (CsrfToken)request.getAttribute("_csrf");
	}
	
	@PostMapping("/students")
	public void addStudent(@RequestBody Student student) {
		students.add(student);
		System.out.println(students.toString());
	}
}
