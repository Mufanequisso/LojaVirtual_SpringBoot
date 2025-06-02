package com.example.demo.dao;



import com.example.demo.model.Pedido;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    @PersistenceContext
    EntityManager em = null;
    List<Pedido> findByClienteId(Integer clienteId);

    // Retorna todos os pedidos com o status informado (ex: "pendente")
    List<Pedido> findByStatus(String status);

    @Query("SELECT p FROM Pedido p WHERE p.cliente.id = :clienteId AND p.status = 'pendente'")
    List<Pedido> buscarPedidosPendentesPorCliente(Integer clienteId);

    // Método para buscar pedido pelo ID e pelo ID do cliente
    Optional<Pedido> findByIdAndClienteId(Integer pedidoId, Integer clienteId);

    // Exemplo: buscar pedidos pendentes de um cliente
    List<Pedido> findByClienteIdAndStatus(Integer clienteId, String status);
}
