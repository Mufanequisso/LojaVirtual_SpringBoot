package com.example.demo.api;

import com.example.demo.dto.DesejoDto;
import com.example.demo.service.DesejoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/desejos")
public class DesejoRestController {

    @Autowired
    private DesejoService desejoService;

    @PostMapping("/{clienteId}")
    public ResponseEntity<Void> adicionarDesejo(
            @PathVariable Integer clienteId,
            @RequestBody DesejoRequest request
    ) {
        desejoService.salvarDesejo(clienteId, request.getProdutoId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("/{clienteId}")
    public ResponseEntity<List<DesejoDto>> listarDesejosPorCliente(@PathVariable Integer clienteId) {
        List<DesejoDto> desejos = desejoService.listarDesejosPorCliente(clienteId);
        return ResponseEntity.ok(desejos);
    }

    // Remover desejo
    @DeleteMapping("/{clienteId}/{produtoId}")
    public ResponseEntity<Void> removerDesejo(
            @PathVariable Integer clienteId,
            @PathVariable Integer produtoId) {
        desejoService.removerDesejo(clienteId, produtoId);
        return ResponseEntity.noContent().build();
    }


    // DTO de requisição
    public static class DesejoRequest {
        private Integer produtoId;

        public Integer getProdutoId() { return produtoId; }
        public void setProdutoId(Integer produtoId) { this.produtoId = produtoId; }
    }
}
