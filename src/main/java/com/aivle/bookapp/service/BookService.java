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

        if(book.getTitle() != null){
            existing.setTitle(book.getTitle());
        }
        if(book.getAuthor() != null){
            existing.setAuthor(book.getAuthor());
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


    public List<Book> searchBooksFilters(String category){

    }

}
