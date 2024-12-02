package com.example.capstone1.Service;

import com.example.capstone1.Model.Order;
import com.example.capstone1.Model.OrderItem;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import com.example.capstone1.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ProductService productService;
    protected final MerchantStockService merchantStockService;

    private final OrderService orderService;
    private final UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void add(User user) {
        userRepository.save(user);
    }


    public Boolean update(Integer id, User updatedUser) {
        User existingUser = userRepository.getById(id);
        if (existingUser != null) {
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setBalance(updatedUser.getBalance());
            existingUser.setRole(updatedUser.getRole());
         userRepository.save(existingUser);
            return true;
        }
        return false;
    }


    public Boolean delete(Integer id) {
        for (User user : userRepository.findAll()) {
            if (user.getId().equals(id)) {
               userRepository.delete(user);
                return true;
            }
        }
        return false;
    }


    public User getUserByID(Integer id){
        for (User u:userRepository.findAll()){
            if (u.getId().equals(id))
                return u;
        }
        return null;
    }




    public String refundOrder(Integer userid,Integer orderid){
        if(getUserByID(userid)==null)
            return "not found user";
        if(orderService.getUserOrder(userid)==null)
          return"there is no order created";
        for (Order o:orderService.getUserOrder(userid)){
           if(o.getId().equals(orderid) ){
               if(o.getOrder_status().equalsIgnoreCase("Delivered")) {
                   o.setOrder_status("refund requested");
               }else {
                   return "refund request should be on  Delivered order only";
               }
            }
        }
        return "refund request created successfully";
    }


public Boolean addBalance(Integer userid,Integer balance){
        User user=getUserByID(userid);
        if (user!=null && balance>0){
            user.setBalance(user.getBalance()+balance);
            return true;
        }
        return false;
}

    public boolean checkBalance(Integer userid,Integer price){
        if(getUserByID(userid).getBalance()>=price)return true;
        return false;
    }

    public void deductedBalance(Integer userid ,Integer price){
        getUserByID(userid).setBalance((int) (getUserByID(userid).getBalance()- price));

    }

    public void rechargeBalance(Integer userid,Integer balance){
        getUserByID(userid).setBalance(getUserByID(userid).getBalance()+ balance);
    }


//    public void addOrderToUserList(Integer userid,Order order){
//
//
//    }








}
