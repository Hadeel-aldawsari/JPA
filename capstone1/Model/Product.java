package com.example.capstone1.Model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Product name should not be empty")
    @Size(min=4,message = "Product name should be more than 3 length long) ")
    @Column(columnDefinition = "varchar(30) not null")
    private String name;

    @NotNull(message = "Product price should bot be empty")
    @PositiveOrZero(message = "Product price should be zero or positive")
    @Column(columnDefinition = "int not null")
    private Integer price;

    @NotNull(message = "category id not null")
    @Column(columnDefinition = "int not null")
    private Integer category_id;



}
