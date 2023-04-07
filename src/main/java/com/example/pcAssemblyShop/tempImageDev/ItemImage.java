package com.example.pcAssemblyShop.tempImageDev;

import com.example.pcAssemblyShop.entity.Article;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@DiscriminatorValue("item_image_indicator")  // this will be stored on Image DType column to indicate it is article_image related\
@AllArgsConstructor
public class ItemImage extends  Image{
   @Column
    private String detail;
}
