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
    
    /** The mock web server for tests */
    private MockWebServer server;
    
    /**
     * The method that runs before every test
     */
    @Before
    public void setUp() {
        
        server = new MockWebServer();
        
        final Call call = Call.builder()
                            .duration(ThreadLocalRandom.current()
                            .nextLong(5000l, 10000l))
                            .build();
                            
        final WebClient webClient = WebClient.builder()
                                        .baseUrl(server.url("/urlEmployee/").toString())
                                        .build();
        
        processAttendCall = new ProcessAttendCall(call, webClient, "pathEmployee");
    }
    
    /**
     * The method that runs after every test
     */
    @After
    public void shutdown() throws IOException{
        server.shutdown();
    }
    
    @Test
    public void runTest() {
        
        prepareResponse(response -> response
				.setHeader("Content-Type", "application/json")
				.setBody("[{\"role\":\"Director\",\"name\":\"Pedro\"},{\"role\":\"Operator\",\"name\":\"Pedro\"}]"));
		
		final ExecutorService executorService =  Executors.newSingleThreadExecutor();
		executorService.execute(processAttendCall);
		executorService.shutdown();
		
    }
    
    /**
     * This method is responsible for preparing the mock server response
     *
     * @param consumer Consume the MockResponse
     */
    private void prepareResponse(final Consumer<MockResponse> consumer) {
		MockResponse response = new MockResponse();
		consumer.accept(response);
		server.enqueue(response);
	}
}
