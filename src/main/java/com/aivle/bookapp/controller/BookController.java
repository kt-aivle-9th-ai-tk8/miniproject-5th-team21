package com.aivle.bookapp.controller;

import com.aivle.bookapp.domain.Book;
import com.aivle.bookapp.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // 도서 단건 조회, 상세 정보
    @GetMapping("/books/{id}")
    public Book getBook(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    // 도서 삭제
    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    // 도서 수정
    @PutMapping("/books/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    // 도서 생성
    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        Book saved = bookService.createBook(book);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // 카테고리, 검색유형, 키워드 검색
    @GetMapping("/books")
    public List<Book> searchFilter(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String keyword
    ) {
        return bookService.searchBooksFilter(category, searchType, keyword);
    }

    // AI 도서 표지 수정
    @PatchMapping("/books/{id}/cover")
    public ResponseEntity<Book> aiBookCover(@PathVariable Long id, @RequestBody String coverImageUrl) {
        Book aiCover = bookService.updateCoverImageUrl(id, coverImageUrl);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(aiCover);
    }
}