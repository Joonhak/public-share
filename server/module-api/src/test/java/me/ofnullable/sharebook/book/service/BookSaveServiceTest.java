package me.ofnullable.sharebook.book.service;

import me.ofnullable.sharebook.account.service.AccountFindService;
import me.ofnullable.sharebook.book.domain.Book;
import me.ofnullable.sharebook.book.repository.BookRepository;
import me.ofnullable.sharebook.common.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static me.ofnullable.sharebook.account.utils.AccountUtils.buildNormalAccount;
import static me.ofnullable.sharebook.book.utils.BookUtils.buildBookWithId;
import static me.ofnullable.sharebook.book.utils.BookUtils.buildSaveBookRequest;
import static me.ofnullable.sharebook.book.utils.CategoryUtils.buildCategory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class BookSaveServiceTest {

    @InjectMocks
    private BookSaveService bookSaveService;

    @Mock
    private AccountFindService accountFindService;

    @Mock
    private CategoryFindService categoryFindService;

    @Mock
    private BookRepository bookRepository;

    @Test
    @DisplayName("도서 등록")
    void save_book() throws NoSuchFieldException, IllegalAccessException {
        var book = buildBookWithId();

        given(accountFindService.findById(any(Long.class)))
                .willReturn(buildNormalAccount());
        given(categoryFindService.findCategoryById(any(Long.class)))
                .willReturn(buildCategory());
        given(bookRepository.save(any(Book.class)))
                .willReturn(book);

        var result = bookSaveService.save(buildSaveBookRequest(), buildNormalAccount());

        assertEquals(result, book.getId());
    }

    @Test
    @DisplayName("존재하지 카테고리로 도서 등록하는 경우 - ResourceNotFoundException")
    void save_book_with_invalid_category() {
        given(accountFindService.findById(any(Long.class)))
                .willReturn(buildNormalAccount());
        given(categoryFindService.findCategoryById(any(Long.class)))
                .willThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> bookSaveService.save(buildSaveBookRequest(), buildNormalAccount()));
    }

}
