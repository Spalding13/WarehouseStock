package com.example.demo.controllers;

import java.util.List;
import org.springframework.http.MediaType;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.StockDto;
import com.example.demo.entities.Stock;
import com.example.demo.entities.Warehouse;
import com.example.demo.services.StockService;
import com.example.demo.services.WarehouseService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

@RestController
public class WarehouseController {
	
	@Autowired
	WarehouseService service;
	
	//get all warehouses
	@RequestMapping("/warehouse")
	public List<Warehouse> getWarehouses() {
		return service.getWarehouses();
	}

	//get all warehouses on a PDF file
	@RequestMapping("/warehouse/pdf")
	public ResponseEntity<InputStreamResource> getWarehousesPdf() throws FileNotFoundException, DocumentException {
		ByteArrayInputStream bis = service.getWarehousesPdf();
		
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Disposition", "inline; filename=warehouseReport.pdf");

	    return ResponseEntity
	                .ok()
	                .headers(headers)
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(new InputStreamResource(bis));
	}

	
	//post a single warehouse
	@PostMapping("/warehouse")
	public void postWarehouse(@RequestBody Warehouse warehouse) {					
		service.postWarehouse(warehouse);
	}
	
	@PutMapping("/warehouse/{warehouseId}")
	public void updateWarehouse(@RequestBody Warehouse warehouse, @PathVariable int warehouseId) {
		service.updateWarehouse(warehouse, warehouseId);
	}
	
	@DeleteMapping("/warehouse/{warehouseId}")
	public void deleteWarehouse(@PathVariable int warehouseId) {
		service.deleteWarehouse(warehouseId);
	}

}
