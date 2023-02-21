package com.example.demo.controllers;

import com.example.demo.models.Book;
import com.example.demo.models.Person;
import com.example.demo.servicies.BooksService;
import com.example.demo.servicies.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.function.BiConsumer;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    protected final PeopleService peopleService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping("")
    public String showAll(Model model, @RequestParam(value = "sort_by_year", required = false) boolean sort){
        if (sort)
            model.addAttribute("books", booksService.findAllSorted());
        else
            model.addAttribute("books", booksService.findAll());
        return "books/all";
    }

    @GetMapping("/{id}")
    public String showBook(Model model, @PathVariable("id") int id){
        model.addAttribute("book", booksService.findById(id));
        if (booksService.findById(id).getOwner() != null)
            model.addAttribute("owner", booksService.findById(id).getOwner());
        else
            model.addAttribute("people", peopleService.findAll());

        model.addAttribute("book", booksService.findById(id));
        return "/books/book";
    }

    @GetMapping("/new")
    public String create(Model model){
        model.addAttribute("book", new Book());
        return "/books/new";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("book", booksService.findById(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String edit(@PathVariable("id") int id, @ModelAttribute("person") @Valid Book book,
                       BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "books/edit";
        booksService.update(id, book);
        return "redirect:/books/{id}";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "books/new";
        booksService.save(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/remove")
    public String deleteOwner(@PathVariable("id") int id){
        booksService.removeOwner(id);
        return "redirect:/books/{id}";
    }

    @PatchMapping("{id}/add")
    public String addOwner(@PathVariable("id") int id, @RequestParam("owner") int personId){
        System.out.println(personId + " " + peopleService.findById(personId));
        booksService.setOwner(id, peopleService.findById(personId));
        return "redirect:/books/{id}";
    }

}
