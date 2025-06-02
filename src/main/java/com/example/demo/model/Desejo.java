package com.example.demo.model;

import jakarta.persistence.*;

import java.time.Instant;
//
//@Entity
//public class Desejo {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    private Integer clienteId;
//    private Integer produtoId;
//
//    private Instant dataCriacao = Instant.now();
//
//    // getters & setters
//
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public Integer getProdutoId() {
//        return produtoId;
//    }
//
//    public void setProdutoId(Integer produtoId) {
//        this.produtoId = produtoId;
//    }
//
//    public Integer getClienteId() {
//        return clienteId;
//    }
//
//    public void setClienteId(Integer clienteId) {
//        this.clienteId = clienteId;
//    }
//
//    public Instant getDataCriacao() {
//        return dataCriacao;
//    }
//
//    public void setDataCriacao(Instant dataCriacao) {
//        this.dataCriacao = dataCriacao;
//    }
//}


@Entity
public class Desejo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    private Instant dataCriacao = Instant.now();

    // Getters e setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }

    public Instant getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(Instant dataCriacao) { this.dataCriacao = dataCriacao; }


    private String imagem; // nome ou caminho da imagem

    // getters e setters
    public String getImagem() { return imagem; }
    public void setImagem(String imagem) { this.imagem = imagem; }
}
