package com.example.capstone1.Service;

import com.example.capstone1.Model.Order;
import com.example.capstone1.Model.OrderItem;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import com.example.capstone1.Repository.OrderRepository;
import com.example.capstone1.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
   private final OrderRepository orderRepository;
   private final UserRepository userRepository;
    private final UserService userService;
    private final MerchantStockService merchantStockService;
    private final CartService cartService;

    public String createOrder(Integer userid, Integer merchantId) {
        //check user
        User user = userRepository.getById(userid);
        if (user == null) {
            return "User not found.";
        }

        if(!merchantStockService.isMerchantsStockExist(merchantId))
            return "merchantId not found";



//         if(cartService.getMyCart(userid)==null || cartService.getMyCart(userid).isEmpty())
//             return "sorry your cart is empty";
        //get cart item
//        ArrayList<OrderItem> cartItems = cartService.getMyCart(userid);
//        if (cartItems == null || cartItems.isEmpty()) {
//            return "Your cart is empty";
//        }

        if(cartService.getMyCart(userid)==null)return "Your cart is empty";

        //get total
        Integer totalPrice = cartService.getCartTotal(userid);

        //check user balance
        if (!userService.checkBalance(userid, totalPrice)) {
            return "User does not have enough balance.";
        }

        //deducted user balance
        userService.deductedBalance(userid, totalPrice);

        // reduce stock
        for (OrderItem item : cartService.getMyCart(userid)) {
            merchantStockService.reduceStock(merchantId, item);
        }

        ArrayList<OrderItem>copyCartItem=new ArrayList<>();
        for(OrderItem item:cartService.getMyCart(userid)){
            copyCartItem.add(item);
        }

        Order newOrder = new Order(
                "pending",
                LocalDate.now(),  // تاريخ الطلب يكون اليوم
                userid,
                totalPrice,
                merchantId
        );

        //add order to  order recourds
        orderRepository.save(newOrder);

        //clear cart
        cartService.clearCart(userid);
        return "Order created successfully";
    }



    public Order getOrderById(Integer orderId) {
        for (Order order : orderRepository.findAll()) {
            if (order.getId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }


    public List<Order>getOrdersByStatus(String status){
       List<Order>orderByStatus=new ArrayList<>();
        for (Order order:orderRepository.findAll()){
            if(order.getOrder_status().equalsIgnoreCase(status))
                orderByStatus.add(order);
        }
        return orderByStatus;
    }


    public List<Order> getUserOrder(Integer userid){
        if(userService.getUserByID(userid)==null)return null;
         List<Order> orderList=new ArrayList<>();

         for (Order o:orderRepository.findAll()){
             if (o.getUser_id().equals(userid)){
                 orderList.add(o);
             }
         }
         return orderList;
    }

}