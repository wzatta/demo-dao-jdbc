package application;

import java.time.LocalDate;

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
	}

}
