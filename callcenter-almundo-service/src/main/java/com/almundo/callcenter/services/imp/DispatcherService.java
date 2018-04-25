package com.almundo.callcenter.services.imp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.almundo.callcenter.model.Call;
import com.almundo.callcenter.process.ProcessAttendCall;
import com.almundo.callcenter.services.IDispatcherService;

/**
 * <b>Description:<b> This class is in charge of dispatch the calls to the employees
 * 
 * @author <a href="mailto:mardres@gmail.com">Mario Andres Murillo</a>
 * @since 21/04/2018
 */
@Service
public class DispatcherService implements IDispatcherService {
    
    /** Amount max calls allowed */
    private int maxCallsAllowed;
    
    /** Path of the call service */
    private String pathCallService;
    
    /** Path of the employee service */
    private String pathEmployeeService;
    
    /** The web client for call service */
    private WebClient webClientCallService;
    
    /** The web client for employee service */
    private WebClient webClientEmployeeService;
    
    /**
     * Controller for Dispatcher
     * 
     * @param maxCallsAllowed Amount max calls allowed
     * @param urlCallService Base url of call service
     * @param urlEmployeeService Base url of employee service
     * @param pathCallService Path of the call service 
     * @param pathEmployeeService Path of the employee service
     */
    public DispatcherService(@Value("${max.calls.allowed}") final Integer maxCallsAllowed, 
                @Value("${url.call.service}") final String urlCallService, 
                @Value("${url.employee.service}") final String urlEmployeeService, 
                @Value("${path.call.service}") final String pathCallService, 
                @Value("${path.employee.service}") final String pathEmployeeService) {
        
        this.pathCallService = pathCallService;
        
        this.pathEmployeeService = pathEmployeeService;
        
        this.maxCallsAllowed = maxCallsAllowed;
        
        this.webClientCallService = WebClient.builder().baseUrl(urlCallService).build();
        
        this.webClientEmployeeService = WebClient.builder().baseUrl(urlEmployeeService).build();
    }
    
    /**
     * @inhericDoc
     */
    @Override
    public void dispatchCall() {
        webClientCallService.get()
                    .uri(pathCallService)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToFlux(Call.class)
                    .buffer(maxCallsAllowed)
                    .subscribe(calls -> {
                        final ExecutorService executorService = Executors
                                                        .newFixedThreadPool(calls.size());
                        calls.forEach(call -> {
                        	executorService.execute(new ProcessAttendCall(
                                        call, 
                                        webClientEmployeeService, 
                                        webClientCallService, 
                                        pathEmployeeService, 
                                        pathCallService));
                        	executorService.shutdown();
                        });
                    });
    }
    
}
