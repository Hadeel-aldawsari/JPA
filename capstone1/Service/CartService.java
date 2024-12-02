package com.example.capstone1.Service;

import com.example.capstone1.Model.OrderItem;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import com.example.capstone1.Repository.OrderItemRepository;
import com.example.capstone1.Service.MerchantStockService;
import com.example.capstone1.Service.ProductService;
import com.example.capstone1.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final UserService userService;
    private final ProductService productService;
    private final MerchantStockService merchantStockService;
    private final OrderItemRepository orderItemRepository;

    public String addToCart(Integer userId,Integer merchantId,OrderItem item) {
        User user = userService.getUserByID(userId);
        if (user == null) {
            return "User not found.";
        }

        // check if the product exist in the merchant
        if(!merchantStockService.isInStock(merchantId,item.getId())){
            return "there is no product stock in the merchant";
        }


      //check the quantity
        if (merchantStockService.getStock(merchantId, item.getId()) < item.getQuantity()) {
            return "Not enough stock for product " + item.getId();
        }
        if (merchantStockService.getStock(merchantId, item.getId())==0) {
            return "Out Of Stock" + item.getId();
        }

        orderItemRepository.save(item);
        return "Product added to cart successfully!";
    }




  //get customer cart
    public List<OrderItem> getMyCart(Integer userId) {
        User user = userService.getUserByID(userId);
        if (user == null) {
            return null;
        }
        List<OrderItem>items=new ArrayList<>();
       for (OrderItem o:orderItemRepository.findAll()){
           if(o.getUserid().equals(userId))
               items.add(o);
       }return items;

    }


    public Integer getCartTotal(Integer userId) {
        //check user exist
        User user = userService.getUserByID(userId);
        if (user == null) {
            return 0;
        }

        Integer  total = 0;

        for (OrderItem item : getMyCart(userId)) {
            Product product = productService.getProductByID(item.getId());
            if (product != null) {
                total += product.getPrice() * item.getQuantity();
            }
        }
        return total;
    }


    public Boolean clearCart(Integer userId) {
        User user = userService.getUserByID(userId);
        if (user != null) {
            for (OrderItem orderItem:getMyCart(userId)){
                orderItemRepository.delete(orderItem);
            }
            return true;
        }
        return false;
    }

}
