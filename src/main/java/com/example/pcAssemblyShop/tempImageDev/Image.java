package com.example.pcAssemblyShop.tempImageDev;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="image_category",discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor
public abstract class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column
    protected String fileName;
    @Column
    protected String saveFileName;
    @Column
    protected String filePath;
    @Column
    protected String cloudinaryUrl;
    @Column
    protected String contentType;
    @Column
    protected Long size;
    @Column
    protected LocalDateTime registerDate;


    public Image(Long id, String fileName, String saveFileName, String filePath, String cloudinaryUrl, String contentType, Long size, LocalDateTime registerDate) {
        this.id = id;
        this.fileName = fileName;
        this.saveFileName = saveFileName;
        this.filePath = filePath;
        this.cloudinaryUrl = cloudinaryUrl;
        this.contentType = contentType;
        this.size = size;
        this.registerDate = registerDate;
    }
}
