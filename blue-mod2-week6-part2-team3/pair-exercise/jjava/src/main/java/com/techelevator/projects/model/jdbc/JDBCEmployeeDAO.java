package com.techelevator.projects.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Department;
import com.techelevator.projects.model.Employee;
import com.techelevator.projects.model.EmployeeDAO;

public class JDBCEmployeeDAO implements EmployeeDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCEmployeeDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Employee> getAllEmployees() {
		
		List<Employee> employees = new ArrayList<>();
		String sqlGetTheEmployees = "SELECT * FROM employee";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetTheEmployees);
		
		while(results.next()) {
			Employee theEmployee = mapRowToEmployee(results);
			employees.add(theEmployee);
			
		}
		      return employees;
}

	@Override
	public List<Employee> searchEmployeesByName(String firstNameSearch, String lastNameSearch) {
	
		ArrayList<Employee> employees = new ArrayList<>();
		String sqlFindEmployeesByName ="";
		
		SqlRowSet results;
		
		sqlFindEmployeesByName = "SELECT * FROM employee WHERE first_name = ? And last_name = ?";
		results = jdbcTemplate.queryForRowSet(sqlFindEmployeesByName, firstNameSearch, lastNameSearch);
		while (results. next()) {
			Employee theEmployee = mapRowToEmployee(results);
			employees.add(theEmployee);
			
		}
		
		
		return employees;
}
	
		
	

	@Override
	public List<Employee> getEmployeesByDepartmentId(long id) {
		
		ArrayList<Employee> employees = new ArrayList<>();
		String sqlGetEmployeesByDeptId = "SELECT * FROM employee WHERE department_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetEmployeesByDeptId, id);
		
		while (results.next()) {
			Employee theEmployee = mapRowToEmployee(results);
			employees.add(theEmployee);
		}
		
		
		return employees;
	}

	@Override
	public List<Employee> getEmployeesWithoutProjects() {
		ArrayList<Employee> employees = new ArrayList<>();
		String query = "SELECT * " +
				"FROM employee LEFT JOIN project_employee " +
				"ON employee.employee_id = project_employee.employee_id " +
				"WHERE project_employee.project_id IS NULL " ;
				
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(query);
		
		while (results.next()) {
			Employee newEmployee = mapRowToEmployee(results);
			employees.add(newEmployee);
			
		}
	  return employees;
	}

	@Override
	public List<Employee> getEmployeesByProjectId(Long projectId) {
		
		ArrayList<Employee> employees = new ArrayList<>();
		String sqlGetEmployeesByProjectId = "SELECT * FROM employee INNER JOIN project_employee ON employee.employee_id = "
				+" project_employee.employee_id WHERE project_id =?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetEmployeesByProjectId, projectId);
		
		while (results.next()) {
			Employee theEmployee = mapRowToEmployee(results);
			employees.add(theEmployee);
		}
				
				
		return employees;
	}

	@Override
	public void changeEmployeeDepartment(Long employeeId, Long departmentId) {
		
		String sqlChangeEmployeeDept = "UPDATE employee SET department_id = ? WHERE employee_id =?";
		jdbcTemplate.update(sqlChangeEmployeeDept, departmentId, employeeId);
		
		
	}
	
	 private Employee mapRowToEmployee (SqlRowSet results) {
		 
		 Employee theEmployee;
		 
		 theEmployee = new Employee();
		 theEmployee.setId(results.getLong("employee_id"));
		 theEmployee.setDepartmentId(results.getLong("department_id"));
		 theEmployee.setFirstName(results.getString("first_name"));
		 theEmployee.setLastName(results.getString("last_name"));
		 theEmployee.setBirthDay(results.getDate("birth_date").toLocalDate());
		 theEmployee.setHireDate(results.getDate("hire_date").toLocalDate());
		 theEmployee.setGender(results.getString("gender").charAt(0));
		 return theEmployee;
		 
	 } 
	 }
			
			


