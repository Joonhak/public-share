package org.slam.web;

import lombok.AllArgsConstructor;
import org.slam.service.book.BookSelectService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookSelectService bookSelectService;

    @GetMapping("/{id}")
    public String getBookDetail(@PathVariable Long id, Model model, Authentication auth) {
        if (auth != null) model.addAttribute("book", bookSelectService.selectBookDetail(id, auth.getName()));
        else model.addAttribute("book", bookSelectService.selectBookDetail(id, null));
        return "book/detail";
    }

}