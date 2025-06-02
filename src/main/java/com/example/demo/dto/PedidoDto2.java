package com.example.demo.dto;

import java.math.BigDecimal;
import java.util.List;

public class PedidoDto2 {
    private Integer id;
    private List<ItemPedidoDTO> itensPedidos;
    private BigDecimal total;
    private String status;
    private int quantidadeTotal; // NOVO CAMPO

    // Getters e setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public List<ItemPedidoDTO> getItensPedidos() { return itensPedidos; }
    public void setItensPedidos(List<ItemPedidoDTO> itensPedidos) { this.itensPedidos = itensPedidos; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getQuantidadeTotal() { return quantidadeTotal; }
    public void setQuantidadeTotal(int quantidadeTotal) { this.quantidadeTotal = quantidadeTotal; }

    public static class ItemPedidoDTO {
        private Integer produtoId;
        private String produtoNome;
        private int quantidade;

        // Getters e setters
        public Integer getProdutoId() { return produtoId; }
        public void setProdutoId(Integer produtoId) { this.produtoId = produtoId; }

        public String getProdutoNome() { return produtoNome; }
        public void setProdutoNome(String produtoNome) { this.produtoNome = produtoNome; }

        public int getQuantidade() { return quantidade; }
        public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    }
}
