package application;

import java.time.LocalDate;

import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		Department obj = new Department(1,"Books");
		
		Seller seller = new Seller(1,"Bob", "bob@gmail.com", LocalDate.now(), 3000.00, obj);
		
		System.out.println(seller);
	}

}
