package com.example.demo.dao;

import com.example.demo.model.Desejo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DesejoRepository extends JpaRepository<Desejo, Integer> {
    // métodos customizados se precisar
    List<Desejo> findByClienteId(Integer clienteId);

    Optional<Desejo> findByClienteIdAndProdutoId(Integer clienteId, Integer produtoId);
}