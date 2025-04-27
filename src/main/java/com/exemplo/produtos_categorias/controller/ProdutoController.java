package com.exemplo.produtos_categorias.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.exemplo.produtos_categorias.model.Categoria;
import com.exemplo.produtos_categorias.model.Produto;
import com.exemplo.produtos_categorias.repository.CategoriaRepository;
import com.exemplo.produtos_categorias.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository; // Necessário para associar a categoria

    // GET /api/produtos - Listar todos
    @GetMapping
    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    // GET /api/produtos/{id} - Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/categorias/{categoriaId}/produtos - Listar produtos por categoria
    @GetMapping("/por-categoria/{categoriaId}")
    public ResponseEntity<List<Produto>> listarProdutosPorCategoria(@PathVariable Long categoriaId) {
         if (!categoriaRepository.existsById(categoriaId)) {
            return ResponseEntity.notFound().build(); // Categoria não existe
         }
         List<Produto> produtos = produtoRepository.findByCategoriaId(categoriaId);
         return ResponseEntity.ok(produtos);
    }


    // POST /api/produtos - Criar novo produto associado a uma categoria
    @PostMapping
    public ResponseEntity<?> criarProduto(@RequestBody Produto produto) {
        // Verifica se a categoria foi informada e existe
        if (produto.getCategoria() == null || produto.getCategoria().getId() == null) {
             return ResponseEntity.badRequest().body("ID da Categoria é obrigatório.");
        }

        Optional<Categoria> categoriaOptional = categoriaRepository.findById(produto.getCategoria().getId());
        if (categoriaOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Categoria com ID " + produto.getCategoria().getId() + " não encontrada.");
        }

        // Associa a categoria gerenciada pelo JPA ao produto antes de salvar
        produto.setCategoria(categoriaOptional.get());
        Produto novoProduto = produtoRepository.save(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }

    // PUT /api/produtos/{id} - Atualizar produto existente
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarProduto(@PathVariable Long id, @RequestBody Produto produtoDetalhes) {
         return produtoRepository.findById(id)
            .map(produtoExistente -> {
                // Verifica e atualiza a categoria se necessário
                if (produtoDetalhes.getCategoria() != null && produtoDetalhes.getCategoria().getId() != null) {
                    Optional<Categoria> categoriaOptional = categoriaRepository.findById(produtoDetalhes.getCategoria().getId());
                    if (categoriaOptional.isEmpty()) {
                         return ResponseEntity.badRequest().body("Categoria com ID " + produtoDetalhes.getCategoria().getId() + " não encontrada para atualização.");
                    }
                     produtoExistente.setCategoria(categoriaOptional.get());
                } else {
                     return ResponseEntity.badRequest().body("ID da Categoria é obrigatório para atualização.");
                }

                produtoExistente.setNome(produtoDetalhes.getNome());
                produtoExistente.setDescricao(produtoDetalhes.getDescricao());
                produtoExistente.setPreco(produtoDetalhes.getPreco());

                Produto produtoAtualizado = produtoRepository.save(produtoExistente);
                return ResponseEntity.ok(produtoAtualizado);
             })
             .orElse(ResponseEntity.notFound().build());
    }


    // DELETE /api/produtos/{id} - Deletar produto
        @DeleteMapping("/{id}")
         public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
    // Verifica primeiro se o produto existe
    Optional<Produto> produtoOptional = produtoRepository.findById(id);
    if (produtoOptional.isEmpty()) {
        // Se não existe, retorna 404 Not Found
        return ResponseEntity.notFound().build();
    }
    // Se existe, deleta
    produtoRepository.delete(produtoOptional.get());
    // Retorna 204 No Content
    return ResponseEntity.noContent().build();
}
}