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
    
   /**
    public Role getRole() {
        return role;
    }
    
    public String getName() {
        return name;
    }
    
    public Employee(Role role, String name) {
        
        this.role = role;
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "Employee(role=" + getRole() + ", name=" + getName() + ") ordinal=" + getRole().ordinal();
    }*/
    
    @Override
    public int compareTo(Employee o) {
        return getRole().ordinal() > o.getRole().ordinal() ? -1 
            : getRole().ordinal() < o.getRole().ordinal() ? 1 : 0; 
    }
}
