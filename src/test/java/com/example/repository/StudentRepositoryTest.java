package com.example.repository;

import com.example.entity.Guardian;
import com.example.entity.Student;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class StudentRepositoryTest {

	@Autowired
	private StudentRepositary studentRepository;

	@Test
	void saveStudent() {
		Guardian guardian = Guardian.builder().email("NOT NULL").name("NOT NULL").mobile("NOT NULL").build();
		// given
		Student student = Student.builder().firstName("Raghavedra").lastName("Cherukuri")
				.emailId("cherukuri2904@gmail.com")/*
													 * .guardianName("SRRGV") .guardianMobile("9248283448")
													 * .guardianEmail("12342@GMAIL.COM")
													 */.guardian(guardian).build();

		studentRepository.save(student);
	}

	@Test
	public void saveStudentWIthGuardian() {

		Guardian guardian = Guardian.builder().email("12342@GMAIL.COM").name("RAghavendra").mobile("92481813448")
				.build();
		Student student = Student.builder().firstName("New Raghavendra").lastName("New Cherukuri")
				.emailId("Newcherukuri@gmail.com").guardian(guardian).build();
		studentRepository.save(student);
	}

	@Test
	public void printAllStudent() {
		List<Student> studentList = studentRepository.findAll();

		System.out.println("studentList" + studentList);
	}

	@Test
	public void printStudentByFirstName() {

		List<Student> student = studentRepository.findByFirstName("Raghavedra");
		System.out.println("student" + student);
	}

	@Test
	public void findByFirstNameContaining() {

		List<Student> student = studentRepository.findByFirstNameContaining("Ra");
		System.out.println("***student" + student);
	}

	@Test
	public void getStudentByEmailAddress() {

		Student student = studentRepository.getStudentByEmailAddress("cherukuri2904@gmail.com");
		System.out.println("***getStudentByEmailAddress " + student);
	}
	
	@Test
	public void getStudentFirstNameByEmailAddress() {

		String student = studentRepository.getStudentFirstNameByEmailAddress("cherukuri2904@gmail.com");
		System.out.println("***getStudentFirstNameByEmailAddress " + student);
	}
	
	@Test
	public void updateStudentNameByEmailIdTest() {
		int rows = studentRepository.updateStudentNameByEmailId("Updated++ Raghavendra", "Newcherukuri@gmail.com");
		
		System.out.println("Excuted.........");
		System.out.println("ROWSS______" + rows);
	}
}
