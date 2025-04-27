package com.exemplo.produtos_categorias.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal; // Usar BigDecimal para valores monetários

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "produtos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @Column(nullable = false, precision = 10, scale = 2) // Precisão para valores monetários
    private BigDecimal preco; // Alterado para BigDecimal

    // Lado "Muitos" do relacionamento ManyToOne com Categoria
    // FetchType.LAZY: Carrega a Categoria somente quando for acessada (melhor performance)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false) // Define a coluna de chave estrangeira na tabela 'produtos'
    @JsonBackReference // Evita recursão infinita ao serializar (Categoria -> Produto -> Categoria)
    private Categoria categoria; // A categoria à qual este produto pertence
}