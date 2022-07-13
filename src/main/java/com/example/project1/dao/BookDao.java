package com.example.project1.dao;

import com.example.project1.models.Books;
import com.example.project1.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class BookDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Books> index() {
        return jdbcTemplate.query("SELECT * FROM Books", new BeanPropertyRowMapper<>(Books.class));
    }

    public Books show(int id){
        return jdbcTemplate.query("SELECT * FROM Books WHERE book_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Books.class)).stream().findAny().orElse(null);
    }
    public Books getOwner(int id){
        return jdbcTemplate.query("SELECT * FROM Books WHERE id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Books.class)).stream().findAny().orElse(null);
    }
    public void assign(int id, Person person){
        jdbcTemplate.update("UPDATE Books SET id=? WHERE book_id=?", person.getId(), id);
    }
    public void save(Books book){
        jdbcTemplate.update("INSERT INTO Books(name,author,year) VALUES(?, ?, ?)",
                book.getName(), book.getAuthor(), book.getYear());
    }
    public void update(int id, Books books){
        jdbcTemplate.update("UPDATE Books SET name=?, author=?, year=? WHERE book_id=?",
                books.getName(), books.getAuthor(), books.getYear(), id);
    }
    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Books WHERE book_id=?",id);
    }
    public void unsign(int id){
        jdbcTemplate.update("UPDATE Books SET id=? WHERE book_id=?",0,id);
    }

}
