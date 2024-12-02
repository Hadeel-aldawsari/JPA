package com.example.capstone1.Controller;


import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.Order;
import com.example.capstone1.Model.User;
import com.example.capstone1.Service.OrderService;
import com.example.capstone1.Service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService orderService;

    //here to solve question 12
    private final PurchaseService purchaseService;


    // Endpoint لشراء منتج باستخدام @PathVariable
    @PostMapping("/buy/{userId}/{productId}/{merchantId}")
    public ResponseEntity<String> buyProduct(@PathVariable Integer userId,
                                             @PathVariable Integer productId,
                                             @PathVariable Integer merchantId) {

        String result = purchaseService.buyProduct(userId, productId, merchantId);

        if (result.contains("successfully")) {
            return ResponseEntity.status(200).body(result);
        } else {
            return ResponseEntity.status(400).body(result);
        }
    }

    @GetMapping("/get-all-order")
    public ResponseEntity getAllOrders(){
        return ResponseEntity.status(200).body(orderService.getAllOrders());
    }

    @PostMapping("/create/{userid}/{merchantId}")
    public ResponseEntity createOrder(@PathVariable Integer userid,@PathVariable Integer merchantId) {
        return ResponseEntity.status(200).body(orderService.createOrder(userid, merchantId));
    }


    @GetMapping("/get-order-by-id/{orderis}")
    public ResponseEntity getOrderById(@PathVariable Integer orderid){
        if(orderService.getOrderById(orderid)==null)
            return ResponseEntity.status(400).body(new ApiResponse("order not found"));
        return ResponseEntity.status(200).body(orderService.getOrderById(orderid));
    }

    @GetMapping("/get-order-by-status/{status}")
    public ResponseEntity getOrdersByStatus(String status){
        if(orderService.getOrdersByStatus(status)==null)
            return ResponseEntity.status(400).body(new ApiResponse("no order found with this status"));
        return ResponseEntity.status(200).body(orderService.getOrdersByStatus(status));
    }









}
