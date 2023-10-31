/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.biblioteca2.controladores;

import com.egg.biblioteca2.entidades.Editorial;
import com.egg.biblioteca2.excepciones.MiException;
import com.egg.biblioteca2.servicios.EditorialServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {

    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/registrar") //localhost:8080/editorial/registrar
    public String registrar() {
        return "editorial_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo) { //El parámetro que llega a este método se llama de la misma manera que el atributo "name" del input de autor_form
        //Con el @RequestParam le indico que ese atributo va a viajar en la URL y que es requerido si o si

        try {
            editorialServicio.crearEditorial(nombre);
            modelo.put("exito", "La editorial fue registrada correctamente");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "editorial_form.html";
        }
        
        return "index.html";
    }
    
    @GetMapping("/listar")
    public String listar(ModelMap modelo){
        
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        
        modelo.addAttribute("editoriales", editoriales);
        
        return "editorial_list.html";
    }
    
     //Entre llaves le indico que variable va a viajar a través de la URL
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
      
        modelo.put("editorial", editorialServicio.getOne(id));
        
        return "editorial_modificar.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo){
        
        try {
            editorialServicio.modificarEditorial(id, nombre);
            return "redirect:../listar";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "editorial_modificar.html";
        }
    }
}
