package com.almundo.callcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The call center application entry point
 * 
 * @author <a href="mailto:mardres@gmail.com">Mario Andres Murillo</a>
 * @since 21/04/2018
 */
@SpringBootApplication
public class CallcenterAlmundoApplication {
    
    /**
     * The main method
     * 
     * @param args The arguments
     */
    public static void main(String[] args) {
		SpringApplication.run(CallcenterAlmundoApplication.class, args);
	}
}
