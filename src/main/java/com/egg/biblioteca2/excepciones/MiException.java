
package com.egg.biblioteca2.excepciones;

public class MiException extends Exception{

    //Esto lo hacemos para poder diferenciar los errores de nuestra logica, de los errores y bugs propios del sistema
    public MiException(String msg) {
        super(msg);
    }
    
    
}
