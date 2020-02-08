package com.rahenry1.rest.webservices.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class EmployeeDaoService {

	private static List<Employee> employees = new ArrayList<>();
	private static int employeeCount = 3;
	
	static {
		employees.add(new Employee(1, "Apple", new Date()));
		employees.add(new Employee(2, "Ball", new Date()));
		employees.add(new Employee(3, "Cat", new Date()));
	}
	
	//get all
	public List<Employee> findAll(){
		return employees;
	}
	
	//create
	public Employee save(Employee emp) {
		if(emp.getEmpId()==null) {
			emp.setEmpId(++employeeCount);
		}
		employees.add(emp);
		return emp;
	}
	
	//Get
	public Employee findEmployee(int empId) {
		for(Employee employee:employees) {
			if(employee.getEmpId()==empId) {
				return employee;
			}
		}
		return null;
	}
	
	//Delete
	public Employee deleteEmployee(int empId) {
		Iterator<Employee> iterator = employees.iterator();
		while(iterator.hasNext()) {
			Employee emp = iterator.next();
			if(emp.getEmpId()==empId) {
				iterator.remove();
				return emp;
			}
		}
		return null;
	}
	
}
