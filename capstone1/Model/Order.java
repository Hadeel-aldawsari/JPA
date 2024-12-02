package com.example.capstone1.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Check;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Check(constraints = "status='pending' or status='progressing' or status='delivered' or status='refund requested' or status='refunded'")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @NotEmpty(message = "order item should not be empty")
//    private ArrayList<OrderItem> cartItems;

    @NotEmpty(message = "order status should not be empty")
    @Pattern(regexp = "pending|progressing|delivered|refund requested|refunded",message = "order status should be pending|progressing|complete|refund")
    @Column(columnDefinition = "varchar(20) not null")
    private String Order_status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "timestamp not null")
    private LocalDate order_date;

   @NotNull(message = "user id should not be null")
   @Column(columnDefinition = "int not null")
    private Integer user_id;

    @NotNull(message = "total price should no be empty")
    @PositiveOrZero(message = "total price should be positive or zero")
    @Column(columnDefinition = "int not null")
    private Integer total_price;

    @NotNull(message = "merchantid should not be null")
    @Column(columnDefinition = "int not null")
    private Integer merchant_id;

    public Order(String Order_status, LocalDate order_date, Integer user_id, Integer total_price, Integer merchant_id) {
        this.Order_status = Order_status;
        this.order_date = order_date;
        this.user_id = user_id;
        this.total_price = total_price;
        this.merchant_id = merchant_id;
    }


    public Order() {

    }
}
