package com.example.capstone1.Model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "userid should not be null")
    @Column(columnDefinition = "int not null")
    private Integer userid;

    @NotNull(message = "quantity should not be null")
    @PositiveOrZero(message = "quantity should not positive number")
    @Min(value = 1,message = "quantity at least should be 1")
    @Column(columnDefinition = "int not null")
    private int quantity;
}
