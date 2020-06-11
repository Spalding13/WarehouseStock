package com.example.demo.controllers;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.StockDto;
import com.example.demo.entities.Employee;
import com.example.demo.entities.Stock;
import com.example.demo.entities.Warehouse;
import com.example.demo.services.EmployeeService;
import com.example.demo.services.StockService;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeService service;
	
	//GET all employees
	@RequestMapping("/employee")
	public List<Employee> getEmployees() {
		return service.getAllEmployees();
	}

	//POST a single employees
	@PostMapping("/employee")
	public void postEmployee(@RequestBody EmployeeDto employee) {					
		service.postEmployee(employee);
	}
	
	//PUT a single employees
	@PutMapping("/employee/{employeeId}")
	public void updateEmployee(@RequestBody Employee employee, @PathVariable int employeeId) {
		service.updateEmployee(employee, employeeId);
	}
	
	//DELETE a employee
	@DeleteMapping("/employee/{employeeId}")
	public void deleteEmployee(@PathVariable int employeeId) {
		service.deleteEmployee(employeeId);
	}	
	
}
