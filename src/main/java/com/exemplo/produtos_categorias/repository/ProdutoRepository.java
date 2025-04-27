package com.exemplo.produtos_categorias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exemplo.produtos_categorias.model.Produto;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // Exemplo de método de consulta personalizado para buscar produtos por ID da categoria
    List<Produto> findByCategoriaId(Long categoriaId);
}