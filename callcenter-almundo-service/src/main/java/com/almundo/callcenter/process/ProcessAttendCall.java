package com.almundo.callcenter.process;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

import java.util.Optional;

import lombok.extern.log4j.Log4j;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.almundo.callcenter.model.Call;
import com.almundo.callcenter.model.Employee;

/**
 * <b>Description:<b> This class is in charge of the process of attending calls
 * 
 * @author <a href="mailto:mardres@gmail.com">Mario Andres Murillo</a>
 * @since 21/04/2018
 */
@Log4j
public class ProcessAttendCall implements Runnable{
    
    /** The call that goes attend */
    private Call call;
    
    /** The web client for employee service */
    private WebClient webClientEmployeeService;
    
    /** The web client for call service */
    private WebClient webClientCallService;
    
    /** Path of the employee service */
    private String pathEmployeeService;
    
    /** Path of the call service */
    private String pathCallService;
    
    /**
     * The constructor for the process for attending a call
     * 
     * @param call The call that goes attend
     * @param webClientEmployeeService The web client for employee service
     * @param webClientCallService The web client for call service
     * @param pathEmployeeService Path of the employee service
     * @param pathCallService Path of the call service
     */
    public ProcessAttendCall(final Call calll, 
            final WebClient webClientEmployeeService, 
            final WebClient webClientCallService,
            final String pathEmployeeService, 
            final String pathCallService) {
        
        this.call = calll;
        this.webClientEmployeeService = webClientEmployeeService;
        this.webClientCallService = webClientCallService;
        this.pathEmployeeService = pathEmployeeService;
        this.pathCallService = pathCallService;
    }
    
    /**
     * @inhericDoc
     */
    @Override
    public void run() {
        
        final Optional<Employee> employee = webClientEmployeeService.get()
                .uri(pathEmployeeService)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Employee.class)
                .blockOptional();
                
        if(employee.isPresent()) {
            
            employee.get().getCallsAttended().add(call);
        
            try {
                Thread.sleep(call.getDuration());
            } catch(final InterruptedException e) {
                log.error(e.getMessage());
            }
            
            webClientEmployeeService.post()
                                .uri(pathEmployeeService)
                                .body(fromObject(employee))
                                .retrieve()
                                .bodyToMono(Void.class);
        } else {
            
            int priority = call.getPriority();
            
            call.setPriority(++priority);
            
            webClientCallService.post()
                                .uri(pathCallService)
                                .body(fromObject(call))
                                .retrieve()
                                .bodyToMono(Void.class);
        }
        
    }
}
