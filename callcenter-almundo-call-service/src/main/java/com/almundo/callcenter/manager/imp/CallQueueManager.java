package com.almundo.callcenter.manager.imp;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.almundo.callcenter.manager.ICallQueueManager;
import com.almundo.callcenter.model.Call;

import lombok.extern.log4j.Log4j;

/**
 * <b>Description:<b> This class represent the call queue manager for application
 * 
 * @author <a href="mailto:mardres@gmail.com">Mario Andres Murillo</a>
 * @since 21/04/2018
 */
@Log4j
public class CallQueueManager implements ICallQueueManager {
    
    /** The calls queue */
    private static final Queue<Call> calls = new PriorityQueue<>();
    
    /**
     * The constructor method by default
     */
    private CallQueueManager() {
    }
    
    /**
     * The method that returns an instance of call queue manager
     */
    public static CallQueueManager getInstance() {
        return Impl.INSTANCE;
    }
    
    /**
     * @inhericDoc
     */
    @Override
    public boolean offer(final Call call) {
        
        log.debug("CallQueueManager::offer [" + call + "]");
        
        call.setPriority(BigInteger.ONE.intValue());
        
        return calls.offer(call);
    }
    
    /**
     * @inhericDoc
     */
    @Override
    public Call poll() {
        
        log.debug("CallQueueManager::poll");
        
        return calls.poll();
    }
    
    /**
     * @inhericDoc
     */
    @Override
    public Queue<Call> getCalls() {
        return calls;
    }
    
    /**
     * @inhericDoc
     */
    @Override
    public int size() {
        
        log.debug("CallQueueManager::size");
        
        return calls.size();
    }
    
    /**
     * @inhericDoc
     */
    public void clear() {
        
        log.debug("CallQueueManager::clear");
        
        calls.clear();
    }
    
    /**
     * <b>Description:<b> This inner class is used for double checked locking
     * 
     * @author <a href="mailto:mardres@gmail.com">Mario Andres Murillo</a>
     * @since 21/04/2018
     */
    private static class Impl {
        
        /** The instance for call queue manager */
        private static final CallQueueManager INSTANCE = new CallQueueManager();
    }
}
