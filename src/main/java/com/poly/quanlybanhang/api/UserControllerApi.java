package com.poly.quanlybanhang.api;

import com.poly.quanlybanhang.dto.request.UserCreationRequest;
import com.poly.quanlybanhang.dto.request.UserUpdationRequest;
import com.poly.quanlybanhang.dto.response.ApiResponse;
import com.poly.quanlybanhang.dto.response.UserResponse;
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
@RequestMapping("/api/users")
public class UserControllerApi {
    UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> create(@Valid @RequestBody UserCreationRequest request){
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .data(userService.create(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> update(@Valid @RequestBody UserUpdationRequest request, @PathVariable String id){
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .data(userService.update(request, id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable String id){
        userService.delete(id);
        return ApiResponse.<String>builder()
                .code(1000)
                .data("User has been deleted")
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getAll(){
        return ApiResponse.<List<UserResponse>>builder()
                .code(1000)
                .data(userService.getAll())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getAll(@PathVariable String id){
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .data(userService.getUser(id))
                .build();
    }
}
