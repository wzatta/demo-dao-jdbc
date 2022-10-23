package application;

import java.util.ArrayList;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
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
		
	}

}
