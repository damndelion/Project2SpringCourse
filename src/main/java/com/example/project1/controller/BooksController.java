package com.example.project1.controller;

import com.example.project1.models.Books;
import com.example.project1.models.Person;
import com.example.project1.services.BooksService;
import com.example.project1.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final PeopleService peopleService;
    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping
    public String index(Model model, @RequestParam(value = "page" , required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false) boolean sortByYear){
        if(page == null || booksPerPage == null){
            model.addAttribute("books", booksService.findAll(sortByYear));
        }
        else {
            model.addAttribute("books", booksService.findWithPagination(page,booksPerPage,sortByYear));
        }
        return "books/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person")Person person){
        model.addAttribute("book", booksService.findOne(id));

        Optional<Person> bookOwner = Optional.ofNullable(booksService.getBookOwner(id));

        if (bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());
        else
            model.addAttribute("people", peopleService.findAll());


        return "books/show";
    }
    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person")Person person) {
        booksService.assign(id, person);
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
        booksService.save(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model,@PathVariable("id") int id){
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Books books, BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors())
            return "books/edit";
        booksService.update(id, books);
        return "redirect:/books";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        booksService.delete(id);
        return "redirect:/books";
    }
    @PatchMapping("/{id}/unsign")
    public String unsign(@PathVariable("id") int id){
        booksService.unsign(id);
        return "redirect:/books";
    }
    @GetMapping("/search")
    public String search(){
        return "books/search";
    }
    @PostMapping("/search")
    public String searchRequest(Model model, @RequestParam("input") String input){
        model.addAttribute("books" , booksService.searchByTitle(input));
        return "books/search";
    }
}
