package com.techelevator.projects.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Employee;
import com.techelevator.projects.model.Project;
import com.techelevator.projects.model.ProjectDAO;

public class JDBCProjectDAO implements ProjectDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCProjectDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Project> getAllActiveProjects() {
		
	
		List<Project> projects = new ArrayList<>();
		String query = "SELECT project_id, name, from_date, to_date FROM project";
		SqlRowSet results = jdbcTemplate.queryForRowSet(query);
		
		while(results.next()) {
			Project theProjects = mapRowToProject(results);
			projects.add(theProjects);
	
	}

		return  projects;
}
	@Override
	public void removeEmployeeFromProject(Long projectId, Long employeeId) {
		
	String sqlRemoveEmployeeFromProject = "DELETE FROM project_employee WHERE employee_id = ? AND project_id = ?";
	jdbcTemplate.update(sqlRemoveEmployeeFromProject, employeeId, projectId);
	
	}

	@Override
	public void addEmployeeToProject(Long projectId, Long employeeId) {
		
		String query = "INSERT INTO project_employee( employee_id, project_id) VALUES (?, ?)";
				
			
		
		jdbcTemplate.update(query,employeeId, projectId);
		
	}
	private Project mapRowToProject (SqlRowSet results) {
	 
		 Project theProject;
		 
		 theProject = new Project();
		 theProject.setId(results.getLong("project_id"));
		 theProject.setName(results.getString("name"));
		 
		 if (results.getDate("from_date") != null) {
	     theProject.setStartDate(results.getDate("from_date").toLocalDate());
		 }
		
		 if (results.getDate("to_date") != null) {
		 theProject.setEndDate(results.getDate("to_date").toLocalDate());
		 }
		 return theProject;
		 
	 } 
}

