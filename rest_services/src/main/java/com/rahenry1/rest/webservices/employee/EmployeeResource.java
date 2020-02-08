package com.rahenry1.rest.webservices.employee;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class EmployeeResource {
	
	@Autowired
	private EmployeeDaoService employeeDaoService;
	
	//GET ALL
	@GetMapping("/employees")
	public List<Employee> retrieveAllEmployee(){
		return employeeDaoService.findAll();
	}
	
	//GET
	@GetMapping("/employees/{empId}")
	public EntityModel<Employee> retrieveEmployee(@PathVariable Integer empId) throws EmployeeNotFoundException {
		Employee emp = employeeDaoService.findEmployee(empId);
		if(emp==null)
		{
			throw new EmployeeNotFoundException("empId-"+empId);
		}
		
		//HATEOAS - Hypermedia as the Engine of Application State
		EntityModel<Employee> model = new EntityModel<>(emp);
		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllEmployee());
		model.add(linkTo.withRel("all-users"));
		
		return model;
	}

	//POST
	@PostMapping("/employees")
	public ResponseEntity<Object> createEmployee(@Valid @RequestBody Employee emp) {
		Employee savedEmp = employeeDaoService.save(emp);
		
		URI location = ServletUriComponentsBuilder.
						fromCurrentRequest().
						path("/{empId}").buildAndExpand(savedEmp.getEmpId()).toUri();
	
		return ResponseEntity.created(location).build();
	}
	
	//Delete
	@DeleteMapping("/employees/{empId}")
	public void deleteEmployee(@PathVariable Integer empId) {
		Employee emp = employeeDaoService.deleteEmployee(empId);
		if(emp==null)
		{
			throw new EmployeeNotFoundException("empId-"+empId);
		}
	}
}
