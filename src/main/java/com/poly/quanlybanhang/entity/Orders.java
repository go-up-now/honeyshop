package com.poly.quanlybanhang.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String fullname;
    String phone;
    Double totalAmount;
    String status;
    LocalDateTime createAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    Users user;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    List<OrderDetails> orderDetails;
}
