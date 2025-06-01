package com.example.demo.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * DTO for {@link com.example.demo.model.Loja}
 */
public class LojaDto1 implements Serializable {
    private final Integer id;
    private final String nome;
    private final String descricao;
    private final UsuarioDto1 vendedor;
    private final Instant criadoEm;

    public LojaDto1(Integer id, String nome, String descricao, UsuarioDto1 vendedor, Instant criadoEm) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.vendedor = vendedor;
        this.criadoEm = criadoEm;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public UsuarioDto1 getVendedor() {
        return vendedor;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LojaDto1 entity = (LojaDto1) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.nome, entity.nome) &&
                Objects.equals(this.descricao, entity.descricao) &&
                Objects.equals(this.vendedor, entity.vendedor) &&
                Objects.equals(this.criadoEm, entity.criadoEm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, descricao, vendedor, criadoEm);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "nome = " + nome + ", " +
                "descricao = " + descricao + ", " +
                "vendedor = " + vendedor + ", " +
                "criadoEm = " + criadoEm + ")";
    }
}