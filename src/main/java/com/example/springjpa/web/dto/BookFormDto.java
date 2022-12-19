package com.example.springjpa.web.dto;

import com.example.springjpa.domain.item.ItemType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class BookFormDto {
    private Long id;

    @NotEmpty(message = "상품명을 입력해주세요.")
    private String name;

    @NotNull(message = "상품가격을 입력해주세요.")
    private Integer price;

    @NotNull(message = "상품재고를 입력해주세요.")
    private Integer stockQuantity;

    private ItemType itemType;

    // album
    //private String artist;
    //private String etc;

    // book
    private String author;
    private String isbn;

    // movie
    //private String director;
    //private String actor;
}


