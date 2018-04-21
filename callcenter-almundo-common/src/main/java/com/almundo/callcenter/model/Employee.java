package com.almundo.callcenter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represent an employee in the application
 * 
 * @author <a href="mailto:mardres@gmail.com">Mario Andres Murillo</a>
 * @since 21/04/2018
 */
@AllArgsConstructor
@Data
@SuppressWarnings(value = "unused")
public class Employee implements Comparable<Employee>{
    
    /** The role of an employee */
    private Role role;
    
    /** The employee name */
    private String name;
    
    
    @Override
    public int compareTo(Employee o) {
        return getRole().ordinal() > o.getRole().ordinal() ? -1 
            : getRole().ordinal() < o.getRole().ordinal() ? 1 : 0; 
    }
}
