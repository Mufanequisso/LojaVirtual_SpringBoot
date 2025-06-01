package com.example.demo.service;

import com.example.demo.dao.LojaRepository;
import com.example.demo.model.Cliente;
import com.example.demo.model.Loja;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LojaService {

    @Autowired
    private LojaRepository lojaRepository;


    public List<Loja> getLojasByVendedorId(Integer vendedorId) {
        return (List<Loja>) lojaRepository.findByVendedorId(vendedorId);
    }
    public List<Loja> listarTodos() {
        return lojaRepository.findAll();
    }

}
