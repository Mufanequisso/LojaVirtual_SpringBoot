package com.example.demo.dao;

import com.example.demo.model.Loja;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LojaRepository extends JpaRepository<Loja, Integer> {
  Loja findByVendedorId(Integer vendedorId);
  }