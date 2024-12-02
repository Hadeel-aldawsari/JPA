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
public class MerchantStock {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "MerchantStock productid should not be empty")
    @Column(columnDefinition = "int not null")
    private Integer productid;

    @NotEmpty(message = "MerchantStock merchantid should not be empty")
    @Column(columnDefinition = "int not null")
    private Integer merchantid;

    @NotNull(message = "stock should not be null")
    @PositiveOrZero(message = "stock should be zero or positive")
    @Min(value = 11,message = "stock should be  more than 10 at start")
    @Column(columnDefinition = "int not null")
    private int stock;
}
