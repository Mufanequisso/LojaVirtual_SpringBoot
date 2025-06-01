package com.example.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "comissoes_admin")
public class ComissoesAdmin {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_pedido_id")
    private ItensPedido itemPedido;

    @Column(name = "valor_total_comissao", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotalComissao;

    @ColumnDefault("current_timestamp()")
    @Column(name = "data_registro", nullable = false)
    private Instant dataRegistro;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public ItensPedido getItemPedido() {
        return itemPedido;
    }

    public void setItemPedido(ItensPedido itemPedido) {
        this.itemPedido = itemPedido;
    }

    public BigDecimal getValorTotalComissao() {
        return valorTotalComissao;
    }

    public void setValorTotalComissao(BigDecimal valorTotalComissao) {
        this.valorTotalComissao = valorTotalComissao;
    }

    public Instant getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(Instant dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

}