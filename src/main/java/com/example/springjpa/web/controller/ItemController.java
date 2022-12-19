package com.example.springjpa.web.controller;

import com.example.springjpa.domain.item.*;
import com.example.springjpa.service.ItemService;
import com.example.springjpa.web.dto.BookFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ItemController {
    private final ItemService itemService;

    /*@ModelAttribute("itemTypes")
    public ItemType[] itemTypes() {
        return ItemType.values();
    }*/

    @GetMapping("/items/add")
    public String itemAddFormView(Model model) {
        log.info("[GET] /items/add  =>  Item Add View");
        model.addAttribute("bookFormDto", new BookFormDto());
        return "/items/itemAddForm";
    }

    @PostMapping("/items/add")
    public String itemAdd(@Valid BookFormDto dto, BindingResult bindingResult) {
        log.info("[POST] /items/add  =>  Item Add");
        log.info("BookFormDto = {}", dto);
        log.info("BindingResult = {}", bindingResult);

        if(bindingResult.hasErrors()) {
            return "/items/itemAddForm";
        }

//        switch (ItemType.valueOf(dto.getItemType().name())) {
//            case ALBUM:
//                AlbumItem albumItem = new AlbumItem();
//                albumItem.setName(dto.getName());
//                albumItem.setPrice(dto.getPrice());
//                albumItem.setStockQuantity(dto.getStockQuantity());
//                albumItem.setArtist(dto.getArtist());
//                albumItem.setEtc(dto.getEtc());
//                itemService.addItem(albumItem);
//                break;
//
//            case BOOK:
//                BookItem bookItem = new BookItem();
//                bookItem.setName(dto.getName());
//                bookItem.setPrice(dto.getPrice());
//                bookItem.setStockQuantity(dto.getStockQuantity());
//                bookItem.setIsbn(dto.getIsbn());
//                bookItem.setAuthor(dto.getAuthor());
//                itemService.addItem(bookItem);
//                break;
//
//            case MOVIE:
//                MovieItem movieItem = new MovieItem();
//                movieItem.setName(dto.getName());
//                movieItem.setPrice(dto.getPrice());
//                movieItem.setStockQuantity(dto.getStockQuantity());
//                movieItem.setActor(dto.getActor());
//                movieItem.setDirector(dto.getDirector());
//                itemService.addItem(movieItem);
//                break;
//        }

        BookItem bookItem = new BookItem();
        bookItem.setName(dto.getName());
        bookItem.setPrice(dto.getPrice());
        bookItem.setStockQuantity(dto.getStockQuantity());
        bookItem.setIsbn(dto.getIsbn());
        bookItem.setAuthor(dto.getAuthor());
        itemService.addItem(bookItem);

        return "redirect:/items";
    }

    @GetMapping("/items")
    public String itemListView(Model model){
        log.info("[GET] /items  => Item List View");
        model.addAttribute("items", itemService.findItems());
        return "/items/itemList";
    }

    @GetMapping(value = "/items/{itemId}/edit")
    public String itemEditFormView(@PathVariable Long itemId, Model model) {
        log.info("[GET] /items/{}/edit  => Item Edit Form View", itemId);

        BookItem findItem = (BookItem)itemService.findItemOne(itemId);

        BookFormDto bookFormDto = new BookFormDto();
        bookFormDto.setId(findItem.getId());
        bookFormDto.setName(findItem.getName());
        bookFormDto.setPrice(findItem.getPrice());
        bookFormDto.setStockQuantity(findItem.getStockQuantity());
        bookFormDto.setAuthor(findItem.getAuthor());
        bookFormDto.setIsbn(findItem.getIsbn());

        model.addAttribute("bookFormDto", bookFormDto);
        return "/items/itemEditForm";
    }

    @PostMapping(value = "/items/{itemId}/edit")
    public String itemEdit(@Valid @ModelAttribute("bookFormDto") BookFormDto dto, BindingResult bindingResult) {
        log.info("[POST] /items/{}/edit  => Item Edit", dto.getId());
        log.info("BookFormDto = {}", dto);
        log.info("BindingResult = {}", bindingResult);

        if(bindingResult.hasErrors()) {
            return "/items/itemEditForm";
        }

        BookItem bookItem = new BookItem();
        bookItem.setId(dto.getId());
        bookItem.setName(dto.getName());
        bookItem.setPrice(dto.getPrice());
        bookItem.setStockQuantity(dto.getStockQuantity());
        bookItem.setAuthor(dto.getAuthor());
        bookItem.setIsbn(dto.getIsbn());

        itemService.addItem(bookItem);  // if id not null, em.merge()

        return "redirect:/items";
    }

    @PostMapping(value = "/items/{itemId}/remove")
    public String itemRemove(@PathVariable Long itemId) {
        log.info("[POST] /items/{}/remove  => Item Delete", itemId);
        itemService.removeItem(itemId);
        return "redirect:/items";
    }
}

