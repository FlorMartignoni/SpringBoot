/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.biblioteca2.controladores;

import com.egg.biblioteca2.entidades.Autor;
import com.egg.biblioteca2.entidades.Editorial;
import com.egg.biblioteca2.entidades.Libro;
import com.egg.biblioteca2.excepciones.MiException;
import com.egg.biblioteca2.servicios.AutorServicio;
import com.egg.biblioteca2.servicios.EditorialServicio;
import com.egg.biblioteca2.servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/registrar") //localhost:8080/libro/registrar
    public String registrar(ModelMap modelo) {
        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();

        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
        return "libro_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) Long isbn, @RequestParam String titulo,
            @RequestParam(required = false) Integer ejemplares, @RequestParam(required = false) String idAutor,
            @RequestParam(required = false) String idEditorial, ModelMap modelo) { //El parámetro que llega a este método se llama de la misma manera que el atributo "name" del input de autor_form
        //Con el @RequestParam le indico que ese atributo va a viajar en la URL y que es requerido si o si

        try {

            libroServicio.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            modelo.put("exito", "El libro se ha cargado correctamente.");

        } catch (MiException ex) {
            List<Autor> autores = autorServicio.listarAutores();
            List<Editorial> editoriales = editorialServicio.listarEditoriales();

            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);
            modelo.put("error", ex.getMessage());
            return "libro_form.html";

        }
        modelo.put("exito", "El libro se ha cargado correctamente.");
        return "index.html";
    }
    
    @GetMapping("/listar")
    public String listar(ModelMap modelo){
        
        List<Libro> libros = libroServicio.listarLibros();
        
        modelo.addAttribute("libros", libros);
        
        return "libro_list.html";
    }
}
