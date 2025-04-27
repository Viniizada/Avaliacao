package com.exemplo.produtos_categorias.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.exemplo.produtos_categorias.model.Categoria;
import com.exemplo.produtos_categorias.repository.CategoriaRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias") // Caminho base para os endpoints de categoria
public class CategoriaController {

    @Autowired // Injeta a dependência do repositório
    private CategoriaRepository categoriaRepository;

    // GET /api/categorias - Listar todas
    @GetMapping
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    // GET /api/categorias/{id} - Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarCategoriaPorId(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return categoria.map(ResponseEntity::ok) // Se encontrar, retorna 200 OK com a categoria
                        .orElse(ResponseEntity.notFound().build()); // Se não, retorna 404 Not Found
    }

    // POST /api/categorias - Criar nova categoria
    @PostMapping
    public ResponseEntity<Categoria> criarCategoria(@RequestBody Categoria categoria) {
        // Não setamos o ID aqui, pois ele é gerado automaticamente (@GeneratedValue)
        Categoria novaCategoria = categoriaRepository.save(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria); // Retorna 201 Created
    }

    // PUT /api/categorias/{id} - Atualizar categoria existente
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> atualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoriaDetalhes) {
        return categoriaRepository.findById(id)
                .map(categoriaExistente -> {
                    categoriaExistente.setNome(categoriaDetalhes.getNome());
                    categoriaExistente.setDescricao(categoriaDetalhes.getDescricao());
                    // Não atualizamos a lista de produtos aqui diretamente, isso é gerenciado pelo Produto
                    Categoria categoriaAtualizada = categoriaRepository.save(categoriaExistente);
                    return ResponseEntity.ok(categoriaAtualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/categorias/{id} - Deletar categoria
    // DELETE /api/categorias/{id} - Deletar categoria
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
    // Verifica primeiro se a categoria existe
    Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
    if (categoriaOptional.isEmpty()) {
        // Se não existe, retorna 404 Not Found
        return ResponseEntity.notFound().build();
    }
    // Se existe, deleta
    categoriaRepository.delete(categoriaOptional.get());
    // Retorna 204 No Content
    return ResponseEntity.noContent().build();
}
}