package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class DesejoDto {
    private Integer id;
    private Integer clienteId;
    private Integer produtoId;
    private String produtoNome;
    private BigDecimal produtoPreco;
    private String lojaNome;
    private Instant dataCriacao;
    private String produtoImagem;

    // Construtor
    public DesejoDto(Integer id, Integer clienteId, Integer produtoId, String produtoNome,
                     BigDecimal produtoPreco, String lojaNome, Instant dataCriacao, String imagem_url) {
        this.id = id;
        this.clienteId = clienteId;
        this.produtoId = produtoId;
        this.produtoNome = produtoNome;
        this.produtoPreco = produtoPreco;
        this.lojaNome = lojaNome;
        this.dataCriacao = dataCriacao;
        this.produtoImagem = imagem_url;
    }

    // Getters públicos
    public Integer getId() {
        return id;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public Integer getProdutoId() {
        return produtoId;
    }

    public String getProdutoNome() {
        return produtoNome;
    }

    public BigDecimal getProdutoPreco() {
        return produtoPreco;
    }

    public String getLojaNome() {
        return lojaNome;
    }

    public Instant getDataCriacao() {
        return dataCriacao;
    }

    public String getProdutoImagem() {
        return produtoImagem;
    }
}
