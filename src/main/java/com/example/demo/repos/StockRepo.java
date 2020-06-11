package com.example.demo.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entities.Stock;

public interface StockRepo extends JpaRepository<Stock, Integer>{

	Stock findById(int id);
	
	Stock findByName(String name);
	
	void deleteByName(String name);
}
