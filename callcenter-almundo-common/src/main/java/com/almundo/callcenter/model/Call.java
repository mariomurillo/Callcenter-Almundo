package com.almundo.callcenter.model;

import java.util.concurrent.TimeUnit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * <b>Description:<b> This class represent a call in the application
 * 
 * @author <a href="mailto:mardres@gmail.com">Mario Andres Murillo</a>
 * @since 21/04/2018
 */
@Data
@Builder
@SuppressWarnings(value = "unused")
public class Call {
    
    /** The call duration */
    private Long duration;
}
