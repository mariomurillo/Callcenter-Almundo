package com.almundo.callcenter.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.almundo.callcenter.exceptions.CallcenterAlmundoException;
import com.almundo.callcenter.model.Call;
import com.almundo.callcenter.service.ICallsQueueService;

/**
 * <b>Description:<b> This class represent the main controller for calls service
 * 
 * @author <a href="mailto:mardres@gmail.com">Mario Andres Murillo</a>
 * @since 21/04/2018
 */
@RestController
public class CallController {
    
    /** The calls queue service */
    private ICallsQueueService callsQueueService;
    
    /**
     * Constructor for call controller
     */
    public CallController(final ICallsQueueService callsQueueService) {
        this.callsQueueService = callsQueueService;
    }
    
    /**
     * This method creates a call in the application
     * 
     * @param call The call to create
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/calls")
    public void create(@RequestBody final Call call) 
                    throws CallcenterAlmundoException {
        callsQueueService.create(call);
    }
    
    /**
     * This method gets an employee in the application 
     */
    @GetMapping("/api/v1/calls")
    public Call get() {
        return callsQueueService.get();
    }
}
