package com.almundo.callcenter.service.imp;

import java.util.Queue;

import lombok.extern.log4j.Log4j;

import org.springframework.stereotype.Service;

import com.almundo.callcenter.exceptions.CallcenterAlmundoException;
import com.almundo.callcenter.manager.ICallQueueManager;
import com.almundo.callcenter.manager.imp.CallQueueManager;
import com.almundo.callcenter.model.Call;
import com.almundo.callcenter.service.ICallsQueueService;

/**
 * <b>Description:<b> This class represent the calls service for application
 * 
 * @author <a href="mailto:mardres@gmail.com">Mario Andres Murillo</a>
 * @since 21/04/2018
 */
@Service
@Log4j
public class CallsQueueService implements ICallsQueueService{
    
    /** The call queue manager */
    private ICallQueueManager callQueueManager = CallQueueManager.getInstance();
    
    /**
     * @inhericDoc
     */
    @Override
    public void create(Call call) throws CallcenterAlmundoException {
        
        log.debug("CallsQueueService::create [" + call + "]");
        
        try{
            callQueueManager.offer(call);
        } catch(NullPointerException e) {
            log.error("CallsQueueService::create Error [{}]", e.getCause());
            
            throw new CallcenterAlmundoException(e.getMessage(), e.getCause());
        }
    }
    
    /**
     * @inhericDoc
     */
    @Override
    public Call get() {
        
        log.debug("CallsQueueService::get");
        
        return callQueueManager.poll();
    }
    
    /**
     * @inhericDoc
     */
    @Override
    public Queue<Call> getCalls() {
        return callQueueManager.getCalls();
    }
}
