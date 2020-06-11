package com.example.demo.repos;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entities.Employee;


public interface EmployeeRepo extends JpaRepository<Employee, Integer>{
	
	Employee findById(int id);
	
	Employee findByName(String name);
	
	void deleteByName(String name);
}
