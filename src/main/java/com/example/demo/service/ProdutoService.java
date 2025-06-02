package com.example.demo.service;

import com.example.demo.dao.ProdutoRepository;
import com.example.demo.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto salvar(Produto produto) {
        produto.setCriadoEm(Instant.now());
        produto.setAtualizadoEm(Instant.now());
        return produtoRepository.save(produto);
    }

    public Produto atualizar(Produto produto) {
        produto.setAtualizadoEm(Instant.now());
        return produtoRepository.save(produto);
    }

    public void remover(Integer id) {
        produtoRepository.deleteById(id);
    }

    public Optional<Produto> buscarPorId(Integer id) {
        return produtoRepository.findById(id);
    }


    public List<Produto> listarTodos() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtos != null ? produtos : new ArrayList<>();

    }



    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Optional<Produto> buscarPorLojaId(Integer lojaId) {
        return produtoRepository.findById(lojaId);
    }



}
