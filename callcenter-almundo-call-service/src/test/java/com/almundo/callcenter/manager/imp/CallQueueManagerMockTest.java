package com.almundo.callcenter.manager.imp;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.almundo.callcenter.manager.ICallQueueManager;
import com.almundo.callcenter.model.Call;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * <b>Description:<b> This class represent the mock test for call queue manager
 * 
 * @author <a href="mailto:mardres@gmail.com">Mario Andres Murillo</a>
 * @since 21/04/2018
 */
@FixMethodOrder(MethodSorters.DEFAULT)
public class CallQueueManagerMockTest {
    
    /** The manager under test */
    private ICallQueueManager callQueueManager;
    
    /**
     * The method that runs before every test
     */
    @Before
    public void setUp() {
        callQueueManager = CallQueueManager.getInstance();
        callQueueManager.clear();
    }
    
    /**
     * The test case for inserts a call into the queue
     */
    @Test
    public void offerTest() {
        assertTrue(callQueueManager
                .offer(Call.builder()
                        .duration(5000l)
                        .build()));
        
        assertEquals(1, CallQueueManager.getInstance().size());
    }
    
    /**
     * The test case for inserts an null object into the queue
     */
    @Test(expected = NullPointerException.class)
    public void offerWithNullTest() {
        callQueueManager.offer(null);
    }
    
    /**
     * The test case for inserts calls into the queue with multiple threads
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
        callQueueManager
                .offer(Call.builder()
                        .duration(5000l)
                        .build());
                
        assertNotNull(callQueueManager.poll());
                
        assertEquals(0, callQueueManager.size());
    }
    
    /**
     * The test case for retrieve and remove a call of a queue empty
     */
    @Test
    public void pollWithQueueEmptyTest() {
        assertNull(callQueueManager.poll());
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
        
        /** The call for offer test */
        private Call call = Call.builder()
                                .duration(5000l)
                                .build();
        
        /**
         * @inhericDoc
         */
        @Override
        public void run() {
            CallQueueManager.getInstance().offer(call);
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
            CallQueueManager.getInstance().poll();
        }
    }
}
