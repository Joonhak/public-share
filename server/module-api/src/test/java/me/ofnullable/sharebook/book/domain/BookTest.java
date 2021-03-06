package me.ofnullable.sharebook.book.domain;

import me.ofnullable.sharebook.account.domain.Account;
import me.ofnullable.sharebook.account.domain.Email;
import me.ofnullable.sharebook.book.domain.converter.BookStatusConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookTest {

    private final Account account = Account.builder()
            .email(Email.of("test1@asd.com"))
            .name("test")
            .password("{noop}test")
            .build();

    private final Book book = Book.builder()
            .title("test book")
            .author("author")
            .publisher("test")
            .description("book for test!")
            .owner(account)
            .imageUri("")
            .build();

    private Category category = Category.of("운영체제");

    @Test
    @DisplayName("Builder로 인스턴스 생성")
    void book_builder() {
        assertEquals(book.getTitle(), "test book");
        assertEquals(book.getAuthor(), "author");
        assertEquals(book.getPublisher(), "test");
        assertEquals(book.getDescription(), "book for test!");
    }

    @Test
    @DisplayName("도서 카테고리 추가")
    void book_set_category() {
        book.setCategory(category);
        assertEquals(book.getCategory().getName(), category.getName());
    }

    @Test
    @DisplayName("도서 상태변경")
    void change_book_status() {
        var available = BookStatus.of(1);
        assertEquals(available, BookStatus.AVAILABLE);

        var unavailable = BookStatus.of(2);
        assertEquals(unavailable, BookStatus.UNAVAILABLE);

        book.toAvailable();
        assertEquals(book.getStatus(), BookStatus.AVAILABLE);

        book.toUnavailable(1L);
        assertEquals(book.getStatus(), BookStatus.UNAVAILABLE);
    }

    @Test
    @DisplayName("존재하지 않는 도서 상태 - IllegalArgumentException")
    void invalid_book_status() {
        assertThrows(IllegalArgumentException.class, () -> BookStatus.of(3));
    }

    @Test
    @DisplayName("BookStatus <-> Database column 컨버팅")
    void converter() {
        var converter = new BookStatusConverter();

        var column = converter.convertToDatabaseColumn(BookStatus.AVAILABLE);
        assertEquals(column, Integer.valueOf(1));

        var attribute = converter.convertToEntityAttribute(column);
        assertEquals(attribute, BookStatus.AVAILABLE);
    }

}
