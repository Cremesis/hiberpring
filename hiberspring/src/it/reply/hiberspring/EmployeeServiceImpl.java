package it.reply.hiberspring;

import java.util.Iterator;
import java.util.List;

import it.reply.hiberspring.dao.EmployeeDao;

public class EmployeeServiceImpl implements EmployeeService {
	private EmployeeDao employeeDao;
	
	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}
	
	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}
	
	@Override
	public void run() {
		employeeDao.deleteAllEmployee();

		/* Add few employee records in database */
		Integer empID1 = employeeDao.addEmployee("Zara", "Ali", 2000);
		Integer empID2 = employeeDao.addEmployee("Daisy", "Das", 5000);
		Integer empID3 = employeeDao.addEmployee("John", "Paul", 5000);
		Integer empID4 = employeeDao.addEmployee("Mohd", "Yasee", 3000);

		/* List down all the employees */
		employeeDao.listEmployees(2000);

		/* Print Total employee's count */
		employeeDao.countEmployees();

		/* Print Total salary */
		employeeDao.totalSalary();
		
		List<Employee> richEmployee = employeeDao.greaterSalary();
		for (Iterator<Employee> it = richEmployee.iterator(); it.hasNext();) {
			Employee employee = it.next();
			System.out.println(employee.getFirstName() + " is rich");
		}
	}
}
