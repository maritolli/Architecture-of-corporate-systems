package com.example.library.controller;

import com.example.library.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LibraryController {

    @Autowired private AuthorService authorService;
    @Autowired private BookService bookService;
    @Autowired private BorrowingService borrowingService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("borrowings", borrowingService.getAllBorrowings());
        return "index";
    }

    // Заказ книги
    @PostMapping("/order")
    public String orderBook(@RequestParam Long readerId, @RequestParam Long bookId, RedirectAttributes redirectAttributes) {
        try {
            bookService.orderBook(readerId, bookId);
            redirectAttributes.addFlashAttribute("message", "✅ Книга успешно заказана!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "❌ " + e.getMessage());
        }
        return "redirect:/";
    }

    // Возврат книги
    @PostMapping("/return/{id}")
    public String returnBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            bookService.returnBook(id);
            redirectAttributes.addFlashAttribute("message", "✅ Книга возвращена!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "❌ " + e.getMessage());
        }
        return "redirect:/";
    }
}