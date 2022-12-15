package com.example.springjpa.service;

import com.example.springjpa.domain.item.AlbumItem;
import com.example.springjpa.domain.item.BookItem;
import com.example.springjpa.domain.item.Item;
import com.example.springjpa.exception.NotEnoughStockException;
import com.example.springjpa.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemServiceTest {
    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("상품등록 테스트")
    @Transactional
    void 상품등록_테스트() throws Exception {
        // given
        BookItem bookItem = new BookItem();
        bookItem.setName("book");
        bookItem.setPrice(1000);
        bookItem.setIsbn("isbn");
        bookItem.setAuthor("author");
        bookItem.setStockQuantity(100);

        // when
        Long addItemId = itemService.addItem(bookItem);
        System.out.println("addItemId = " + addItemId);

        // then
        assertThat(bookItem).isEqualTo(itemRepository.findById(addItemId).get());
    }

    @Test
    @DisplayName("상품 재고예외 테스트")
    @Transactional
    void 상품재고_예외_테스트() throws Exception {
        // given
        AlbumItem albumItem = new AlbumItem();
        albumItem.setStockQuantity(100);

        // when
        Long addItemId = itemService.addItem(albumItem);

        // then
        assertThrows(NotEnoughStockException.class, () -> {
            int quantity = 101;
            Item findItem = itemRepository.findById(addItemId).get();
            findItem.removeStock(quantity);
        });
    }
}