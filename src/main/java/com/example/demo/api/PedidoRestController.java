package com.example.demo.api;


import com.example.demo.dto.ItemPedidoDto;
import com.example.demo.dto.PedidoDto2;
import com.example.demo.model.Pedido;
import com.example.demo.service.PedidoService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PedidoRestController {

    @Autowired
    private PedidoService pedidoService;


    @PostMapping("/criar/{clienteId}")
    public ResponseEntity<PedidoDto2> criarPedidoCliente(
            @PathVariable Integer clienteId,
            @RequestBody List<ItemPedidoDto> itens
    ) {
        PedidoDto2 pedidoDto = pedidoService.criarPedido(clienteId, itens);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoDto);
    }





    // Listar todos os pedidos
    @GetMapping
    public ResponseEntity<List<Pedido>> listarPedidos() {
        List<Pedido> pedidos = pedidoService.listarTodos();
        return ResponseEntity.ok(pedidos);
    }

    // Buscar pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPedido(@PathVariable Integer id) {
        return pedidoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Listar pedidos por cliente
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> listarPorCliente(@PathVariable Integer clienteId) {
        List<Pedido> pedidos = pedidoService.listarPorCliente(clienteId);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/pendentes")
    public List<Pedido> listarPendentesPorCliente(HttpSession session) {
        Integer clienteId = (Integer) session.getAttribute("clienteId");
        if (clienteId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Cliente não logado");
        }
        return pedidoService.listarPorCliente(clienteId);
    }

    // api para criar desejo

}
