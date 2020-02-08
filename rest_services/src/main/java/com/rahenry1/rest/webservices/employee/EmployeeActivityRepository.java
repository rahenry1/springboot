package com.rahenry1.rest.webservices.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeActivityRepository extends JpaRepository<EmployeeActivity, Integer>{ 

}
