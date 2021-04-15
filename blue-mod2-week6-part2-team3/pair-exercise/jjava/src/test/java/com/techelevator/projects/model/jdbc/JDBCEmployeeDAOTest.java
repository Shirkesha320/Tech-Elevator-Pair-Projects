
package com.techelevator.projects.model.jdbc;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.techelevator.projects.model.Department;
import com.techelevator.projects.model.Employee;
import com.techelevator.projects.model.jdbc.JDBCEmployeeDAO;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JDBCEmployeeDAOTest {
	
	
	private static final int TEST_DEPT = 60;
	private static SingleConnectionDataSource dataSource;
	private JDBCEmployeeDAO employeeDAO;
	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	private Department testDepartment;
	
	/* Before any tests are run, this method initializes the datasource for testing. */
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/pairsprojects");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		/* The following line disables autocommit for connections
		 * returned by this DataSource. This allows us to rollback
		 * any changes after each test */
		dataSource.setAutoCommit(false);
	}
	
	/* After all tests have finished running, this method will close the DataSource */
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}
	
	@Before
	public void setup() {
		
		String sqlInsertDept = "INSERT INTO department(department_id, name) VALUES(60, 'BestDeptEver')";
		jdbcTemplate.update(sqlInsertDept);
		
		String sqlInsertEmployee = "INSERT INTO employee (employee_id, first_name, last_name, birth_date, department_id, hire_date, gender) "
				+ "VALUES (100, 'Zzz', 'Zzzzz', '1953-07-09', 60, '2020-06-05', 'F')";
		jdbcTemplate.update(sqlInsertEmployee);
		
		employeeDAO = new JDBCEmployeeDAO(dataSource);
	}
	/* After each test, we rollback any changes that were made to the database so that
	 * everything is clean for the next test */
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void return_all_employees() {
		
		String testQuery = "SELECT employee_id FROM employee";
		
		SqlRowSet testResults = jdbcTemplate.queryForRowSet(testQuery);
		
		int i = 0;
		while (testResults.next()) {
			i++;
		}
		
		List<Employee> actualList = employeeDAO.getAllEmployees();
		
		assertEquals(i, actualList.size());
 	}
	
	@Test
	public void search_employees_by_name_test() {
		
		List<Employee> testEmployeeList = employeeDAO.searchEmployeesByName("Zzz", "Zzzzz");
		
		assertEquals(1, testEmployeeList.size());
		
		assertNotNull(testEmployeeList);
		
		assertEquals(testEmployeeList.get(0).getDepartmentId(), TEST_DEPT);
		
	}
	
	@Test
	public void search_employees_by_department_Id() {
		
		List<Employee> testEmployeeList = employeeDAO.getEmployeesByDepartmentId(TEST_DEPT);
		
		assertEquals(1, testEmployeeList.size());
		
		assertNotNull(testEmployeeList);
		
		assertEquals(testEmployeeList.get(0).getFirstName(), "Zzz");
	}
	
	@Test
	public void search_employees_without_projects_test() {
		
		List<Employee> testEmployeeList = employeeDAO.getEmployeesWithoutProjects();
		
		
		String sqlInsertEmployee = "INSERT INTO employee (first_name, last_name, birth_date, department_id, hire_date, gender) "
				+ "VALUES ('No', 'ProjectPerson', '1953-07-09', 60, '2020-06-05', 'F')";
		jdbcTemplate.update(sqlInsertEmployee);
		
		List<Employee> testEmployeeList2 = employeeDAO.getEmployeesWithoutProjects();
		
		assertNotEquals(testEmployeeList.size(), testEmployeeList2.size());
		assertEquals(testEmployeeList.size(), testEmployeeList2.size() - 1);
	}
	
	@Test
	public void get_employees_by_projects_test() {
		
		String sqlInsertProject = "INSERT INTO project (project_id, name, from_date, to_date)"
				+ " VALUES (50, 'BestProjectEver', '1953-07-09', '2020-06-05')";
		jdbcTemplate.update(sqlInsertProject);
		
		String sqlInsertProjectEmployee = "INSERT INTO project_employee (project_id, employee_id)"
				+ " VALUES (50, 100)";
		jdbcTemplate.update(sqlInsertProjectEmployee);
		
		List<Employee> testEmployeeList = employeeDAO.getEmployeesByProjectId((long) 50);
		
		assertEquals(1, testEmployeeList.size());
		
	}
	
	@Test
	public void change_employee_department_test() {
		
		String sqlInsertDept = "INSERT INTO department(department_id, name) VALUES (90, 'worstDeptEver')";
		
		jdbcTemplate.update(sqlInsertDept);
		employeeDAO.changeEmployeeDepartment((long)100, (long) 90);
		
		String testQuery ="Select department_id FROM employee Where employee_id = 100";
		int testResults = jdbcTemplate.queryForObject(testQuery, int.class);
		assertEquals(testResults, 90);
		
		
		
	}
}	
		//Employee employee = new Employee();
		//employee.setDepartmentId((long) 1000);
		
		//JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		//String sql = "INSERT INTO department (department_id) VALUES (?) RETURNING department_id";
		
		//Long newId = jdbcTemplate.queryForObject(sql, Long.class, employee.getDepartmentId());
		//employee.setId(newId);
		
		//String query = "SELECT department_id FROM employee WHERE department_id = ?";
		//String changeEmployee = jdbcTemplate.queryForObject(query, String.class, employee.getId());
		
		//Assert.assertEquals("CHANGED NAME TEST", changeEmployee);	
	
		
		
	
	
	





