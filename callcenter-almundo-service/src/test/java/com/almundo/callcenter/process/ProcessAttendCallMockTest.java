package com.almundo.callcenter.process;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.reactive.function.client.WebClient;

import com.almundo.callcenter.model.Call;
import com.almundo.callcenter.model.Employee;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

/**
 * <b>Description:<b> This class represent the mock test for process attend of call
 * 
 * @author <a href="mailto:mardres@gmail.com">Mario Andres Murillo</a>
 * @since 21/04/2018
 */
public class ProcessAttendCallMockTest {
    
    /** The process of attend call under test */
    private ProcessAttendCall processAttendCall;
    
    /** The mock web server of employee service for tests */
    private MockWebServer serverEmployeeService;
    
    /** The mock web server of call service for tests */
    private MockWebServer serverCallService;
    
    /**
     * The method that runs before every test
     */
    @Before
    public void setUp() {
        
        serverEmployeeService = new MockWebServer();
        
        serverCallService = new MockWebServer();
        
        final Call call = Call.builder()
                            .duration(ThreadLocalRandom.current()
                                            .nextLong(5000l, 10000l))
                            .priority(ThreadLocalRandom.current()
                                            .nextInt(1, 2))
                            .build();
                            
        final WebClient webEmployeeClient = WebClient.builder()
                                        .baseUrl(serverEmployeeService.url("/urlEmployee/").toString())
                                        .build();
        
        final WebClient webCallClient = WebClient.builder()
                                        .baseUrl(serverCallService.url("/urlCall/").toString())
                                        .build();
        
        processAttendCall = new ProcessAttendCall(call, 
                                    webEmployeeClient, 
                                    webCallClient, 
                                    "pathEmployee", 
                                    "pathCall");
    }
    
    /**
     * The method that runs after every test
     */
    @After
    public void shutdown() throws IOException{
        serverEmployeeService.shutdown();
    }
    
    /**
     * The test case for process of attending a call
     */
    @Test
    public void runTest() {
        
        prepareEmployeeResponse(response -> response
				.setHeader("Content-Type", "application/json")
				.setBody("{\"role\":\"Director\",\"name\":\"Pedro\"}"));
		
		final ExecutorService executorService =  Executors.newSingleThreadExecutor();
		executorService.execute(processAttendCall);
		executorService.shutdown();
		
    }
    
    /**
     * The test case for process of attending a call when not found an employee available
     */
    @Test
    public void runWhenNotFoundEmployeeTest() {
        prepareEmployeeResponse(response -> response
                .setResponseCode(404)
				.setHeader("Content-Type", "application/json")
				.setBody("Not Found"));
		
		final ExecutorService executorService =  Executors.newSingleThreadExecutor();
		executorService.execute(processAttendCall);
		executorService.shutdown();
		
    }
    
    /**
     * This method is responsible for preparing the mock employee server response
     *
     * @param consumer Consume the MockResponse
     */
    private void prepareEmployeeResponse(final Consumer<MockResponse> consumer) {
		MockResponse response = new MockResponse();
		consumer.accept(response);
		serverEmployeeService.enqueue(response);
	}
}
