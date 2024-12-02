package com.example.capstone1.Service;

import com.example.capstone1.Model.Order;
import com.example.capstone1.Model.OrderItem;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final  UserService userService;
    private  final CartService cartService;
private final MerchantStockService merchantStockService;

   //update order status

    public String updateOrderStatus(Integer adminid,Integer orderId) {
        if(!userService.getUserByID(adminid).getRole().equalsIgnoreCase("admin"))
            return "No permeation ,only admin can update status";
        Order order =orderRepository.getById(orderId);
        if (order != null) {
            String status = order.getOrder_status();

            if (status.equals("pending")) {
                order.setOrder_status("progressing");
                return "Order status changed to progressing";
            } else if (status.equals("progressing")) {
                order.setOrder_status("delivered");
                return "Order status changed to delivered";
            }
        }
        return "order not found";
    }


    public List<Order> getRefundRequest(Integer adminid){
        if(userService.getUserByID(adminid).getRole().equalsIgnoreCase("admin")){
     return orderService.getOrdersByStatus("refund requested");
        }
       return null;
    }


    public String ConfirmRefundRequest(Integer adminid,Integer orderid){
        if(!userService.getUserByID(adminid).getRole().equalsIgnoreCase("admin"))
            return "No permeation ,only admin can update status";

        Order order =orderRepository.getById(orderid);

        if(order==null)return "order not found";
           if(order.getOrder_status().equalsIgnoreCase("refund requested")){
               List<OrderItem>orderItems= cartService.getMyCart(order.getUser_id());
               for (OrderItem item:orderItems) {
                   merchantStockService.restock(order.getMerchant_id(),item.getId(), item.getQuantity());
               }
               userService.rechargeBalance(order.getUser_id(),order.getTotal_price());
               order.setOrder_status("refunded");
               return "Refund Confirmed successfully";
           }
        return "confirm only for refund requested order";
        }


}
