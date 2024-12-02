package com.example.capstone1.Service;

import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final UserService userService;
    private final ProductService productService;
    private final MerchantStockService merchantStockService;

    public String buyProduct(Integer userId, Integer productId, Integer merchantId) {
        User user = userService.getUserByID(userId);
        if (user == null) {
            return "User not found";
        }

        Product product = productService.getProductByID(productId);
        if (product == null) {
            return "Product with id this not found";
        }

        if(!merchantStockService.isMerchantsStockExist(merchantId)){
            return "not found merchant";
        }

        int stockQuantity = merchantStockService.getStock(merchantId, productId);
        if (stockQuantity <= 0) {
            return "Product is out of stock";
        }

        if (user.getBalance() < product.getPrice()) {
            return "There is no enough balance to buy the product";
        }

        userService.deductedBalance(userId, product.getPrice());
       // merchantStockService.reduceStock(merchantId, productId);

        return "Product purchased successfully!";
    }
}
