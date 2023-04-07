package com.example.pcAssemblyShop.tempImageDev;


import com.example.pcAssemblyShop.entity.Article;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@DiscriminatorValue("article_image_indicator")  // this will be stored on Image DType column to indicate it is article_image related
@AllArgsConstructor
public class ArticleImage extends Image {
    @ManyToOne
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    private Article article;
}

