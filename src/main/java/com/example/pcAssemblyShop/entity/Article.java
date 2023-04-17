package com.example.pcAssemblyShop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Lob
    @Column
    private String body;
    @Column
    private Timestamp creation;

    @Builder
    public Article(Long id, String title, String body, Timestamp creation) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.creation = creation;
    }
}
