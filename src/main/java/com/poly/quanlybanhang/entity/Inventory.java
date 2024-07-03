package com.poly.quanlybanhang.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    Integer quantity;
    String changeType;
    String reason;
    LocalDate updateAt;

    @ManyToOne
    @JoinColumn(name = "changed_by")
    Users user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Products products;
}
