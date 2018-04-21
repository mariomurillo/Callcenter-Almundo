package com.almundo.callcenter.model;


/**
 * This enum represents the differents roles of an employee in the application
 * 
 * @author <a href="mailto:mardres@gmail.com">Mario Andres Murillo</a>
 * @since 21/04/2018
 */
public enum Role {
    
    /** The supervisor role for an employee */
    DIRECTOR("Director"),
    
    /** The supervisor role for an employee */
    SUPERVISOR("Supervisor"),
    
    /** The operator role for an employee */
    OPERATOR("Operador");
    
    /** The role name */
    private String name;
    
    /**
     * The constructor for Role that receive the name
     */
    private Role(String name) {
        this.name = name;
    }
    
    /** 
     * Method that return the name of the role
     * 
     * @return the role name
     */
    public String getName() {
        return name;
    }
}
