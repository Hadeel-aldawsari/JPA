package com.example.capstone1.Controller;

import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.Category;
import com.example.capstone1.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping("/get-all")
    public ResponseEntity getAll(){
        return ResponseEntity.status(200).body(categoryService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid Category category, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        categoryService.add(category);
        return ResponseEntity.status(200).body(new ApiResponse("added successfully"));
    }

    @PutMapping("/update/{categoryid}")
    public ResponseEntity update(@PathVariable Integer categoryid,@RequestBody @Valid Category category,Errors errors ){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if(categoryService.update(categoryid,category))
        return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("update Unsuccessfully"));
    }


    @DeleteMapping("delete/{categoryid}")
    public ResponseEntity delete(@PathVariable Integer categoryid ){
        if(categoryService.delete(categoryid))
        return ResponseEntity.status(200).body(new ApiResponse("deleted successfully"));

        return ResponseEntity.status(400).body(new ApiResponse(" delete Unsuccessfully"));
    }




}
