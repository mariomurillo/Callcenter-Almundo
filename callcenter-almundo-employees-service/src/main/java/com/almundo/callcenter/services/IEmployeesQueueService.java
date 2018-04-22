package com.almundo.callcenter.services;

import com.almundo.callcenter.exceptions.CallcenterAlmundoException;
import com.almundo.callcenter.model.Employee;

/**
 * <b>Description:<b> This interface define the operations to process the employees in the service 
 * 
 * @author <a href="mailto:mardres@gmail.com">Mario Andres Murillo</a>
 * @since 21/04/2018
 */
public interface IEmployeesQueueService {
    
    /**
     * This method create an employee into the application if it is possible
     * 
     * @param employee The employee to insert
     */
    void create(Employee employee) throws CallcenterAlmundoException;
    
    /**
     * This method retrieve and remove an employee of the application
     */
    Employee get();
}
