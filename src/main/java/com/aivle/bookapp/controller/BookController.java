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
    @PatchMapping("/books/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    // 도서 생성
    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) { // ResponseEntity는 그냥 외우기
        Book saved = bookService.createBook(book);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved); // 지금의 응답엔터티에 어떤 상태를 담을건지, // HTTP 응답 코드를 위해 존재
    }

    // 카테고리 필터
    @GetMapping("/books/search/category")
    public List<Book> getBookByCategory(@RequestParam String category) {
        return bookService.getBooksByCategory(category);
    }

    // 제목 검색
    @GetMapping("/books/search/title")
    public List<Book> searchByTitle(@RequestParam String title) {
        return bookService.searchByTitle(title);
    }

    // 저자 검색
    @GetMapping("/books/search/author")
    public List<Book> searchByAuthor(@RequestParam String author) {
        return bookService.searchByAuthor(author);
    }

    // 키워드로 검색
    @GetMapping("/books/search")
    public List<Book> searchByKeyword(@RequestParam String keyword) {
        return bookService.searchByKeyword(keyword);
    }

    // 제목, 저자 통합 검색
    @GetMapping("/books/search/detail")
    public List<Book> searchByTitleAndAuthor(@RequestParam String title, @RequestParam String author) {
        return bookService.searchBooks(title, author);
    }

    @GetMapping("/books/search")
    public List<Book> searchFilter(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String keyword
    ) {
        return bookService.searchBooksFilter(category, title, author, keyword);
    }
}