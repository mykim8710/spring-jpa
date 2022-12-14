package com.example.springjpa.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;

@Getter @Setter
@Entity
@DiscriminatorColumn(name = "A")
public class AlbumItem extends Item {
    private String artist;
    private String etc;
}
