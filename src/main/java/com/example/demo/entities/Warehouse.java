package com.example.demo.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;

@Entity
public class Warehouse {

	@javax.persistence.Id
	@GeneratedValue
	private int Id;
	
	String name;
	String adress;
	int capacity;
	
	// ---------OneToMany-------------------
	@OneToMany(fetch=FetchType.LAZY, cascade = {CascadeType.ALL})
	private List <Stock> stocks;
	
	@OneToMany(mappedBy = "warehouse", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List <Employee> employee;
	// ---------OneToMany-------------------
	
	public Warehouse(String name, String adress,int capacity) {
		this.name = name;
		this.adress = adress;
		this.capacity = capacity;
	}
	
	public Warehouse() {
		super();
	}


	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	
	
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	//-----------------Stocks--------------------
	public List<Stock> getStocks() {
		return stocks;
	}
	
	public void addStock(Stock stock) {
		this.stocks.add(stock);
	}

	public Stock getStock(int id) {
		for (Stock stock: stocks) {
			if(stock.getId() == id) {
				return stock;
			}
		}
		return null;
	}
	
	public void removeStock(int stockId) {
		for (int i = 0; i < stocks.size(); i++) {
			if(stocks.get(i).getId() == stockId) {
				System.out.println("Deleted stock with id: " + stockId + "!");
				stocks.remove(i);
			}
		}
		System.out.println("No stock with id" + stockId + "!");
	}
	
}
