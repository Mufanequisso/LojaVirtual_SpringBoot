package com.example.demo.dto;



import java.util.List;

public class PedidoRequest {
    private Integer clienteId;
    private List<ItemPedidoDto> itens;

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public List<ItemPedidoDto> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoDto> itens) {
        this.itens = itens;
    }
}
