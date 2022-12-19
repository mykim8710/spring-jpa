package com.example.springjpa.service;

import com.example.springjpa.domain.item.BookItem;
import com.example.springjpa.domain.item.Item;
import com.example.springjpa.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ItemService {
    private final ItemRepository itemRepository;

    // 상품 등록
    @Transactional
    public Long addItem(Item item) {
        itemRepository.save(item);
        return item.getId();
    }

    // 상품 수정 : 변경감지(Dirty Checking)
    @Transactional
    public void editItem(Long itemId, String name, int price, int stockQuantity, String author, String isbn) {
        BookItem findItem = (BookItem)itemRepository.findOneById(itemId).orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다."));

        // 실무에선 Setter X, 의미있는 메서드를 정의
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
        findItem.setAuthor(author);
        findItem.setIsbn(isbn);
    }


    // 상품 전체 조회
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    // 상품 단건 조회
    public Item findItemOne(Long itemId) {
        return itemRepository.findOneById(itemId).orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다."));
    }

    // 상품 삭제
    @Transactional
    public void removeItem(Long itemId) {
        itemRepository.deleteOneById(itemId);
    }
}
