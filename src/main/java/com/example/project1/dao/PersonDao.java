package com.example.project1.dao;

import com.example.project1.models.Books;
import com.example.project1.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDao {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Person> index(){
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id){
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }
    public List<Books> check(int id){
        return jdbcTemplate.query("SELECT * FROM Books WHERE id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Books.class));
    }
    public Optional<Person> getPersonByFullName(String name) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE name=?", new Object[]{name},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void update(int id, Person person){
        jdbcTemplate.update("UPDATE Person SET name=?, yearofBirth=? WHERE id=?",
                person.getName(),person.getYearOfBirth(),id);
    }
    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Person WHERE id=?",id);
    }
    public void save(Person person){
        jdbcTemplate.update("INSERT INTO Person(name,yearofBirth) VALUES(?, ?)",
                person.getName(),person.getYearOfBirth());
    }
}
