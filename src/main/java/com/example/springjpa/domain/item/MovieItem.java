package com.example.springjpa.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
@DiscriminatorColumn(name = "M")
public class MovieItem extends Item {
    private String director;
    private String actor;
}
