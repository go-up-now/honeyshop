package com.poly.quanlybanhang.dto.response;

import com.poly.quanlybanhang.entity.Users;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class OrderResponse {
    String id;
    String fullname;
    String phone;
    Double totalAmount;
    String status;
    Users user;
}
