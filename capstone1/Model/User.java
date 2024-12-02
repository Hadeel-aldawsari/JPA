package com.example.capstone1.Model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Check;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@Check(constraints="role='admin' or role='customer'")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "User username should not be empty")
    @Size(min=6,message = "user name should be  more than 5 length long")
    @Pattern(regexp = "^(?=.*[A-Za-z])[A-Za-z0-9._]{4,20}$", message = "Username must contain at least one letter and can only contain alphanumeric characters, dots, or underscores.")
    @Column(columnDefinition ="varchar(30) not null unique")
    private String username;

    @NotEmpty(message = "password should not be empty")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{7,}$",
            message = "Password must contain at least one digit [0-9].\n" +
                    "Password must contain at least one lowercase Latin character [a-z].\n" +
                    "Password must contain at least one uppercase Latin character [A-Z].\n" +
                    "Password must contain at least one special character like ! @ # & ( ).\n" +
                    "Password must contain a length of at least 7 characters." )
    @Column(columnDefinition ="varchar(30) not null ")
    private String password;

    @NotEmpty(message = "email should not be empty")
    @Email(message = "enter valid email")
    @Column(columnDefinition ="varchar(10) not null unique")
    private String email;

    @NotEmpty(message = "role should not be empty")
    @Pattern(regexp = "admin|customer",message = "role have to be Admin or Customer")
    @Column(columnDefinition ="varchar(8) not null")
    private String role;


    @NotNull(message = "balance should not be null")
    @PositiveOrZero(message = "balance should be zero or positive")
    @Column(columnDefinition ="int not null")
    private Integer balance;

//how can i fix this ?
//    private ArrayList<Order> orders;
//    private ArrayList<OrderItem> cartItems;







}
