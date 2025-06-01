package com.example.demo.dto;

import com.example.demo.model.Produto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class ProdutoDTO {
    private Integer id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer quantidade;
    private String lojaNome;
    private String vendedorNome;

    @JsonProperty("imagem")
    private String imagem_url;


    public ProdutoDTO(){

    }


    // Construtor
    public ProdutoDTO(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.preco = produto.getPreco();
        this.quantidade = produto.getQuantidade();
        this.lojaNome = produto.getLoja() != null ? produto.getLoja().getNome() : null;
        this.vendedorNome = produto.getLoja() != null && produto.getLoja().getVendedor() != null
                ? produto.getLoja().getVendedor().getNome()
                : null;
        this.imagem_url = produto.getImagem_url();
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public String getLojaNome() {
        return lojaNome;
    }

    public String getVendedorNome() {
        return vendedorNome;
    }

    public String getImagem_url() {
        return imagem_url;
    }
}
