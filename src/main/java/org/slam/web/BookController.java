package org.slam.web;

import lombok.AllArgsConstructor;
import org.slam.dto.common.Paginator;
import org.slam.service.book.BookSelectService;
import org.slam.service.history.HistorySelectService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final HistorySelectService historySelectService;
    private final BookSelectService bookSelectService;

    @GetMapping("/{id}")
    public String findBookDetail(@PathVariable Long id, Model model, Authentication auth) {
        model.addAttribute("book", bookSelectService.findBookDetail(id, auth));
        return "book/detail";
    }

    @GetMapping("/{bookId}/histories")
    public String findBookHistory(@PathVariable Long bookId, @ModelAttribute Paginator paginator, Authentication auth, Model model) {
        paginator.setUsername(auth.getName());
        model.addAttribute("detail", historySelectService.findHistoryDetailsByBookId(bookId, paginator));
        return "book/histories";
    }

}