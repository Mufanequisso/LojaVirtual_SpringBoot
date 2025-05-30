package com.example.demo.dao;

import com.example.demo.model.Cliente;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

  Optional<Cliente> findByEmailAndSenha(String email, String senha);

  // Este método já existe por padrão, mas pode deixar explícito se quiser:
  Optional<Cliente> findById(Integer id);


  void deleteById(Integer id);

}