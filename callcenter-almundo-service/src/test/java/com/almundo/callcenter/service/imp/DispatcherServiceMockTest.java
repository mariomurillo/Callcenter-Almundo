package com.almundo.callcenter.service.imp;

import java.io.IOException;

import java.util.function.Consumer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.almundo.callcenter.services.imp.DispatcherService;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

/**
 * <b>Description:<b> This class represent the mock test for dispatch
 * 
 * @author <a href="mailto:mardres@gmail.com">Mario Andres Murillo</a>
 * @since 21/04/2018
 */
public class DispatcherServiceMockTest {
    
    /** The service under test */
    private DispatcherService dispatcher;
    
    /** The mock web server for tests */
    private MockWebServer server;
    
    /**
     * The method that runs before every test
     */
    @Before
    public void setUp() {
        
        server = new MockWebServer();
        server.url("/urlCall/");
        
        dispatcher = new DispatcherService(1, 
        		"http://" + server.getHostName() + ":" + server.getPort() + "/urlCall", 
        		"http://" + server.getHostName() + ":" + server.getPort() + "/urlEmployee/", 
        		"pathCall", 
        		"pathEmployee");
    }
    
    /**
     * The method that runs after every test
     */
    @After
    public void shutdown() throws IOException{
        server.shutdown();
    }
    
    /**
     * The test case for dispatchCall
     */
    @Test
    public void dispatchCallTest() {
        
        prepareResponse(response -> response
				.setHeader("Content-Type", "application/json")
				.setBody("[{\"duration\":\"5000\"},{\"duration\":\"7000\"}]"));
        
        dispatcher.dispatchCall();
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
