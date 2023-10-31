
package com.egg.biblioteca2.repositorios;

import com.egg.biblioteca2.entidades.Autor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AutorRepositorio extends JpaRepository<Autor, String>{
    
}
