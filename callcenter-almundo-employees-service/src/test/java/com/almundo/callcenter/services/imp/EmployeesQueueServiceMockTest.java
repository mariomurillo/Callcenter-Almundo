package com.almundo.callcenter.services.imp;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.almundo.callcenter.exceptions.CallcenterAlmundoException;
import com.almundo.callcenter.manager.imp.EmployeesQueueManager;
import com.almundo.callcenter.model.Employee;
import com.almundo.callcenter.model.Role;
import com.almundo.callcenter.services.IEmployeesQueueService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * <b>Description:<b> This class represent the mock test for employees queue service
 * 
 * @author <a href="mailto:mardres@gmail.com">Mario Andres Murillo</a>
 * @since 21/04/2018
 */
public class EmployeesQueueServiceMockTest {
    
    /** The employees queue service under test */
    private IEmployeesQueueService employeesQueueService;
    
    /**
     * The method that runs before every test
     */
    @Before
    public void setUp() {
        EmployeesQueueManager.getInstance().clear();
        
        employeesQueueService = new EmployeesQueueService();
    }
    
    /**
     * The test case for create an employee
     */
    @Test
    public void createTest() {
        try {
            employeesQueueService.create(Employee.builder()
                                .name("employee")
                                .role(Role.DIRECTOR)
                                .callsAttended(new ArrayList<>())
                                .build());
                                
            assertEquals(1, EmployeesQueueManager.getInstance().size());
        } catch(CallcenterAlmundoException e) {
            fail();
        }
    }
    
    /**
     * The test case for create an employee with calls attended null
     */
    @Test
    public void createWithCallsAttendedNullTest() {
        try {
            employeesQueueService.create(Employee.builder()
                                .name("employee")
                                .role(Role.DIRECTOR)
                                .callsAttended(null)
                                .build());
                                
            assertEquals(1, EmployeesQueueManager.getInstance().size());
        } catch(CallcenterAlmundoException e) {
            fail();
        }
    }
    
    /**
     * The test case for create an employee null
     */
    @Test(expected = CallcenterAlmundoException.class)
    public void createWithNullTest() throws CallcenterAlmundoException{
        try {
            employeesQueueService.create(null);
        } catch(CallcenterAlmundoException e) {
            throw e;
        }
    }
    
    /**
     * The test case for get an employee
     */
    @Test
    public void getTest() {
        
        EmployeesQueueManager.getInstance()
                    .offer(Employee.builder()
                                .name("employee")
                                .role(Role.DIRECTOR)
                                .build());
                                
        employeesQueueService.get();
        
        assertEquals(0, EmployeesQueueManager.getInstance().size());
    }
    
    /**
     * The test case for get an employee with queue empty
     */
    @Test
    public void getWithQueueEmptyTest() {
        
        employeesQueueService.get();
        
        assertEquals(0, EmployeesQueueManager.getInstance().size());
    }
}
