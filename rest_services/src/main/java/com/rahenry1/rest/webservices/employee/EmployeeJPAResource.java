package com.rahenry1.rest.webservices.employee;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
public class EmployeeJPAResource {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private EmployeeActivityRepository employeeActivityRepo;
	
	//GET ALL
	@GetMapping("/jpa/employees")
	public List<Employee> retrieveAllEmployee(){
		return employeeRepository.findAll();
	}
	
	//GET
	@GetMapping("/jpa/employees/{empId}")
	public EntityModel<Employee> retrieveEmployee(@PathVariable Integer empId) throws EmployeeNotFoundException {
		Optional<Employee> emp = employeeRepository.findById(empId);
		if(!emp.isPresent())
		{
			throw new EmployeeNotFoundException("empId-"+empId);
		}
		
		//HATEOAS - Hypermedia as the Engine of Application State
		EntityModel<Employee> model = new EntityModel<>(emp.get());
		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllEmployee());
		model.add(linkTo.withRel("all-users"));
		
		return model;
	}

	//POST
	@PostMapping("/jpa/employees")
	public ResponseEntity<Object> createEmployee(@Valid @RequestBody Employee emp) {
		Employee savedEmp = employeeRepository.save(emp);
		
		URI location = ServletUriComponentsBuilder.
						fromCurrentRequest().
						path("/{empId}").buildAndExpand(savedEmp.getEmpId()).toUri();
	
		return ResponseEntity.created(location).build();
	}
	
	//Delete
	@DeleteMapping("/jpa/employees/{empId}")
	public void deleteEmployee(@PathVariable Integer empId) {
		employeeRepository.deleteById(empId);
	}
	
	
	//GET ALL Activities table
	@GetMapping("/jpa/employees/{empId}/activities")
	public List<EmployeeActivity> retrieveAllEmployeeActivities(@PathVariable int empId){
		Optional<Employee> emp = employeeRepository.findById(empId);
		
		if(!emp.isPresent()) {
			throw new EmployeeNotFoundException("empId - "+empId);
		}
		
		return emp.get().getEmpActivities(); 
	}
	
	//POST
	@PostMapping("/jpa/employees/{empId}/activities")
	public ResponseEntity<Object> createEmployeeActivity(@PathVariable int empId,
			@RequestBody EmployeeActivity empActivity) {
		Optional<Employee> empOptional = employeeRepository.findById(empId);
		
		if(!empOptional.isPresent()) {
			throw new EmployeeNotFoundException("empId - "+empId);
		}
		
		Employee savedEmp = empOptional.get();
		
		empActivity.setEmployee(savedEmp);
		
		employeeActivityRepo.save(empActivity);
		
		URI location = ServletUriComponentsBuilder.
						fromCurrentRequest().
						path("/{empId}").buildAndExpand(empActivity.getId()).toUri();
	
		return ResponseEntity.created(location).build();
	}
}
