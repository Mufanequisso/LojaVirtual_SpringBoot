package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Entity
@Table(name = "configuracoes_sistema")
public class ConfiguracoesSistema {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ColumnDefault("10.00")
    @Column(name = "porcentagem_comissao", precision = 5, scale = 2)
    private BigDecimal porcentagemComissao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getPorcentagemComissao() {
        return porcentagemComissao;
    }

    public void setPorcentagemComissao(BigDecimal porcentagemComissao) {
        this.porcentagemComissao = porcentagemComissao;
    }

}