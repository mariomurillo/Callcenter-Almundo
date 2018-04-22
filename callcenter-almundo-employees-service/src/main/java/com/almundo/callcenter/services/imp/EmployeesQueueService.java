package com.almundo.callcenter.services.imp;

import lombok.extern.log4j.Log4j;

import org.springframework.stereotype.Service;

import com.almundo.callcenter.exceptions.CallcenterAlmundoException;
import com.almundo.callcenter.manager.IEmployeesQueueManager;
import com.almundo.callcenter.manager.imp.EmployeesQueueManager;
import com.almundo.callcenter.model.Employee;
import com.almundo.callcenter.services.IEmployeesQueueService;

/**
 * <b>Description:<b> This class represent the employees service for application
 * 
 * @author <a href="mailto:mardres@gmail.com">Mario Andres Murillo</a>
 * @since 21/04/2018
 */
@Service
@Log4j
public class EmployeesQueueService implements IEmployeesQueueService {
    
    private IEmployeesQueueManager employeesQueueManager = EmployeesQueueManager.getInstance();
    
    /**
     * @inhericDoc
     */
    @Override
    public void create(Employee employee) throws CallcenterAlmundoException {
        
        log.debug("EmployeesQueueService::create [" + employee.toString() + "]");
        
        try{
            employeesQueueManager.offer(employee);
        } catch(NullPointerException e) {
            
            log.error("EmployeesQueueService::create Error [{}]", e.getCause());
            
            throw new CallcenterAlmundoException(e.getMessage(), e.getCause());
        }
    }
    
    /**
     * @inhericDoc
     */
    @Override
    public Employee get() {
        
        log.debug("EmployeesQueueService::get");
        
        return employeesQueueManager.poll();
    }
}
