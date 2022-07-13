package com.example.project1.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Books {
    private int book_id;
    private int id;
    @NotEmpty(message = "name should not be empty")
    @Size(min = 2, max = 30, message = "size of your name is wrong")
    private String name;
    @NotEmpty(message = "name should not be empty")
    @Size(min = 2, max = 30, message = "size of your name is wrong")
    private String author;


    @Min(value = 0, message = "year should not be negative")
    private int year;

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public Books(int book_id, int id, String name, String author, int year) {
        this.book_id = book_id;
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Books() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
