package com.example.capstone1.Controller;

import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.Category;
import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchant")
public class MerchantControlller {
    private final MerchantService merchantService;


    @GetMapping("/get-all")
    public ResponseEntity getAll(){
        return ResponseEntity.status(200).body(merchantService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid Merchant merchant, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        merchantService.add(merchant);
        return ResponseEntity.status(200).body(new ApiResponse("added successfully"));
    }

    @PutMapping("/update/{merchantid}")
    public ResponseEntity update(@PathVariable Integer merchantid,@RequestBody @Valid Merchant merchant,Errors errors ){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if(merchantService.update(merchantid,merchant))
            return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("update Unsuccessfully"));
    }


    @DeleteMapping("delete/{merchantid}")
    public ResponseEntity delete(@PathVariable Integer merchantid ){
        if(merchantService.delete(merchantid))
            return ResponseEntity.status(200).body(new ApiResponse("deleted successfully"));

        return ResponseEntity.status(400).body(new ApiResponse(" delete Unsuccessfully"));
    }



}
