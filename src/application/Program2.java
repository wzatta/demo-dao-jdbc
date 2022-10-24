package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		List<Department> list = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		DepartmentDao departDao = DaoFactory.createDepartmentDao();
		
		System.out.println("\n=== TEST 1: Department : Insert ===");
		Department dep = new Department(null,"Utensilios");
		departDao.insert(dep);
		System.out.println("Inserted! New Id = "+ dep.getId());
		
		System.out.println("\n=== TEST 2: Department : UpDate ===");
		Department dep1 = departDao.findById(23);
		dep1.setName("Novo Departamento Novo");
		departDao.update(dep1);
		System.out.println("Update Completed");
		
		System.out.println("\n==== TEST 3: Department findAll ===");
		list = departDao.findAll();
		for(Department s : list) {
			System.out.println(s);
		}
		System.out.println("\n=== TEST 4: Department Delete ===");			
		System.out.println("Entre com o Registro para Deletar : ");
		Integer id = sc.nextInt();
		departDao.deleteById(id);
		System.out.println("Registro Deletado");
		
		System.out.println("\n=== TEST 5: Department findById ===");			
		System.out.println("Entre com o Registro para Consulta : ");
		Integer id1 = sc.nextInt();
		dep1 = departDao.findById(id1);
		System.out.println(dep1);
		
	}

}
