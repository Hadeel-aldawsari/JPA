package com.example.capstone1.Controller;

import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.OrderItem;
import com.example.capstone1.Service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping("/add-to-cart/{userid}/{merchantid}")
    public ResponseEntity addToCart(@PathVariable Integer userid, @PathVariable Integer merchantid, @RequestBody @Valid OrderItem Orderitem, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        String result= cartService.addToCart(userid,merchantid,Orderitem);
        return ResponseEntity.status(200).body(result);

    }

    @GetMapping("/get-my-cart/{userid}")
    public ResponseEntity getCart(@PathVariable Integer userid){
      if(cartService.getMyCart(userid)==null ||cartService.getMyCart(userid).isEmpty())
          return ResponseEntity.status(400).body(new ApiResponse("There is no item on this user cart"));
      return ResponseEntity.status(200).body(cartService.getMyCart(userid));
    }


    @GetMapping("/get-total-cart/{userid}")
    public ResponseEntity getCartTotal(@PathVariable Integer userid){
        return ResponseEntity.status(200).body(cartService.getCartTotal(userid));
    }

    @PutMapping("/clear/{userid}")
    public ResponseEntity clearCart(@PathVariable Integer userid){
        if(cartService.clearCart(userid))
            return ResponseEntity.status(200).body(new ApiResponse("cleared successfully"));
        return ResponseEntity.status(400).body(new ApiResponse("cleared Unsuccessful"));

                }
}

