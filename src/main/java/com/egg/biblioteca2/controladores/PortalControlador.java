
package com.egg.biblioteca2.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/") //Va a entrar al controlador cada vez que aparezca una / en la url
public class PortalControlador {
    
    @GetMapping("/") //Mapea la URL
    public String index(){
        
        return "index.html";
    }
}
