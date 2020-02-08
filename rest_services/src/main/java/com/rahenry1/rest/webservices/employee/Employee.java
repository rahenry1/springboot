package com.rahenry1.rest.webservices.employee;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

@Entity
public class Employee {
	
		@Id
		@GeneratedValue
		private Integer empId;
		
		@Size(min = 2, message = "Name should have atleast 2 chars" )
		private String name;
		
		@Past
		private Date birthdate;
		
		@OneToMany(mappedBy = "employee")
		private List<EmployeeActivity> empActivities;
		
		protected Employee(){
			
		}
		
		@Override
		public String toString() {
			return String.format("Employee [empId=%s, name=%s, birthDate=%s]", empId, name, birthdate);
		}
		
		public Employee(Integer empId, String name, Date birthdate) {
			super();
			this.empId = empId;
			this.name = name;
			this.birthdate = birthdate;
		}
		
		public Integer getEmpId() {
			return empId;
		}
		public void setEmpId(Integer empId) {
			this.empId = empId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Date getBirthdate() {
			return birthdate;
		}
		public void setBirthdate(Date birthdate) {
			this.birthdate = birthdate;
		}

		public List<EmployeeActivity> getEmpActivities() {
			return empActivities;
		}

		public void setEmpActivities(List<EmployeeActivity> empActivities) {
			this.empActivities = empActivities;
		}
		
		
}
