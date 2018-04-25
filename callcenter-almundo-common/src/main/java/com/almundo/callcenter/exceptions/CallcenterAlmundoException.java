package com.almundo.callcenter.exceptions;

/**
 * <b>Description:<b> This class represents an own exception in the application
 * 
 * @author <a href="mailto:mardres@gmail.com">Mario Andres Murillo</a>
 * @since 21/04/2018
 */
public class CallcenterAlmundoException extends Exception {
    
    /** The Constant for serialization. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new call center almundo exception.
	 */
	public CallcenterAlmundoException() {
	    super();
    }
    
	/**
	 * Instantiates a new call center almundo exception.
	 *
	 * @param message the message
	 */
	public CallcenterAlmundoException(final String message) {
		super(message);
	}

	/**
	 * Instantiates a new call center almundo exception.
	 *
	 * @param cause the exception cause
	 */
	public CallcenterAlmundoException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new call center almundo exception.
	 *
	 * @param message the exception message
	 * @param cause the exception cause
	 */
	public CallcenterAlmundoException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
