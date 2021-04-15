package com.techelevator.projects.model.jdbc;

import static org.junit.Assert.assertNotNull;

import java.sql.Date;
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
import com.techelevator.projects.model.Project;

public class JDCBCProjectDOATest {
	
private static SingleConnectionDataSource dataSource;
private static JDBCProjectDAO dao;
private static JDBCEmployeeDAO daoEmployee;
private static JDBCDepartmentDAO daoDepartment;
private static final String TESTING_PROJECT = "GET_IN";

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
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	
	@Before
	public  void setUp() {

	dao = new JDBCProjectDAO(dataSource);
	
	Date from_date = Date.valueOf("2020-03-20");
	Date to_date = Date.valueOf("2021-03-20");
	String sqlInsertProject = "INSERT INTO project (project_id, name, from_date, to_date) VALUES(200, ?, ?, ?)";
	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	jdbcTemplate.update(sqlInsertProject, TESTING_PROJECT, from_date, to_date);
	dao = new JDBCProjectDAO(dataSource);
	daoEmployee = new JDBCEmployeeDAO(dataSource);
	
	
   }
		

	
	@After
    public void rollback() throws SQLException {
	
	dataSource.getConnection().rollback(); 
	
	}
	@Test
	
	public void test_all_active_Projects() {
		
		List<Project> results = dao.getAllActiveProjects();
		Assert.assertNotNull(results);
		Assert.assertEquals(7, results.size());
		
		
	}
	
	@Test 
	
	public void testAddAndRemoveEmployeeFromProject() {
				
		Long employee_id = daoEmployee.getAllEmployees().get(7).getId();
		Long project_id = dao.getAllActiveProjects().get(0).getId();
		int numBeforeAddingEmployee = daoEmployee.getEmployeesByProjectId(project_id).size();
		
		dao.addEmployeeToProject (project_id, employee_id);
		Assert.assertEquals(numBeforeAddingEmployee + 1, daoEmployee.getEmployeesByProjectId(project_id).size());
		dao.removeEmployeeFromProject(project_id, employee_id);
		Assert.assertEquals(numBeforeAddingEmployee, daoEmployee.getEmployeesByProjectId(project_id).size());
	}
	
 }
