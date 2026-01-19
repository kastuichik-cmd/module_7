package com.traineeship.module_6_easy.service;


import com.traineeship.module_6_easy.advice.BookNotFoundException;
import com.traineeship.module_6_easy.model.entity.Author;
import com.traineeship.module_6_easy.model.entity.Book;
import com.traineeship.module_6_easy.model.dto.BookDto;
import com.traineeship.module_6_easy.repository.AuthorRepository;
import com.traineeship.module_6_easy.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found: " + id));
    }

    public void deleteBookById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found: " + id);
        }
        bookRepository.deleteById(id);
    }

    public void printBooksAuthors() {
        List<Book> books = bookRepository.findAllWithAuthors();
        for (Book book : books) {
            System.out.println(book.getTitle() + " - " + book.getAuthor().getName());
        }
    }

    public List<Book> getBooksByAuthor(String authorName) {
        Author author = getAuthorByName(authorName);
        return bookRepository.findByAuthor(author);
    }

    public Optional<Book> getBookByTitleAndAuthor(String title, String authorName) {
        Author author = getAuthorByName(authorName);
        return bookRepository.findByTitleAndAuthor(title, author);
    }


    public Book createBook(BookDto bookDto) {
        Author author = getOrCreateAuthor(bookDto.getAuthor());

        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(author);

        return bookRepository.save(book);
    }

    public Book updateBook(Long id, BookDto bookDto) {
        Book book = getBookById(id);
        book.setTitle(bookDto.getTitle());
        book.setAuthor(getOrCreateAuthor(bookDto.getAuthor()));
        return bookRepository.save(book);
    }

    public List<Book> searchBooksByTitle(String searchText) {
        return bookRepository.findByTitleContainingIgnoreCaseOrderByPublicationYearDesc(searchText);
    }

    @Transactional
    public Book createBookWithAuthor(BookDto bookDto, boolean throwError) {
        Author author = new Author();
        author.setName(bookDto.getAuthor());
        authorRepository.save(author);

        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(author);

        if (throwError) {
            throw new RuntimeException("Ошибка после сохранения автора!");
        }

        return bookRepository.save(book);
    }

    private Author getAuthorByName(String authorName) {
        return authorRepository.findByNameIgnoreCase(authorName)
                .orElseThrow(() ->
                        new BookNotFoundException("Author not found: " + authorName)
                );
    }

    private Author getOrCreateAuthor(String authorName) {
        return authorRepository.findByNameIgnoreCase(authorName)
                .orElseGet(() -> {
                    Author author = new Author();
                    author.setName(authorName);
                    return authorRepository.save(author);
                });
    }
}
