package com.example.demo.dao;

import com.example.demo.model.Produto;
import com.example.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

  List<Usuario> findByTipo(String tipo);

  Usuario findByEmail(String email);


}