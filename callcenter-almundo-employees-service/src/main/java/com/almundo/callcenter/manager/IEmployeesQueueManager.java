package com.almundo.callcenter.manager;

import com.almundo.callcenter.model.Employee;

/**
 * <b>Description:<b> This interface define the operations to process the employees in the queue 
 * 
 * @author <a href="mailto:mardres@gmail.com">Mario Andres Murillo</a>
 * @since 21/04/2018
 */
public interface IEmployeesQueueManager {
    
    /**
     * This method inserts an employee into the queue if it is possible
     * 
     * @param employee The employee to insert
     */
    boolean offer(Employee employee);
    
    /**
     * This method retrieve and remove an employee of the queue
     */
    Employee poll();
    
    /**
     * This method returns the number of elements in the queue
     */
    int size();
    
    /**
     * This method removes all employees from the queue
     */
    void clear();
}
