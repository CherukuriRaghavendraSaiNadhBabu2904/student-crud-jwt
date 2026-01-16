 package com.example.repository;
  
  import java.util.List;

import org.hibernate.query.NativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Student;

import jakarta.transaction.Transactional;
  
  
@Repository
public interface StudentRepositary extends JpaRepository<Student, Long>{
	
	public List<Student> findByFirstName(String firstName);
 
	public List<Student> findByFirstNameContaining(String firstName);
	//JPQL
	@Query("select s from Student s where s.emailId = ?1")
	Student getStudentByEmailAddress(String emailId);
	
	@Query("select s.firstName from Student s where s.emailId = ?1")
	String getStudentFirstNameByEmailAddress(String emailId);
	
	@Modifying
    @Transactional
	@Query(value = "update tbl_student set first_name = ?1 WHERE email_address = ?2",
			nativeQuery = true)
	int updateStudentNameByEmailId(String firstName ,String emailId);
	
}
