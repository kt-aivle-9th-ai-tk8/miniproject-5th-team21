package com.aivle.bookapp.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false) // DB 제약
    @NotBlank               // Bean Validation
    private String title;

    @Column(nullable=false) // DB 제약
    @NotBlank               // Bean Validation
    private String author;

    @Column(nullable=false) // DB 제약
    @NotBlank               // Bean Validation
    @Lob                    // 방대한 텍스트 저장 명시
    private String content;

    private String coverImageUrl;

    private String category;

    private String createdAt;

    private String updatedAt;
}
