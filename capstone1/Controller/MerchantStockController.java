package com.example.capstone1.Controller;

import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchant-stock")
public class MerchantStockController {

    private final MerchantStockService merchantStockService;

    @GetMapping("/get-all")
    public ResponseEntity getAll() {
        return ResponseEntity.status(200).body(merchantStockService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
       if( merchantStockService.add(merchantStock))
        return ResponseEntity.status(200).body(new ApiResponse("added successfully"));
       return ResponseEntity.status(400).body(new ApiResponse("Unsuccessfully,enter valied data"));
    }

    @PutMapping("/update/{merchantStockId}")
    public ResponseEntity update(@PathVariable Integer merchantStockId, @RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (merchantStockService.update(merchantStockId, merchantStock))
            return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("update Unsuccessfully"));
    }

    @DeleteMapping("/delete/{merchantStockId}")
    public ResponseEntity delete(@PathVariable Integer merchantStockId) {
        if (merchantStockService.delete(merchantStockId))
            return ResponseEntity.status(200).body(new ApiResponse("deleted successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("delete Unsuccessfully"));
    }
}
