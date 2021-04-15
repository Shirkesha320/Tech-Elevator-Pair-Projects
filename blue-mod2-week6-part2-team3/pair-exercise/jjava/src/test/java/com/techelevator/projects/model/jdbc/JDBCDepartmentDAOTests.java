package com.techelevator.projects.model.jdbc;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.projects.model.Department;

public class JDBCDepartmentDAOTests {

	private static SingleConnectionDataSource dataSource;
	
	private static JDBCDepartmentDAO dao;
	
	@BeforeClass
	public static void setUpDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/pairsprojects");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	}
	/* After all tests have finished running, this method will close the DataSource */
	@AfterClass
	public static void cleanup()  {
		dataSource.destroy();
	}
	
	@Before
		public  void setUp() {
	
		dao = new JDBCDepartmentDAO(dataSource);

}
	@After
	public void rollback() throws SQLException {
		
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void getAllDepartments_withDataInDatabase_shouldReturnMultipleResults() {
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		String dept1 = "Test Department 1";
		String dept2 = "Test Department 2";
		
		String query = "INSERT INTO department (name) VALUES (?)";
		
		jdbcTemplate.update(query, dept1);
		jdbcTemplate.update(query, dept2);
		
		List<Department> result = dao.getAllDepartments();
		
		Assert.assertNotNull(result);
		Assert.assertTrue("getAllDepartments expected at least 2 rows, got " + result.size(),
				result.size() >= 2);

}
	
	@Test
	public void createDepartment_withValidData_shouldInsertAndReturnId() {
		
		Department dept = new Department();
		dept.setName("CREATE DEPT TEST");
		
		Department result = dao.createDepartment(dept);
		
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getId());
		
		String sql = "SELECT name FROM department WHERE department_id = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		String resultString  = jdbcTemplate.queryForObject(sql, String.class, result.getId());
		
		Assert.assertNotNull(resultString);
		Assert.assertEquals("createdDepartment expected " + dept.getName() + " got " + 
				result.getName(), dept.getName(), resultString);

}
	@Test
	public void saveDepartmentTest_withValidData_shouldUpdateRecord() {
		Department dept = new Department();
		dept.setName("SAVE TEST");
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		String sql = "INSERT INTO department (name) VALUES (?) RETURNING department_id";
		
		Long newId = jdbcTemplate.queryForObject(sql, Long.class, dept.getName());
		dept.setId(newId);
		
		String query = "SELECT name FROM department WHERE department_id = ?";
		String savedDeptName = jdbcTemplate.queryForObject(query, String.class, dept.getId());
		
		Assert.assertEquals("SAVE TEST", savedDeptName);	
	}

	@Test
	public void searchDepartmentByName_withDatabase_shouldReturnResult() {
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		String dept1 = "Test Dept Name 1";
		String query = "INSERT INTO department (name) VALUES (?)";
		
		jdbcTemplate.update(query, dept1);
		
		List<Department> result = dao.searchDepartmentsByName("Test Dept Name 1");
		
		Assert.assertNotNull(result);
		Assert.assertEquals(dept1, result.get(0).getName());
		
	}


	

	


		



	
	

}
