//package com.example.demo.service;
//
//import com.example.demo.dao.DesejoRepository;
//import com.example.demo.dto.DesejoDto;
//import com.example.demo.model.Desejo;
//import com.example.demo.model.Produto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class DesejoService {
//
//    @Autowired
//    private DesejoRepository desejoRepository;
//
//    public void adicionarDesejo(Integer clienteId, Integer produtoId) {
//        Desejo desejo = new Desejo();
//        desejo.setCliente(clienteId);
//        desejo.setProduto(produtoId);
//        desejo.setDataCriacao(new Date().toInstant());
//        desejoRepository.save(desejo);
//    }
//
//
//}

package com.example.demo.service;

import com.example.demo.dto.DesejoDto;
import com.example.demo.model.Desejo;
import com.example.demo.model.Cliente;
import com.example.demo.model.Produto;
import com.example.demo.dao.ClienteRepository;
import com.example.demo.dao.DesejoRepository;
import com.example.demo.dao.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DesejoService {

    @Autowired
    private DesejoRepository desejoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public Desejo salvarDesejo(Integer clienteId, Integer produtoId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Desejo desejo = new Desejo();
        desejo.setCliente(cliente);
        desejo.setProduto(produto);
        desejo.setImagem(produto.getImagem_url());

        return desejoRepository.save(desejo);
    }

    public List<DesejoDto> listarDesejosPorCliente(Integer clienteId) {
        List<Desejo> desejos = desejoRepository.findByClienteId(clienteId);

        return desejos.stream().map(d -> {
            Produto p = d.getProduto();
            return new DesejoDto(
                    d.getId(),
                    d.getCliente().getId(),
                    p.getId(),
                    p.getNome(),
                    p.getPreco(),
                    p.getLoja().getNome(),
                    d.getDataCriacao(),
                    d.getImagem()
            );
        }).collect(Collectors.toList());
    }

    // Metodo para remover desejo
    public void removerDesejo(Integer clienteId, Integer produtoId) {

        Optional<Desejo> desejoOpt = desejoRepository.findByClienteIdAndProdutoId(clienteId, produtoId);

        if (desejoOpt.isPresent()) {
            desejoRepository.delete(desejoOpt.get());
        } else {
            // Opcional: lançar exceção caso não encontrado, para controle
            throw new EntityNotFoundException("Desejo não encontrado para remoção.");
        }
    }
}

