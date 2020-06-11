package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.StockDto;
import com.example.demo.entities.Employee;
import com.example.demo.entities.Stock;
import com.example.demo.entities.Warehouse;
import com.example.demo.repos.EmployeeRepo;
import com.example.demo.repos.StockRepo;
import com.example.demo.repos.WarehouseRepo;

@Service
public class EmployeeService {
	
	@Autowired
	EmployeeRepo emplRepo;
	
	@Autowired
	WarehouseRepo warehouseRepo;
	
	//GET all employees
	public List<Employee> getAllEmployees() {
		return emplRepo.findAll();
	}
	 
	//POST a single employees
	public void postEmployee(EmployeeDto employeeDto) {
		Employee employee =  new Employee(employeeDto.getName(), employeeDto.getAge(), employeeDto.getSalary());
		Warehouse foundWarehouse = warehouseRepo.findByName(employeeDto.getWarehouseName());
		
		if(emplRepo.findByName(employee.getName()) != null) {
			System.out.println("An employee with that name is already present!");
		} else if(foundWarehouse == null) {	
			System.out.println("No warehouse with that name!");
		} else {
			employee.setWarehouse(foundWarehouse);
			emplRepo.save(employee);
			System.out.println("Saved " + employee.getName() + "!" );
		}
	}
	
	//PUT a single employees
	public void updateEmployee (Employee employee, int id) {
		Employee foundEmployee = emplRepo.findById(id);
		if (foundEmployee != null) {
			foundEmployee.setName(employee.getName());
			foundEmployee.setAge(employee.getAge());
			foundEmployee.setSalary(employee.getSalary());
		}
		
	}
	
	//DELETE a employee
	public void deleteEmployee(int id) {
		Employee foundEmployee = emplRepo.findById(id);
		if(foundEmployee == null) {
			System.out.printf("Error, employee with id - %d not found!", id);

		} else {
			emplRepo.deleteById(id);
		}
	}
}
