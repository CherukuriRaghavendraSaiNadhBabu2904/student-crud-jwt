package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Student;
//import com.example.activemqprocudure.MessageProducer;
import com.example.repository.StudentRepositary;

@RestController
public class HomeController {
	@Autowired
	private StudentRepositary studentRepositary;
	
		
	@GetMapping("/")
	public String index() {
		return "Welcome to SpringBoot CRUD Operation";
		
	}
	@PostMapping("/saveData")
	public Student saveData(@RequestBody Student studentEntity) {
		Student saved = studentRepositary.save(studentEntity);
		return studentEntity;
	}
	@GetMapping("/getAllStudent")
	public List<Student> getAll(){
		List<Student> studentList = studentRepositary.findAll();
		return studentList;		
	}
	//to delete particular object
	@DeleteMapping("/deleteStudent/{rollNo}")
	public String  deleteStudent(@PathVariable Long rollNo){
		Student student = studentRepositary.findById(rollNo).get();
		if(student!= null) {
			studentRepositary.delete(student);
			}
		return "Deleted Successfully";		
	}
	@PutMapping("/updateData")
	public Student updateStudentData(@RequestBody Student studentEntity) {
		studentRepositary.save(studentEntity);
		return studentEntity;
	}
	@GetMapping("/getStudent/{rollNo}")
	public Student getStudentData(@PathVariable Long rollNo) {
		return studentRepositary.findById(rollNo).get();
	}
	
}
