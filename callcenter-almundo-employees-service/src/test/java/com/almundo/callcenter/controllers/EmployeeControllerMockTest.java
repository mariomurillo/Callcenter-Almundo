package com.almundo.callcenter.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Mono;

import com.almundo.callcenter.manager.IEmployeesQueueManager;
import com.almundo.callcenter.manager.imp.EmployeesQueueManager;
import com.almundo.callcenter.model.Employee;
import com.almundo.callcenter.model.Role;

import static org.mockito.Mockito.when;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;


/**
 * <b>Description:<b> This class represent the mock test for employees controller
 * 
 * @author <a href="mailto:mardres@gmail.com">Mario Andres Murillo</a>
 * @since 21/04/2018
 */
public class EmployeeControllerMockTest {
    
    /** The web client for tests */
    private WebTestClient webTestClient;
    
    /** The employee controler under test */
    private EmployeeController controller;
    
    /** The employee queue manager for tests */
    private IEmployeesQueueManager employeesQueueManager;
    
    /**
     * The method that runs before every test
     */
    @Before
    public void setUp() {
        
        employeesQueueManager = EmployeesQueueManager.getInstance();
        employeesQueueManager.clear();
        
        controller = new EmployeeController(employeesQueueManager);
        
        webTestClient = WebTestClient.bindToController(controller).build();
    }
    
    /**
     * The test case for create an employee
     */
    @Test
    public void createEmployeeTest() {
        webTestClient.post()
                .uri("/api/v1/employees")
                .body(fromObject(Employee.builder()
                                .name("employee")
                                .role(Role.SUPERVISOR)
                                .build()))
                .exchange()
                .expectStatus()
                .isCreated();
    }
    
    @Test
    public void getEmployee() {
        webTestClient.get()
                .uri("/api/v1/employees")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Employee.class);
    }
}
