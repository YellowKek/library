package com.example.demo.servicies;

import com.example.demo.models.Book;
import com.example.demo.models.Person;
import com.example.demo.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public List<Book> findAllSorted(){
        return booksRepository.findAll(Sort.by("year"));
    }

    public Book findById(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setBook_id(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void removeOwner(int id){
        booksRepository.findById(id).ifPresent(book -> book.setOwner(null));
    }

    @Transactional
    public void setOwner(int bookId, Person person){
        booksRepository.findById(bookId).ifPresent(book -> book.setOwner(person));
    }


}
