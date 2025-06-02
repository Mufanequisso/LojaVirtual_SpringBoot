package com.example.demo.dto;

import java.util.List;

public class PedidoDto {

    private Long clienteId;
    private List<ItemPedidoDTO> itens;

    // getters e setters

    public static class ItemPedidoDTO {
        private Long produtoId;
        private int quantidade;

        public Long getProdutoId() {
            return produtoId;
        }

        public void setProdutoId(Long produtoId) {
            this.produtoId = produtoId;
        }

        public int getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }
// getters e setters
    }
}
