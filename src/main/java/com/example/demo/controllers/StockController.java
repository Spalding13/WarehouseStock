package com.example.demo.controllers;

import java.util.List;

import javax.mail.MessagingException;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.StockDto;
import com.example.demo.entities.Stock;
import com.example.demo.services.StockService;

@RestController
public class StockController {
	
	@Autowired
	StockService service;
	
	
	//GET stocks
	@RequestMapping("/stock")
	public List<Stock> getAllStocks() {
		return service.getAllStocks();
	}
	
	//POST a stock
	@PostMapping("/stock")
	public void postStock(@RequestBody StockDto stock) throws MessagingException {	
		service.postStock(stock);
	}
	
	//PUT a stock
	@PutMapping("/stock/{stockId}")
	public void updateStock(@RequestBody Stock stock, @PathVariable int stockId) {
		service.updateStock(stock, stockId);
	}
	
	//DELETE a stock
	@DeleteMapping("/stock/{id}")
	public void deleteStock(@PathVariable int id) {
		service.deleteStock(id);
	}

}
