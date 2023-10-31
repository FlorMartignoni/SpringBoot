
package com.egg.biblioteca2.servicios;

import com.egg.biblioteca2.entidades.Autor;
import com.egg.biblioteca2.excepciones.MiException;
import com.egg.biblioteca2.repositorios.AutorRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio {
    
    @Autowired
    private AutorRepositorio autorRepositorio;
    
    @Transactional
    public void crearAutor(String nombre) throws MiException{
        
        validar(nombre);
        
        Autor autor = new Autor();
        
        autor.setNombre(nombre);
        
        autorRepositorio.save(autor);
        
    }
    
    public List<Autor> listarAutores(){
        
        List<Autor> autores = new ArrayList();
        
        autores = autorRepositorio.findAll();
        
        return autores;
    }
    
    public void modificarAutor(String nombre, String id) throws MiException{
        
        validar(nombre);
        
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        
        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            
            autor.setNombre(nombre);
            
            autorRepositorio.save(autor); //Persistimos el autor en la base de datos
        }
        
    }
    
    public Autor getOne(String id){
        
        
        return autorRepositorio.getOne(id);
    }
    
    private void validar(String nombre) throws MiException{
        if(nombre.isEmpty() || nombre == null ){
            throw new MiException("El nombre del autor no puede estar vacío o ser nulo");
        }
    }
    
}
