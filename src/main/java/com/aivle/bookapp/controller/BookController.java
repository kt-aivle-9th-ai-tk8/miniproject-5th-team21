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

    // 전체 도서 목록
    @GetMapping("/books")
    public List<Book> getAll() {
        return bookService.getAllBooks();
    }

    // 도서 삭제
    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build(); // HTTP 응답 코드를 위해 존재
    }

    // 도서 수정
    @PutMapping("/books/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    // 도서 생성
    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) { // ResponseEntity는 그냥 외우기
        Book saved = bookService.createBook(book);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved); // 지금의 응답엔터티에 어떤 상태를 담을건지, // HTTP 응답 코드를 위해 존재
    }

    // 카테고리, 제목, 저자, 키워드 검색
    @GetMapping("/books/search")
    public List<Book> searchFilter(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String keyword
    ) {
        return bookService.searchBooksFilter(category, searchType, keyword);
    }
}