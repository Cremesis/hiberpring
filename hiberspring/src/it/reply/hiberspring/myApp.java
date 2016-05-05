package it.reply.hiberspring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class myApp {
	public static void main(String[] args) {
		/* Remove all employees from the database */
		EmployeeService employeeService =
				(EmployeeService) new ClassPathXmlApplicationContext("it/reply/hiberspring/resources/Beans.xml")
				.getBean("employeeService");
		employeeService.run();
	}
}