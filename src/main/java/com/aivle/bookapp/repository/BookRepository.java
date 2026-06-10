package com.aivle.bookapp.repository;

import com.aivle.bookapp.domain.Book;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    // category 비명시
    List<Book> findByTitleContaining(String title);
    List<Book> findByAuthorContaining(String author);
    default List<Book> findBySearchKeyword(String keyword) {
        return findByTitleContainingOrAuthorContaining(keyword, keyword);
    }
    // category 명시
    List<Book> findByCategory(String category);
    List<Book> findByCategoryAndTitleContaining(String category, String author);
    List<Book> findByCategoryAndAuthorContaining(String category, String author);
    default List<Book> findByCategoryAndSearchKeyword(String category, String keyword) {
        return findByCategoryAndTitleContainingOrCategoryAndAuthorContaining(category, keyword, category, keyword);
    }

    List<Book> findByTitleContainingOrAuthorContaining(String title, String author); // 직접 미사용
    List<Book> findByCategoryAndTitleContainingOrCategoryAndAuthorContaining(String category1, String title, String category2, String author);
}
