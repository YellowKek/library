package com.example.demo.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "books")
@NoArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int book_id;

    @Column(name = "book_name")
    @NotEmpty(message = "Название не должно быть пустым")
    @Size(min = 2, max = 100, message = "Название должно быть от 2 до 100 символов длиной")
    private String name;

    @Column(name = "author")
    @NotEmpty(message = "Автор не должно быть пустым")
    @Size(min = 2, max = 100, message = "Автор должно быть от 2 до 100 символов длиной")
    private String author;

    @Column(name = "book_year")
    @Min(value = 1000, message = "Год должен быть больше 1000!")
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person owner;

    public Book(int book_id, String name, String author, int year, Person owner) {
        this.book_id = book_id;
        this.name = name;
        this.author = author;
        this.year = year;
        this.owner = owner;
    }
}
