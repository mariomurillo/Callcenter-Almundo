package com.almundo.callcenter.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.almundo.callcenter.exceptions.CallcenterAlmundoException;
import com.almundo.callcenter.model.Employee;
import com.almundo.callcenter.services.IEmployeesQueueService;

/**
 * <b>Description:<b> This class represent the main controller for employees service
 * 
 * @author <a href="mailto:mardres@gmail.com">Mario Andres Murillo</a>
 * @since 21/04/2018
 */
@RestController
public class EmployeeController {
    
    /** The employees queue service */
    private IEmployeesQueueService employeesQueueService;
    
    /**
     * Constructor for employee controller
     */
    public EmployeeController(final IEmployeesQueueService employeesQueueService) {
        this.employeesQueueService = employeesQueueService;
    }
    
    /**
     * This method creates an employee in the application
     * 
     * @param employee The employee to create
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/employees")
    public void create(@RequestBody final Employee employee) 
                    throws CallcenterAlmundoException {
        employeesQueueService.create(employee);
    }
    
    /**
     * This method gets an employee in the application 
     */
    @GetMapping("/api/v1/employees")
    public Employee get() {
        return employeesQueueService.get();
    }
    
}
