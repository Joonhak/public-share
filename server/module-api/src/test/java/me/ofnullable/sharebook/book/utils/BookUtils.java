package me.ofnullable.sharebook.book.utils;

import me.ofnullable.sharebook.account.domain.Account;
import me.ofnullable.sharebook.account.utils.AccountUtils;
import me.ofnullable.sharebook.book.domain.Book;
import me.ofnullable.sharebook.book.domain.Category;
import me.ofnullable.sharebook.book.dto.book.SaveBookRequest;
import org.springframework.data.domain.Page;

import java.util.List;

import static me.ofnullable.sharebook.utils.PageRequestUtils.buildPage;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookUtils {

    private static Account account = AccountUtils.buildNormalAccount();

    private static Book book = Book.builder()
            .title("test book")
            .author("author")
            .publisher("test")
            .description("book for test!")
            .owner(account)
            .imageUri("/asd")
            .build();

    private static Category category = Category.of("운영체제");

    public static Book buildBook() {
        book.setCategory(category);
        return book;
    }

    public static Book buildBookWithId() throws NoSuchFieldException, IllegalAccessException {
        var book = buildBook();
        var field = book.getClass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(book, 1L);
        return book;
    }

    public static SaveBookRequest buildSaveBookRequest() {
        return SaveBookRequest.builder()
                .title("title")
                .author("author")
                .description("description")
                .publisher("publisher")
                .imageUri("/image/url")
                .categoryId(1L)
                .build();
    }

    private static List<Book> buildBookList() {
        return List.of(book, book, book);
    }

    public static Page<Book> buildNormalPageBook() {
        return buildPage(buildBookList(), 20);
    }

    public static Page<Book> buildIrregularPageBook() {
        return buildPage(buildBookList(), 100);
    }

    public static void equalBook(Book result, Book target) {
        assertEquals(result.getTitle(), target.getTitle());
        assertEquals(result.getAuthor(), target.getAuthor());
        assertEquals(result.getPublisher(), target.getPublisher());
        assertEquals(result.getDescription(), target.getDescription());
        assertEquals(result.getImageUri(), target.getImageUri());
        assertEquals(result.getOwner(), target.getOwner());
        assertEquals(result.getCategory(), target.getCategory());
    }

}
