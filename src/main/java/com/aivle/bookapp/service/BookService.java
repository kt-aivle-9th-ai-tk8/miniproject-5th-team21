package com.aivle.bookapp.service;

import com.aivle.bookapp.domain.Book;
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
    public Book updateBook(Long id, Book book){
        Book existing = getBookById(id);

        if (book.getTitle() != null) {
            existing.setTitle(book.getTitle());
        }
        if (book.getAuthor() != null) {
            existing.setAuthor(book.getAuthor());
        }
        if (book.getContent() != null) {
            existing.setContent(book.getContent());
        }
        if (book.getCategory() != null) {
            existing.setCategory(book.getCategory());
        }

        return bookRepository.save(existing);
    }

    @Transactional
    public Book createBook(Book book){
        return bookRepository.save(book);
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
    public List<Book> searchBooksFilter(String category, String searchType, String keyword){
        // 1. 카테고리 필터링만 있고 검색어가 없는 경우
        if ((category != null && !category.isEmpty()) && (keyword == null || keyword.isEmpty())) {
            return bookRepository.findByCategory(category);
        }

        // 2. 검색 유형(searchType)에 따른 분기 처리
        if (searchType != null && keyword != null && !keyword.isEmpty()) {
            switch (searchType.toLowerCase()) {
                case "title":
                    return bookRepository.findByTitleContaining(keyword); // 제목 검색
                case "author":
                    return bookRepository.findByAuthorContaining(keyword); // 저자 검색
                case "total":
                default:
                    // 제목 + 저자 통합 검색
                    return bookRepository.findByTitleContainingOrAuthorContaining(keyword, keyword);
            }
        }

        // 3. 조건이 없다면 전체 목록 반환
        return bookRepository.findAll();
    }

}
