package com.example.demo.services;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import com.example.demo.dto.StockDto;
import com.example.demo.entities.Stock;
import com.example.demo.entities.Warehouse;
import com.example.demo.repos.StockRepo;
import com.example.demo.repos.WarehouseRepo;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

@Service
public class StockService {
	
	@Autowired
	StockRepo stockRepo;
	
	@Autowired
	WarehouseRepo warehouseRepo;
	
	//GET all stocks
	public List<Stock> getAllStocks() {
		return stockRepo.findAll();
		
	}
	
	//POST a single stock
	public void postStock(StockDto stockDto) throws MessagingException {
		Stock stock = new Stock(stockDto.getName(), stockDto.getQuantity(), stockDto.getPrice());
		Warehouse foundWarehouse = warehouseRepo.findByName(stockDto.getWarehouseName());
		
		String name = stock.getName();
		
		//Check to see if there is already a warehouse with that name
		if(stockRepo.findByName(name) != null) {
			
			System.out.println("Stock with that name is already present!");
		} else if(foundWarehouse == null) {
			
			System.out.println("No warehouse with that name!");
		} else if(foundWarehouse.getCapacity() - 1 < 0) {
			sendEmail(foundWarehouse.getName() + " has no capacity left!");
			System.out.println(foundWarehouse.getName() + " has no capacity left!");
		} else {
			foundWarehouse.setCapacity(foundWarehouse.getCapacity() - 1);
			stock.setWarehouse(foundWarehouse);
			foundWarehouse.addStock(stock);

			warehouseRepo.save(foundWarehouse);
			stockRepo.save(stock);	
			
			System.out.println("Saved " + stock.getName() + "!" );
		}
	}
	 
	//PUT a single stock
	public void updateStock(Stock stock, int id) {
		Stock foundStock = stockRepo.findById(id);
		if(foundStock == null) {
			System.out.println("This stock is not found in the database!");
		} else {
			foundStock.setName(stock.getName());
			foundStock.setQuantity(stock.getQuantity());
			foundStock.setPrice(stock.getPrice());
			stockRepo.save(foundStock);
		}
	}	
	
	//DELETE a stock
	public void deleteStock(int id) {
		Stock foundStock = stockRepo.findById(id);

		if(foundStock == null) {
			System.out.printf("Error, stock with id - %d not found!", id);

		} else {
			Warehouse foundWarehouse = warehouseRepo.findByName(foundStock.getWarehouse().getName());
			foundWarehouse.setCapacity(foundWarehouse.getCapacity() + 1);
			foundWarehouse.removeStock(id);
			
			
			warehouseRepo.save(foundWarehouse);
			stockRepo.deleteById(id);
		}
	}	
	
	public void sendEmail(String msg) {

        final String username = "kakashi_m@abv.bg";
        final String password = "lupinek_1317!@";

        Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.abv.bg");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("kakashi_m@abv.bg"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("ivelina.slaveva96@gmail.com, ilianlalov@gmail.com")
            );
            message.setSubject("Stock Application Messaging System");
            message.setText("This is an automated message:"
                    + "\n\n" + msg);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}