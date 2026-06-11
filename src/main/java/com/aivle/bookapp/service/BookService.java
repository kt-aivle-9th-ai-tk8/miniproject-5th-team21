package com.aivle.bookapp.service;

import com.aivle.bookapp.domain.Book;
import com.aivle.bookapp.exception.BookNotFoundException;
import com.aivle.bookapp.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Book getBookById(Long id){
        return bookRepository.findById(id).orElseThrow(()->new BookNotFoundException(id));
    }

    @Transactional
    public Book createBook(Book book){
        return bookRepository.save(book);
    }

    @Transactional
    public Book updateBook(Long id, Book book){
        Book existing = getBookById(id);

        existing.setTitle(book.getTitle());
        existing.setAuthor(book.getAuthor());
        existing.setContent(book.getContent());
        existing.setCategory(book.getCategory());
        existing.setCoverImageUrl(book.getCoverImageUrl());
        existing.setUpdatedAt(book.getUpdatedAt());

        return bookRepository.save(existing);
    }

    @Transactional
    public void deleteBook(Long id){
        if(bookRepository.existsById(id)){
            bookRepository.deleteById(id);
        }else {
            throw new BookNotFoundException(id);
        }
    }

    @Transactional(readOnly = true)
    public List<Book> searchBooksFilter(String category, String keyword, String searchType) {

        String cleanCategory   = (category != null && !category.trim().isEmpty()) ? category.trim() : null;
        String cleanKeyword    = (keyword != null && !keyword.trim().isEmpty()) ? keyword.trim() : null;
        String cleanSearchType = (searchType != null && !searchType.trim().isEmpty()) ? searchType.trim().toLowerCase() : "total";

        // 1. 카테고리만 있는 경우
        if (cleanCategory != null && cleanKeyword == null) {
            return bookRepository.findByCategory(cleanCategory);
        }

        // 2. 카테고리 + 검색어
        if (cleanCategory != null) {
            switch (cleanSearchType) {
                case "title":
                    return bookRepository.findByCategoryAndTitleContaining(cleanCategory, cleanKeyword);
                case "author":
                    return bookRepository.findByCategoryAndAuthorContaining(cleanCategory, cleanKeyword);
                case "total":
                default:
                    return bookRepository.findByCategoryAndSearchKeyword(cleanCategory, cleanKeyword);
            }
        }

        // 3. 검색어만 있는 경우
        if (cleanKeyword != null) {
            switch (cleanSearchType) {
                case "title":
                    return bookRepository.findByTitleContaining(cleanKeyword);
                case "author":
                    return bookRepository.findByAuthorContaining(cleanKeyword);
                default:
                    return bookRepository.findBySearchKeyword(cleanKeyword);
            }
        }

        // 4. 카테고리없고, 검색어도 없는 경우 -> 전체조회
        return bookRepository.findAll();
    }

    @Transactional
    public Book updateCoverImageUrl(Long id, String coverImageUrl){
        Book book = getBookById(id);
        book.setCoverImageUrl(coverImageUrl);

        return book;
    }

}
