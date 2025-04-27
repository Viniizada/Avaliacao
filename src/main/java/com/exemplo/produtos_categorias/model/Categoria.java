package com.exemplo.produtos_categorias.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity // Marca a classe como uma entidade JPA
@Table(name = "categorias") // Nome da tabela no banco (opcional, por padrão seria 'categoria')
@Data // Lombok: Gera getters, setters, toString, equals, hashCode
@NoArgsConstructor // Lombok: Gera construtor sem argumentos
@AllArgsConstructor // Lombok: Gera construtor com todos os argumentos
public class Categoria {

    @Id // Chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremento pelo banco
    private Long id;

    @Column(nullable = false, length = 100) // Coluna não nula, tamanho máximo 100
    private String nome;

    @Column(length = 255)
    private String descricao;

    // Lado "Um" do relacionamento OneToMany com Produto
    // mappedBy indica o nome do campo na classe Produto que mapeia esta relação
    // cascade = CascadeType.ALL: operações (persist, merge, remove, etc.) na Categoria são cascateadas para Produtos
    // orphanRemoval = true: se um Produto for removido da lista desta Categoria, ele será deletado do banco
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Produto> produtos; // Lista de produtos pertencentes a esta categoria
}