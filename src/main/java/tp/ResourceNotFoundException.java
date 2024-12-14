/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp;


public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super();
    }
     
    public ResourceNotFoundException(String message) {
        super(message);
    }
      
    public ResourceNotFoundException(long id) {
        super("Resource not found: " + id);
    }
}
