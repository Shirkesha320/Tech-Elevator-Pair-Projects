package com.techelevator.projects.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Department;
import com.techelevator.projects.model.DepartmentDAO;
import com.techelevator.projects.model.Employee;

public class JDBCDepartmentDAO implements DepartmentDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCDepartmentDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Department> getAllDepartments() {
		List<Department> deptList = new ArrayList<>();
		String query = "SELECT department_id, name FROM department";
		
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query);
		
		while (rowSet.next()) {
			Department newDept = getDepartmentFromRowSet(rowSet);
			deptList.add(newDept);
		}
		return deptList ;
	}

	@Override
	public List<Department> searchDepartmentsByName(String nameSearch) {
		List<Department> deptList = new ArrayList<>();
				
		String query = "SELECT department_id, name FROM department WHERE name = ?";
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query, nameSearch);
		 
		while(rowSet.next()) {
			Department dept = getDepartmentFromRowSet(rowSet);
			deptList.add(dept);
		}
		return deptList;
	}

	@Override
	public void saveDepartment(Department updatedDepartment) {
		
		String sqlInsertDepartment = "UPDATE department SET name = ? WHERE department_id = ?";
		jdbcTemplate.update(sqlInsertDepartment, updatedDepartment.getName(),
				updatedDepartment.getId());
		
	}

	@Override
	public Department createDepartment(Department newDepartment) {
		 String query = "INSERT INTO department (name) VALUES(?)";
		 jdbcTemplate.update(query, newDepartment.getName());
		 newDepartment.setId(getDepartmentId(newDepartment.getName()));
		return newDepartment;
	}

	@Override
	public Department getDepartmentById(Long Id) {
		
		Department newDept = new Department();
				
		String query = "SELECT department_id, name FROM department WHERE department_id = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(query, Id);
		while (results.next()) {
			
			
			
		}
		return newDept;
	}

	 private Department getDepartmentFromRowSet(SqlRowSet rowSet){
		
	Department dept = new Department();
	dept.setId(rowSet.getLong("department_id"));
	dept.setName(rowSet.getString("name"));
	return dept;

}
	 private Long getDepartmentId(String name) {
			// query the department id by name
			String query = "SELECT department_id FROM department WHERE name=?";
			
			// id should be unique so we can use queryForObject
			Long resultId = jdbcTemplate.queryForObject(query, Long.class, name);
			return resultId;
	 }
}