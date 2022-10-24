package application;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("====== TEST 1: SELLER FINDBYID =====");
		Seller seller = sellerDao.findById(5);
		System.out.println(seller);
		
		System.out.println("\n====== TEST 2: SELLER findByDepartment =====");
		Department dep = new Department(2,"null");
		List<Seller> list2  = sellerDao.findByDepartment(dep);
		for(Seller s : list2) {
			System.out.println(s);
		}
		
		System.out.println("\n====== TEST 3: SELLER findAll =====");
		List<Seller> list3 = sellerDao.findAll();
		for(Seller s : list3) {
			System.out.println(s);
		}
		
		System.out.println("\n====== TEST 4: SELLER Insert =====");
			Seller newSeller = new Seller(null, "Greg","greg@gmail.com", LocalDate.now(), 3000.0, dep);
			sellerDao.insert(newSeller);
			System.out.println("Inserted! New Id = "+ newSeller.getId());
		
		System.out.println("\n====== TEST 5: SELLER UpDate =====");	
			seller = sellerDao.findById(1);
			seller.setName("Martha Waine");
			sellerDao.update(seller);
			System.out.println("Update Completed");
		
		System.out.println("\n====== TEST 6: SELLER Delete =====");			
			System.out.println("Entre com o Registro para Deletar : ");
			Integer id = sc.nextInt();
			sellerDao.deleteById(id);
			System.out.println("Registro Deletado");
	}
	

}
