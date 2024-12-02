package com.example.capstone1.Controller;

import com.example.capstone1.Service.AdminService;
import com.example.capstone1.Model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    //This end point update the status depending on the sequence of it
    @PutMapping("/update-order/{adminid}/{orderId}")
    public ResponseEntity updateOrderStatus(@PathVariable Integer adminid, @PathVariable Integer orderId) {
        return ResponseEntity.status(200).body(adminService.updateOrderStatus(adminid,orderId));
    }

    // end point to get order with request of refund
    @GetMapping("/get-refund-requests/{adminid}")
    public ResponseEntity getRefundRequests(@PathVariable Integer adminid) {
    return ResponseEntity.status(200).body(adminService.getRefundRequest(adminid));
    }

    // end point to conform refund request
    @PutMapping("/refund-confirm/{userid}/{orderid}")
    public ResponseEntity confirmRefundRequest(
            @PathVariable Integer userid,
            @PathVariable Integer orderid) {
        return ResponseEntity.status(200).body(adminService.ConfirmRefundRequest(userid,orderid));
    }
}
