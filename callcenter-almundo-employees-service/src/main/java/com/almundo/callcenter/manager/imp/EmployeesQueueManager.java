package com.almundo.callcenter.manager.imp;

import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

import lombok.extern.log4j.Log4j;

import com.almundo.callcenter.manager.IEmployeesQueueManager;
import com.almundo.callcenter.model.Employee;

/**
 * <b>Description:<b> This class represent the employees queue manager for application
 * 
 * @author <a href="mailto:mardres@gmail.com">Mario Andres Murillo</a>
 * @since 21/04/2018
 */
@Log4j
public class EmployeesQueueManager implements IEmployeesQueueManager{
    
    /** The employees queue */
    private static final Queue<Employee> employees = new PriorityBlockingQueue<>();
    
    /**
     * The constructor method by default
     */
    private EmployeesQueueManager() {
    }
    
    /**
     * The method that returns an instance of employees queue manager
     */
    public static EmployeesQueueManager getInstance() {
        return Impl.INSTANCE;
    }
    
    /**
     * @inhericDoc
     */
    public boolean offer(final Employee employee) {
        
        log.debug("EmployeesQueueManager::offer [" + employee.toString() + "]");
        
        return employees.offer(employee);
    }
    
    /**
     * @inhericDoc
     */
    public Employee poll() {
        
        log.debug("EmployeesQueueManager::poll");
        
        return employees.poll();
    }
    
    /**
     * @inhericDoc
     */
    public int size() {
        
        log.debug("EmployeesQueueManager::size");
        
        return employees.size();
    }
    
    /**
     * @inhericDoc
     */
    public void clear() {
        
        log.debug("EmployeesQueueManager::clear");
        
        employees.clear();
    }
    
    /**
     * <b>Description:<b> This inner class is used for double checked locking
     * 
     * @author <a href="mailto:mardres@gmail.com">Mario Andres Murillo</a>
     * @since 21/04/2018
     */
    private static class Impl {
        
        /** The instance for employees queue manager */
        private static final EmployeesQueueManager INSTANCE = new EmployeesQueueManager();
    }
}
