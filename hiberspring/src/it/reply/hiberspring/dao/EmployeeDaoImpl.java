package it.reply.hiberspring.dao;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import it.reply.hiberspring.Employee;

public abstract class EmployeeDaoImpl extends HibernateDaoSupport implements EmployeeDao {

	protected abstract Employee buildEmployee();

	@Override
	public void deleteAllEmployee() {
		getHibernateTemplate().bulkUpdate("delete from Employee");
	}
	
	/* Method to CREATE an employee in the database */
	@Override
	public Integer addEmployee(String fname, String lname, int salary) {
		Integer employeeID = null;
		Employee employee = buildEmployee();
		employee.setFirstName(fname);
		employee.setLastName(lname);
		employee.setSalary(salary);
		employeeID = (Integer) getHibernateTemplate().save(employee);
		return employeeID;
	}

	/* Method to READ all the employees having salary more than 2000 */
	@Override
	public void listEmployees(int salary) {
		DetachedCriteria cr = DetachedCriteria.forClass(Employee.class).add(Restrictions.gt("salary", salary));
		
		List<Employee> employees = getHibernateTemplate().findByCriteria(cr);

		for (Iterator<Employee> iterator = employees.iterator(); iterator.hasNext();) {
			Employee employee = iterator.next();
			System.out.print("First Name: " + employee.getFirstName());
			System.out.print("  Last Name: " + employee.getLastName());
			System.out.println("  Salary: " + employee.getSalary());
		}
	}

	/* Method to print total number of records */
	@Override
	public void countEmployees() {
		DetachedCriteria cr = DetachedCriteria.forClass(Employee.class).setProjection(Projections.rowCount());

		List<Employee> rowCount = getHibernateTemplate().findByCriteria(cr);

		System.out.println("Total Count: " + rowCount.get(0));
	}

	/* Method to print sum of salaries */
	@Override
	public void totalSalary() {
		DetachedCriteria cr = DetachedCriteria.forClass(Employee.class).setProjection(Projections.sum("salary"));

		List<Employee> totalSalary = getHibernateTemplate().findByCriteria(cr);

		System.out.println("Total Salary: " + totalSalary.get(0));
	}
	
	/*
	 * Example of query using the callback approach to reach the "Session"
	 * associated with the template
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> greaterSalary() throws DataAccessException {
		return (List<Employee>) getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				DetachedCriteria maxSalary = DetachedCriteria.forClass(Employee.class).setProjection(Projections.max("salary"));
//				Criteria cr = session.createCriteria(Employee.class).add(Subqueries.eq("salary", maxSalary));
				Criteria cr = session.createCriteria(Employee.class).add(Property.forName("salary").eq(maxSalary));
				return cr.list();
			}
		});
//		return this.hTemplate.execute(new HibernateCallback() {
//			public Object doInHibernate(Session session) {
//			Criteria criteria = session.createCriteria(Product.class);
//			criteria.add(Expression.eq("category", category));
//			criteria.setMaxResults(6);
//			return criteria.list();
//			}
//			};
	}
}
