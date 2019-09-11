package org.slam.publicshare.book.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slam.publicshare.book.domain.Book;
import org.slam.publicshare.book.exception.NoSuchBookException;
import org.slam.publicshare.book.repository.BookRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.slam.publicshare.book.utils.BookUtils.*;
import static org.slam.publicshare.book.utils.CategoryUtils.buildCategory;
import static org.slam.publicshare.common.utils.PageRequestUtils.buildPageable;

@ExtendWith(SpringExtension.class)
public class BookFindServiceTest {

    @InjectMocks
    private BookFindService bookFindService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryFindService categoryFindService;

    private Book book = buildBook();

    @Test
    @DisplayName("도서가 존재하는 경우 정상작동")
    public void find_book_by_id() {
        given(bookRepository.findById(any(Long.class)))
                .willReturn(Optional.of(book));

        var result = bookFindService.findById(any(Long.class));

        equalBook(result, book);
    }

    @Test
    @DisplayName("도서가 존재하지 않는 경우 - NoSuchBookException")
    public void find_book_by_invalid_id() {
        given(bookRepository.findById(any(Long.class)))
                .willReturn(Optional.empty());

        assertThrows(NoSuchBookException.class, () -> bookFindService.findById(any(Long.class)));
    }

    @Test
    @DisplayName("도서 리스트 페이지 요청")
    public void book_list_pagination() {
        given(bookRepository.findAll(any(Pageable.class)))
                .willReturn(buildNormalPageBook());

        var result = bookFindService.findAll(null, buildPageable(10));

        assertEquals(result.getSize(), 10);
    }

    @Test
    @DisplayName("도서 제목 검색 요청")
    public void book_list_pagination_by_title() {
        given(bookRepository.findByTitleContainingIgnoreCase(any(String.class), any(Pageable.class)))
                .willReturn(buildNormalPageBook());

        var result = bookFindService.findAll("test title", buildPageable(10));

        assertEquals(result.getSize(), 10);
    }

    @Test
    @DisplayName("비정상적인 사이즈로 요청시 요청 사이즈 10으로 고정")
    public void book_list_irregular_size_pagination() {
        given(bookRepository.findByTitleContainingIgnoreCase(any(String.class), any(Pageable.class)))
                .willReturn(buildIrregularPageBook());

        var result = bookFindService.findAll("test title", buildPageable(100));

        assertEquals(result.getSize(), 12);
    }

    @Test
    @DisplayName("카테고리 내 도서 페이지 요청")
    public void find_book_by_category() {
        given(categoryFindService.findByName(any(String.class)))
                .willReturn(buildCategory());
        given(bookRepository.findAllByCategoryName(any(String.class), any(Pageable.class)))
                .willReturn(buildNormalPageBook());

        var result = bookFindService.findAllByCategory("운영체제", buildPageable(10));

        assertEquals(result.getSize(), 10);
    }

}