package com.example.project1.services;

import com.example.project1.models.Books;
import com.example.project1.models.Person;
import com.example.project1.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BooksService {
    private final BooksRepository booksRepository;
    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Books> findAll(boolean sortByYear){
        if(sortByYear){
            return booksRepository.findAll(Sort.by("year"));
        }
        else {
            return booksRepository.findAll();
        }
    }
    public List<Books> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYear){
        if(sortByYear){
            return booksRepository.findAll(PageRequest.of(page,booksPerPage, Sort.by("year"))).getContent();
        }
        else {
            return booksRepository.findAll(PageRequest.of(page,booksPerPage)).getContent();
        }
    }

    public Books findOne(int id){
        Optional<Books> findBook = booksRepository.findById(id);
        return findBook.orElse(null);
    }
    public void save(Books books){
        booksRepository.save(books);
    }

    public void update(int id, Books updatedBook) {
        Books bookToBeUpdated = booksRepository.findById(id).get();

        updatedBook.setBook_id(id);
        updatedBook.setOwner(bookToBeUpdated.getOwner());

        booksRepository.save(updatedBook);
    }

    public void delete(int id){
        booksRepository.deleteById(id);
    }
    public List<Books> searchByTitle(String name) {
        return booksRepository.findByNameStartingWith(name);
    }

    public Person getBookOwner(int id) {
        return booksRepository.findById(id).map(Books::getOwner).orElse(null);
    }
    public void release(int id) {
        booksRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(null);
                });
    }

    public void assign(int id, Person selectedPerson) {
        booksRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(selectedPerson);
                    book.setTakenAt(new Date());
                }
        );
    }
    public void unsign(int id){
        booksRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(null);
                    book.setTakenAt(null);
                });
    }

}
