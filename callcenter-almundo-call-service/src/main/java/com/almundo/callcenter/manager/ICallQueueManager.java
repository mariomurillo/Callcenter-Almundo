package com.almundo.callcenter.manager;

import com.almundo.callcenter.model.Call;

/**
 * <b>Description:<b> This interface define the operations to process the call in the queue 
 * 
 * @author <a href="mailto:mardres@gmail.com">Mario Andres Murillo</a>
 * @since 21/04/2018
 */
public interface ICallQueueManager {
    
    /**
     * This method inserts a call into the queue if it is possible
     * 
     * @param call The call to insert
     */
    boolean offer(Call call);
    
    /**
     * This method retrieve and remove a call of the queue
     */
    Call poll();
    
    /**
     * This method returns the number of elements in the queue
     */
    int size();
    
    /**
     * This method removes all call from the queue
     */
    void clear();
}
