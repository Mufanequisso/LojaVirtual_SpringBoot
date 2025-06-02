package com.example.demo.api;

import com.example.demo.dao.LojaRepository;
import com.example.demo.dao.ProdutoRepository;
import com.example.demo.dto.ProdutoDTO;

import com.example.demo.dto.ProdutoFormDto;
import com.example.demo.model.Loja;
import com.example.demo.model.Produto;
import com.example.demo.service.ProdutoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoRestController {

    private final ProdutoService produtoService;
    private final ProdutoRepository produtoRepository;
    private final LojaRepository lojaRepository; //  adicionar aqui

    @Autowired
    public ProdutoRestController(
            ProdutoService produtoService,
            ProdutoRepository produtoRepository,
            LojaRepository lojaRepository //  injectar aqui
    ) {
        this.produtoService = produtoService;
        this.produtoRepository = produtoRepository;
        this.lojaRepository = lojaRepository;
    }

    @GetMapping
    public List<ProdutoDTO> listarTodos() {
        return produtoService.listarTodos().stream()
                .map(ProdutoDTO::new)
                .collect(Collectors.toList());
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> salvarProduto(
            @RequestPart("produto") String produtoJson,
            @RequestPart("imagem") MultipartFile imagem) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            ProdutoFormDto produtoFormDTO = mapper.readValue(produtoJson,    ProdutoFormDto.class);

            String nomeArquivo = UUID.randomUUID() + "_" + imagem.getOriginalFilename();
            Path diretorioUploads = Paths.get("uploads");
            if (!Files.exists(diretorioUploads)) {
                Files.createDirectories(diretorioUploads);
            }
            Path arquivoPath = diretorioUploads.resolve(nomeArquivo);
            Files.write(arquivoPath, imagem.getBytes());

            Produto produto = new Produto();
            produto.setNome(produtoFormDTO.getNome());
            produto.setDescricao(produtoFormDTO.getDescricao());
            produto.setPreco(produtoFormDTO.getPreco());
            produto.setQuantidade(produtoFormDTO.getQuantidade());
            produto.setImagem_url("/uploads/" + nomeArquivo);

            // Simulação do vendedor autenticado
            int vendedorId = 1;

            // Agora
            Loja loja = lojaRepository.findByVendedorId(produtoFormDTO.getLojaId());
            if (loja == null) {
                return ResponseEntity.badRequest().body("Vendedor não possui loja");
            }

            produto.setLoja(loja);

            Produto salvo = produtoService.salvar(produto);
            return ResponseEntity.ok(salvo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar produto");
        }
    }
}
