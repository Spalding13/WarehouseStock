package com.example.demo.services;

import java.util.List;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.example.demo.dto.StockDto;
import com.example.demo.entities.Stock;
import com.example.demo.entities.Warehouse;
import com.example.demo.repos.StockRepo;
import com.example.demo.repos.WarehouseRepo;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class WarehouseService {
	
	@Autowired
	WarehouseRepo warehouseRepo;
	
	//GET all warehouses
	public List<Warehouse> getWarehouses() {
		return warehouseRepo.findAll();
	}
	
	//GET all warehouses - pdf
	public ByteArrayInputStream getWarehousesPdf() throws DocumentException, FileNotFoundException {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			List<Warehouse> warehouses = new ArrayList(warehouseRepo.findAll());
			
			Document document = new Document();
			 
			
			//Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
			//Chunk chunk = new Chunk(new String(warehouses.split("")), font);
			
			PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(60);
            table.setWidths(new int[]{1, 3, 3, 3});

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("Id", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Name", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);
            
            hcell = new PdfPCell(new Phrase("Capacity", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Adress", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);
			
			for (Warehouse warehouse : warehouses) {

                PdfPCell cell;

                cell = new PdfPCell(new Phrase(new String(String.valueOf(warehouse.getId()))));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(warehouse.getName()));
                cell.setPaddingLeft(5);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase(String.valueOf(warehouse.getCapacity())));
                cell.setPaddingLeft(5);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(warehouse.getAdress())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);
            }
			 
			PdfWriter.getInstance(document, out);
			document.open();
			document.add(table);
			
			document.close();
			
			return new ByteArrayInputStream(out.toByteArray());

		}
	
	
	//POST a single warehouse
	public void postWarehouse(Warehouse warehouse) {
		String name = warehouse.getName();
		//Check to see if there is already a warehouse with that name
		if(warehouseRepo.findByName(name) != null) {
			//Send error message!
			System.out.println("A warehouse with that name is already present!");
		} else {
			warehouseRepo.save(warehouse);	
			System.out.println("Saved " + warehouse.getName() + "!" );
		}
	}
	 
	//PUT a single warehouse
	public void updateWarehouse(Warehouse warehouse, int id) {
		Warehouse foundWarehouse = warehouseRepo.findById(id);
		if(foundWarehouse == null) {
			System.out.printf("Error, dog with id - %d not found!", id);

		} else {
			foundWarehouse.setName(warehouse.getName());
			foundWarehouse.setAdress(warehouse.getAdress());
			foundWarehouse.setCapacity(warehouse.getCapacity());
			warehouseRepo.save(foundWarehouse);
		}
	}	
	
	//DELETE a warehouse
	public void deleteWarehouse(int id) {
		Warehouse foundWarehouse = warehouseRepo.findById(id);
		if(foundWarehouse == null) {
			System.out.printf("Error, dog with id - %d not found!", id);

		} else {
			warehouseRepo.deleteById(id);
		}
	}	
}
