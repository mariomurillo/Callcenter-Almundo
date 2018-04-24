package com.almundo.callcenter.process;

import lombok.extern.log4j.Log4j;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.almundo.callcenter.model.Call;
import com.almundo.callcenter.model.Employee;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

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
    
    /** Path of the employee service */
    private String pathEmployeeService;
    
    /**
     * The constructor for the process for attending a call
     * 
     * @param call The call that goes attend
     * @param webClientEmployeeService The web client for employee service
     * @param pathEmployeeService Path of the employee service
     */
    public ProcessAttendCall(final Call calll, 
            final WebClient webClientEmployeeService, 
            final String pathEmployeeService) {
        
        this.call = calll;
        this.webClientEmployeeService = webClientEmployeeService;
        this.pathEmployeeService = pathEmployeeService;
    }
    
    /**
     * @inhericDoc
     */
    @Override
    public void run() {
        
        final Employee employee = webClientEmployeeService.get()
                .uri(pathEmployeeService)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Employee.class)
                .block();
                
        employee.getCallsAttended().add(call);
        
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
    }
}
