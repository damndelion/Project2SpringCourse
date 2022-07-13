package com.example.project1.controller;

import com.example.project1.dao.BookDao;
import com.example.project1.dao.PersonDao;
import com.example.project1.models.Books;
import com.example.project1.models.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDao bookDao;
    private final PersonDao personDao;

    public BooksController(BookDao bookDao, PersonDao personDao) {
        this.bookDao = bookDao;
        this.personDao = personDao;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("books", bookDao.index());
        return "books/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person")Person person){
        model.addAttribute("book", bookDao.show(id));
        model.addAttribute("owner", personDao.show(bookDao.show(id).getId()));
        model.addAttribute("people", personDao.index());
        return "books/show";
    }
    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person")Person person) {
        bookDao.assign(id,person);
        return "redirect:/books";
    }
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book")Books books){
        return "books/new";
    }
    @PostMapping
    public String create(@ModelAttribute("book") @Valid Books book, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "books/new";
        bookDao.save(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model,@PathVariable("id") int id){
        model.addAttribute("book", bookDao.show(id));
        return "books/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Books books, BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors())
            return "books/edit";
        bookDao.update(id, books);
        return "redirect:/books";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDao.delete(id);
        return "redirect:/books";
    }
    @PatchMapping("/{id}/unsign")
    public String unsign(@PathVariable("id") int id){
        bookDao.unsign(id);
        return "redirect:/books";
    }
}
