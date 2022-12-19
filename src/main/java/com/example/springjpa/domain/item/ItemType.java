package com.example.springjpa.domain.item;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ItemType {
    ALBUM("ALBUM"),
    BOOK("BOOK"),
    MOVIE("MOVIE")
    ;

    private final String description;

    ItemType(String description) {
        this.description = description;
    }
}