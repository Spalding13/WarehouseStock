package com.example.demo.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Stock;
import com.example.demo.services.WarehouseStockService;



@RestController
public class WarehouseStockController {

	@Autowired
	private void WarehouseStock() {

	} 
	
	@Autowired
	WarehouseStockService service;
	
	//GET - get all stocks from warehouse
	@RequestMapping("/warehouse/{warehouseId}/stock")
	public  List<Stock> getAllStockFromWarehouse(@PathVariable int warehouseId) {
		return service.getAllStockFromWarehouse(warehouseId);
	}
		
	//POST - create a new stock from warehouse
	@PostMapping("/warehouse/{warehouseId}/stock")
	public void postStockInWarehouse(@PathVariable int warehouseId, @RequestBody Stock stock) {					
		service.postStockInWarehouse(warehouseId, stock);
	}
		
	//PUT - modify information on specific stock from warehouse
	@PutMapping("/warehouse/{warehouseId}/stock/{stockID}")
	public void updateStockFromWarehouse (@PathVariable int warehouseId, @RequestBody Stock stock, @PathVariable int stockID) {					
		service.updateStockFromWarehouse(warehouseId, stock, stockID);
	}
		
	//DELETE - delete a single stock entry from warehouse
	@DeleteMapping("/warehouse/{warehouseId}/stock/{stockID}")
	public void deleteStockFromWarehouse(@PathVariable int warehouseId, @PathVariable int stockID) {
		service.deleteStockFromWarehouse(warehouseId, stockID);
	}
	
}
