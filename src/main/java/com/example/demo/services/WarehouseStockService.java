package com.example.demo.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.entities.Stock;
import com.example.demo.entities.Warehouse;
import com.example.demo.repos.StockRepo;
import com.example.demo.repos.WarehouseRepo;


@Service
public class WarehouseStockService {

	@Autowired
	StockRepo stockRepo;
	
	@Autowired
	WarehouseRepo warehouseRepo;
	
	
	public List<Stock> getAllStockFromWarehouse(int warehouseId) {
		Warehouse foundWarehouse = warehouseRepo.findById(warehouseId);
		if(foundWarehouse == null) {
			System.out.printf("Warehouse with id - %d not found!", warehouseId);
			return null;
		} else {
			System.out.printf("Stock in warehouse with id - %d: %s\n",warehouseId, foundWarehouse);
			return warehouseRepo.findById(warehouseId).getStocks();
		}
	}
	
	public void postStockInWarehouse(int warehouseId, Stock stock) {
		Warehouse foundWarehouse = warehouseRepo.findById(warehouseId);
		if(foundWarehouse == null) {
			System.out.printf("Warehouse with id - %d not found!", warehouseId);
		} else if(foundWarehouse.getCapacity() - 1 < 0) {
			
			System.out.println(foundWarehouse.getName() + " has no capacity left!");
		} else {
			System.out.printf("Saving %s in warehouse with id - %d\n", stock.getName(), warehouseId);
			
			stock.setWarehouse(foundWarehouse);
			stockRepo.save(stock);
			
			foundWarehouse.setCapacity(foundWarehouse.getCapacity() - 1);
			foundWarehouse.addStock(stock);
			warehouseRepo.save(foundWarehouse);
		}
	}
	
	public void updateStockFromWarehouse(int warehouseId, Stock stock, int stockId) {
		Warehouse foundWarehouse = warehouseRepo.findById(warehouseId);
		if(foundWarehouse == null) {
			System.out.printf("Warehouse with id - %d not found!", warehouseId);
		} else {
			Stock foundStock = stockRepo.findById(stockId);
			if(foundStock != null) {
				System.out.printf("Modifying %s in shelter with id - %d\n", stock.getName(), warehouseId);
				foundStock.setName(stock.getName());
				foundStock.setQuantity(stock.getQuantity());
				foundStock.setPrice(stock.getPrice());

				stockRepo.save(foundStock);	
			}
		}
	}
	
	public void deleteStockFromWarehouse(int warehouseId, int stockId) {
		Warehouse foundWarehouse = warehouseRepo.findById(warehouseId);
		if(foundWarehouse == null) {
			System.out.printf("Warehouse with id - %d not found!", warehouseId);
		} else {
			Stock foundStock = foundWarehouse.getStock(stockId);
			if(foundStock != null) {
				foundWarehouse.setCapacity(foundWarehouse.getCapacity() + 1);
				foundWarehouse.removeStock(stockId);
				warehouseRepo.save(foundWarehouse);
				stockRepo.deleteById(stockId);
			}
		}
	}

	
}
