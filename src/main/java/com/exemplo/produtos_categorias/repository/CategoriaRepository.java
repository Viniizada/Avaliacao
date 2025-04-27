package com.exemplo.produtos_categorias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exemplo.produtos_categorias.model.Categoria;

@Repository // Marca a interface como um componente Repository do Spring
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    // JpaRepository já fornece métodos CRUD básicos: save, findById, findAll, deleteById, etc.
    // Podem ser adicionados métodos de consulta personalizados aqui, se necessário.
}