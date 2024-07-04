package com.poly.quanlybanhang.api;

import com.poly.quanlybanhang.dto.request.ProductRequest;
import com.poly.quanlybanhang.dto.request.UserCreationRequest;
import com.poly.quanlybanhang.dto.request.UserUpdationRequest;
import com.poly.quanlybanhang.dto.response.ApiResponse;
import com.poly.quanlybanhang.dto.response.ProductResponse;
import com.poly.quanlybanhang.dto.response.UserResponse;
import com.poly.quanlybanhang.service.ProductService;
import com.poly.quanlybanhang.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductControllerApi {
    ProductService productService;

    @PostMapping
    public ApiResponse<ProductResponse> create(@RequestBody ProductRequest request){
        return ApiResponse.<ProductResponse>builder()
                .code(1000)
                .data(productService.create(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> update(@RequestBody ProductRequest request, @PathVariable String id){
        return ApiResponse.<ProductResponse>builder()
                .code(1000)
                .data(productService.update(request, id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable String id){
        productService.delete(id);
        return ApiResponse.<String>builder()
                .code(1000)
                .data("Product has been deleted")
                .build();
    }

    @GetMapping
    public ApiResponse<List<ProductResponse>> getAll(){
        return ApiResponse.<List<ProductResponse>>builder()
                .code(1000)
                .data(productService.getAll())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getAll(@PathVariable String id){
        return ApiResponse.<ProductResponse>builder()
                .code(1000)
                .data(productService.getProduct(id))
                .build();
    }
}
