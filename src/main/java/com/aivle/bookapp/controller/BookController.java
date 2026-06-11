package com.aivle.bookapp.controller;

import com.aivle.bookapp.domain.Book;
import com.aivle.bookapp.dto.*;
import com.aivle.bookapp.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // 도서 단건 조회, 상세 정보
    @GetMapping("/books/{id}")
    // 반환형은 프론트엔드가 요구하는 형태일 것
    public ResponseEntity<BookResponse> getBook(@PathVariable Long id) {
        // bookService로부터 id번 책을 원본말고 BookDto에 담아 가져옴
        BookDto bookDto = bookService.getBookById(id);
        // bookService로부터 받은 bookDto를 프론트엔드를 위한 BookResponse로 재포장
        return ResponseEntity.ok(BookResponse.from(bookDto));
    }

    // 도서 삭제
    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);

        return ResponseEntity.noContent().build();
    }

    // 도서 수정
    @PutMapping("/books/{id}")
    // @RequestBody로 넘어온 JSON 데이터를 PutBookRequest에 담음
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id, @Valid @RequestBody PutBookRequest request) {
        // bookService가 읽기 편한 UpdateBookCommand로 변환
        UpdateBookCommand command = request.toCommand();
        // bookService로부터 수정된 id번 책을 command 형태로 BookDto에 담아 가져옴
        BookDto updatedBookDto = bookService.updateBook(id, command);
        // bookService로부터 받은 updatedBookDto를 프론트엔드를 위한 BookResponse로 재포장
        return ResponseEntity.ok(BookResponse.from(updatedBookDto));
    }

    // 도서 생성
    @PostMapping("/books")
    // @RequestBody로 넘어온 JSON 데이터를 PostBookRequest에 담음
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody PostBookRequest request) {
        // bookService가 읽기 편한 CreateBookCommand로 변환
        CreateBookCommand command = request.toCommand();
        // bookService로부터 생성된 새 데이터를 command 형태로 BookDto에 담아 가져옴
        BookDto savedBookDto = bookService.createBook(command);
        // bookService로부터 받은 savedBookDto를 프론트엔드를 위한 BookResponse로 재포장
        return ResponseEntity.status(HttpStatus.CREATED).body(BookResponse.from(savedBookDto));
    }

    // 카테고리, 검색유형, 키워드 검색
    @GetMapping("/books")
    public ResponseEntity<List<BookResponse>> searchFilter(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String keyword
    ) {
        //
        List<BookDto> books = bookService.searchBooksFilter(category, searchType, keyword);
        List<BookResponse> responses = books.stream()
                .map(BookResponse::from)
                .collect(Collectors.toList());
        // 프론트엔드를 위한 responses로 재포장
        return ResponseEntity.ok(responses);
    }

    // AI 도서 표지 수정
    @PatchMapping("/books/{id}/cover")
    // @RequestBody로 넘어온 JSON 데이터를 PatchBookCoverImageUrlRequest에 담음
    public ResponseEntity<BookResponse> aiBookCover(@PathVariable Long id, @RequestBody PatchBookCoverImageUrlRequest request) {
        //
        UpdateBookCoverImageUrlCommand command = request.toCommand();
        // bookService로부터 id번 책의 AI 표지를 command 형태로 BookDto에 담아 가져옴
        BookDto aiCoverDto = bookService.updateCoverImageUrl(id, command);
        // bookService로부터 받은 aiCoverDto를 프론트엔드를 위한 BookResponse로 재포장
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(BookResponse.from(aiCoverDto));
    }
}