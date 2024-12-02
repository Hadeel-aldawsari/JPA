package com.example.capstone1.Controller;

import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/get-all")
    public ResponseEntity getAll() {
        if(productService.getAll()==null || productService.getAll().isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("there is no product"));
        return ResponseEntity.status(200).body(productService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if(productService.add(product))
        return ResponseEntity.status(200).body(new ApiResponse("added successfully"));
        return ResponseEntity.status(400).body(new ApiResponse("catogery not found"));
    }

    @PutMapping("/update/{productid}")
    public ResponseEntity update(@PathVariable Integer productid, @RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (productService.update(productid, product))
            return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("update Unsuccessfully"));
    }

    @DeleteMapping("/delete/{productid}")
    public ResponseEntity delete(@PathVariable Integer productid) {
        if (productService.delete(productid))
            return ResponseEntity.status(200).body(new ApiResponse("deleted successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("delete Unsuccessfully"));
    }
}
