package com.example.pcAssemblyShop.repository;

import com.example.pcAssemblyShop.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {
}
