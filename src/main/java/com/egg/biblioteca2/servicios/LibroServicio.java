
package com.egg.biblioteca2.servicios;

import com.egg.biblioteca2.entidades.Autor;
import com.egg.biblioteca2.entidades.Editorial;
import com.egg.biblioteca2.entidades.Libro;
import com.egg.biblioteca2.excepciones.MiException;
import com.egg.biblioteca2.repositorios.AutorRepositorio;
import com.egg.biblioteca2.repositorios.EditorialRepositorio;
import com.egg.biblioteca2.repositorios.LibroRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroServicio {
    
    @Autowired //Inyección de dependencias
    private LibroRepositorio libroRepositorio;
    
    @Autowired
    private AutorRepositorio autorRepositorio;
    
    @Autowired 
    private EditorialRepositorio editorialRepositorio;
    
    @Transactional //Con esta anotación indico que este metodo modifica mi base de datos
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException{
        
        //Al poner las excepciones primero, en caso de lanzarse alguna, no se va a ejecutar el código de abajo ni se va a 
        //persistir el objeto mal creado en la base de datos
        validar(isbn, titulo, idAutor, idEditorial, ejemplares);
        
        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();
        Libro libro = new Libro();
        
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date()); //Le da el alta con la fecha en la que se crea el libro
        
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        
        libroRepositorio.save(libro);
    }
    
    public List<Libro> listarLibros(){
        
        List<Libro> libros = new ArrayList();
        
        libros = libroRepositorio.findAll();
        
        return libros;
    }
    
    public void modificarLibro(Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares) throws MiException{
        
        validar(isbn, titulo, idAutor, idEditorial, ejemplares);
        Optional<Libro> respuesta = libroRepositorio.findById(isbn); //El optional evalua si el valor obtenido es no nulo, de tener un valor devuelve true
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);
        
        Autor autor = new Autor();
        Editorial editorial = new Editorial();
        
        if(respuestaAutor.isPresent()){
            autor = respuestaAutor.get();
        }
        
        if(respuestaEditorial.isPresent()){
            editorial = respuestaEditorial.get();
        }
        
        if (respuesta.isPresent()) {
            
            Libro libro = respuesta.get();
            
            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setEjemplares(ejemplares);
            
            libroRepositorio.save(libro); //Persistimos el libro modificado en la base de datos
            
        }
    }
    
    private void validar(Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares) throws MiException{
           
        if(isbn == null){
            throw new MiException("El ISBN no puede ser nulo");
        }
        if(titulo.isEmpty() || titulo == null){
            throw new MiException("El título no puede estar vacío o ser nulo");
        }
        if(ejemplares == null){
            throw new MiException("Los ejemplares no pueden ser nulos");
        }
        if(idAutor.isEmpty() || idAutor == null){
            throw new MiException("El id del Autor no puede estar vacío o ser nulo");
        }
        if(idEditorial.isEmpty() || idEditorial == null){
            throw new MiException("El id de la Editorial no puede estar vacío o ser nulo");
        }
    }
    
}
