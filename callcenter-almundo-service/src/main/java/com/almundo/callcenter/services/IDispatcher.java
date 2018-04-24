package com.almundo.callcenter.services;

/**
 * <b>Description:<b> This interface defines the operations to dispatch the calls to the employees in the service 
 * 
 * @author <a href="mailto:mardres@gmail.com">Mario Andres Murillo</a>
 * @since 21/04/2018
 */
public interface IDispatcher {
    
    /**
     * This method sends calls to available employees according to their priority
     */
    void dispatchCall();
}
