package it.reply.hiberspring.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import it.reply.hiberspring.Employee;

public interface EmployeeDao {
	void deleteAllEmployee();
	Integer addEmployee(String fname, String lname, int salary);
	void listEmployees(int salary);
	void countEmployees();
	void totalSalary();
	List<Employee> greaterSalary() throws DataAccessException;
}
