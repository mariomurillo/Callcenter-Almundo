package com.almundo.callcenter.controllers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;

import com.almundo.callcenter.manager.imp.CallQueueManager;
import com.almundo.callcenter.model.Call;
import com.almundo.callcenter.service.ICallsQueueService;
import com.almundo.callcenter.service.imp.CallsQueueService;

import static org.mockito.Mockito.when;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

/**
 * <b>Description:<b> This class represent the mock test for calls controller
 * 
 * @author <a href="mailto:mardres@gmail.com">Mario Andres Murillo</a>
 * @since 21/04/2018
 */
public class CallControllerMockTest {
    
    /** The web client for tests */
    private static WebTestClient webTestClient;
    
    /** The call controler under test */
    private CallController controller;
    
    /** The call queue service for tests */
    private ICallsQueueService callsQueueService;
    
    /**
     * The method that runs before every test
     */
    @Before
    public void setUp() {
        
        CallQueueManager.getInstance().clear();
        
        callsQueueService = Mockito.mock(CallsQueueService.class);
        
        controller = new CallController(callsQueueService);
        
        webTestClient = WebTestClient.bindToController(controller).build();
    }
    
    /**
     * The test case for create a call
     */
    @Test
    public void createCallTest() {
        webTestClient.post()
                .uri("/api/v1/calls")
                .body(fromObject(Call.builder()
                                .duration(5000l)
                                .build()))
                .exchange()
                .expectStatus()
                .isCreated();
    }
    
    /** The test case for get calls */
    @Test
    public void getCalls() {
        
        when(callsQueueService.getCalls()).thenReturn(new LinkedBlockingQueue<>());
        
        webTestClient.get()
                .uri("/api/v1/calls")
                .exchange()
                .expectStatus()
                .isOk();
    }
}
