package com.example.demo.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
@Table(name = "persons")
@NoArgsConstructor
@Getter
@Setter
public class Person {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int person_id;

    @Column(name = "person_fio")
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длиной")
    private String fio;


    @Column(name = "person_year")
    @Min(value = 1900, message = "Не меньше чем 1900")
    private int year;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person(String fio, int year) {
        this.fio = fio;
        this.year = year;
    }

    @Override
    public String toString() {
        return "Person{" +
                "person_id=" + person_id +
                ", person='" + fio + '\'' +
                ", year=" + year +
                ", books=" + books +
                '}';
    }

    public Person(String fio, int year, List<Book> books) {
        this.fio = fio;
        this.year = year;
        this.books = books;
    }
}
