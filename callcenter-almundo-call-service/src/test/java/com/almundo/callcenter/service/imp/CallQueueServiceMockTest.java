package com.almundo.callcenter.service.imp;

import org.junit.Before;
import org.junit.Test;

import com.almundo.callcenter.exceptions.CallcenterAlmundoException;
import com.almundo.callcenter.manager.ICallQueueManager;
import com.almundo.callcenter.manager.imp.CallQueueManager;
import com.almundo.callcenter.model.Call;
import com.almundo.callcenter.service.ICallsQueueService;
import com.almundo.callcenter.service.imp.CallsQueueService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * <b>Description:<b> This class represent the mock test for calls queue service
 * 
 * @author <a href="mailto:mardres@gmail.com">Mario Andres Murillo</a>
 * @since 21/04/2018
 */
public class CallQueueServiceMockTest {
    
    /** The calls queue service under test */
    private ICallsQueueService callsQueueService;
    
    /**
     * The method that runs before every test
     */
    @Before
    public void setUp() {
        CallQueueManager.getInstance().clear();
        
        callsQueueService = new CallsQueueService();
    }
    
    /**
     * The test case for create a call
     */
    @Test
    public void createTest() {
        try {
            callsQueueService.create(Call.builder()
                                .duration(5000l)
                                .build());
                                
            assertEquals(1, CallQueueManager.getInstance().size());
        } catch(CallcenterAlmundoException e) {
            fail();
        }
    }
    
    /**
     * The test case for create a call null
     */
    @Test(expected = CallcenterAlmundoException.class)
    public void createWithNullTest() throws CallcenterAlmundoException{
        try {
            callsQueueService.create(null);
        } catch(CallcenterAlmundoException e) {
            throw e;
        }
    }
    
    /**
     * The test case for get a call
     */
    @Test
    public void getTest() {
        
        CallQueueManager.getInstance()
                    .offer(Call.builder()
                                .duration(5000l)
                                .build());
                                
        callsQueueService.get();
        
        assertEquals(0, CallQueueManager.getInstance().size());
    }
    
    /**
     * The test case for get a call with queue empty
     */
    @Test
    public void getWithQueueEmptyTest() {
        
        callsQueueService.get();
        
        assertEquals(0, CallQueueManager.getInstance().size());
    }
}
