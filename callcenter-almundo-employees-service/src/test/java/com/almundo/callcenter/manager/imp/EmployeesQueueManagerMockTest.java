package com.almundo.callcenter.manager.imp;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.almundo.callcenter.manager.IEmployeesQueueManager;
import com.almundo.callcenter.model.Employee;
import com.almundo.callcenter.model.Role;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * <b>Description:<b> This class represent the mock test for employees queue manager
 * 
 * @author <a href="mailto:mardres@gmail.com">Mario Andres Murillo</a>
 * @since 21/04/2018
 */
@FixMethodOrder(MethodSorters.DEFAULT)
public class EmployeesQueueManagerMockTest {
    
    /** The manager under test */
    private IEmployeesQueueManager employeeQueueManager;
    
    /**
     * The method that runs before every test
     */
    @Before
    public void setUp() {
        employeeQueueManager = EmployeesQueueManager.getInstance();
        employeeQueueManager.clear();
    }
    
    /**
     * The test case for inserts an employee into the queue
     */
    @Test
    public void offerTest() {
        assertTrue(employeeQueueManager
                .offer(Employee.builder()
                        .name("employee")
                        .role(Role.OPERATOR)
                        .build()));
        
        assertEquals(1, EmployeesQueueManager.getInstance().size());
    }
    
    /**
     * The test case for inserts an null object into the queue
     */
    @Test(expected = NullPointerException.class)
    public void offerWithNullTest() {
        employeeQueueManager.offer(null);
    }
    
    /**
     * The test case for inserts employees into the queue with multiple threads
     */
    @Test
    public void offerWithMultipleThreadsTest() {
        
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        
        for (int i = 0; i < 10; i++) {
            executorService.execute(new OfferWorker());
        }
    }
    
    /**
     * The test case for retrieve and remove an employee of the queue
     */
    @Test
    public void pollTest() {
        employeeQueueManager
                .offer(Employee.builder()
                        .name("employee")
                        .role(Role.SUPERVISOR)
                        .build());
                
        assertNotNull(employeeQueueManager.poll());
                
        assertEquals(0, employeeQueueManager.size());
    }
    
    /**
     * The test case for retrieve and remove an employee of a queue empty
     */
    @Test
    public void pollWithQueueEmptyTest() {
        assertNull(employeeQueueManager.poll());
    }
    
    /**
     * The test case for retrieve and remove employees of the queue with multiple threads
     */
    @Test
    public void pollrWithMultipleThreadsTest() {
        
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        
        for (int i = 0; i < 10; i++) {
            executorService.execute(new OfferWorker());
        }
        
        for (int i = 0; i < 10; i++) {
            executorService.execute(new OfferWorker());
        }
    }
    
    /**
     * <b>Description:<b> This class represent a worker for offer test with multipple threads
     * @author <a href="mailto:mardres@gmail.com">Mario Andres Murillo</a>
     * @since 21/04/2018
     */
    public static class OfferWorker implements Runnable {
        
        /** The employee for offer test */
        private Employee employee = Employee.builder()
                                    .name("mario")
                                    .role(Role.DIRECTOR)
                                    .build();
        
        /**
         * @inhericDoc
         */
        @Override
        public void run() {
            EmployeesQueueManager.getInstance().offer(employee);
        }
    }
    
    /**
     * <b>Description:<b> This class represent a worker for poll test with multipple threads
     * @author <a href="mailto:mardres@gmail.com">Mario Andres Murillo</a>
     * @since 21/04/2018
     */
    public static class PollWorker implements Runnable {
        
        /**
         * @inhericDoc
         */
        @Override
        public void run() {
            EmployeesQueueManager.getInstance().poll();
        }
    }
}
