package com.almundo.callcenter.service;

import java.util.Queue;

import com.almundo.callcenter.exceptions.CallcenterAlmundoException;
import com.almundo.callcenter.model.Call;

/**
 * <b>Description:<b> This interface define the operations to process the calls in the service 
 * 
 * @author <a href="mailto:mardres@gmail.com">Mario Andres Murillo</a>
 * @since 21/04/2018
 */
public interface ICallsQueueService {
    
    /**
     * This method create a call into the application if it is possible
     * 
     * @param call The call to insert
     */
    void create(Call call) throws CallcenterAlmundoException;
    
    /**
     * This method retrieve and remove a call of the application
     */
    Call get();
    
    /**
     * This method returns the queue of calls
     */
    public Queue<Call> getCalls();
}
