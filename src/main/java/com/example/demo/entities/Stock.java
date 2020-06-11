package com.example.demo.entities;
//taskkill /F /PID 2804
//netstat -ano | findstr :8080
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Stock {
	
	@javax.persistence.Id
	@GeneratedValue
	private int Id;

	String name;
	int quantity;
	int price;
	
	//---------ManyToOne------------------	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnore
	private Warehouse warehouse;

	//---------ManyToOne------------------
	
	public Stock(String name, int quantity, int price) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}
	
	public Stock() {
		super();
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
	
}
