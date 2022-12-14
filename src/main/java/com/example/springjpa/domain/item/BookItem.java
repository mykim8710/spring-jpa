package com.example.springjpa.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
@DiscriminatorColumn(name = "B")
public class BookItem extends Item {
    private String author;
    private String isbn;
}
