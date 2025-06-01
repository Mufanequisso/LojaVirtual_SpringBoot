package com.example.demo.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

//public class ProdutoFormDto implements Serializable {
//    private Integer id;
//    private String nome;
//    private String descricao;
//    private BigDecimal preco;
//    private Integer quantidade;
//    private Integer lojaId;       // Novo campo para envio via formulário/JSON
//    private LojaDto1 loja;        // Campo opcional para exibição de dados da loja
//    private Instant criadoEm;
//    private Instant atualizadoEm;
//    private String imagem_url;
//
//    public ProdutoFormDto() {
//        // Construtor padrão necessário para Jackson
//    }
//
//    public ProdutoFormDto(Integer id, String nome, String descricao, BigDecimal preco, Integer quantidade,
//                          Integer lojaId, LojaDto1 loja, Instant criadoEm, Instant atualizadoEm, String imagem_url) {
//        this.id = id;
//        this.nome = nome;
//        this.descricao = descricao;
//        this.preco = preco;
//        this.quantidade = quantidade;
//        this.lojaId = lojaId;
//        this.loja = loja;
//        this.criadoEm = criadoEm;
//        this.atualizadoEm = atualizadoEm;
//        this.imagem_url = imagem_url;
//    }
//
//    public Integer getId() { return id; }
//    public void setId(Integer id) { this.id = id; }
//
//    public String getNome() { return nome; }
//    public void setNome(String nome) { this.nome = nome; }
//
//    public String getDescricao() { return descricao; }
//    public void setDescricao(String descricao) { this.descricao = descricao; }
//
//    public BigDecimal getPreco() { return preco; }
//    public void setPreco(BigDecimal preco) { this.preco = preco; }
//
//    public Integer getQuantidade() { return quantidade; }
//    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
//
//    public Integer getLojaId() { return lojaId; }
//    public void setLojaId(Integer lojaId) { this.lojaId = lojaId; }
//
//    public LojaDto1 getLoja() { return loja; }
//    public void setLoja(LojaDto1 loja) { this.loja = loja; }
//
//    public Instant getCriadoEm() { return criadoEm; }
//    public void setCriadoEm(Instant criadoEm) { this.criadoEm = criadoEm; }
//
//    public Instant getAtualizadoEm() { return atualizadoEm; }
//    public void setAtualizadoEm(Instant atualizadoEm) { this.atualizadoEm = atualizadoEm; }
//
//    public String getImagem_url() { return imagem_url; }
//    public void setImagem_url(String imagem_url) { this.imagem_url = imagem_url; }
//}


import java.io.Serializable;
import java.math.BigDecimal;

public class ProdutoFormDto implements Serializable {
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer quantidade;
    private Integer lojaId;

    public ProdutoFormDto() {}

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public Integer getLojaId() { return lojaId; }
    public void setLojaId(Integer lojaId) { this.lojaId = lojaId; }
}
