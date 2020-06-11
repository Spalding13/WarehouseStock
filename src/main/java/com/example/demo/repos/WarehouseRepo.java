package com.example.demo.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entities.Warehouse;

public interface WarehouseRepo extends JpaRepository<Warehouse, Integer>{

	Warehouse findById(int id);
	
	Warehouse findByName(String name);
	
	void deleteByName(String name);
}