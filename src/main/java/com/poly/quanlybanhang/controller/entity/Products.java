package com.poly.quanlybanhang.controller.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Products{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;
    String description;
    Double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Categories categories;

    @OneToMany(mappedBy = "product")
    List<OrderDetails> orderDetails;


    @Column(name = "createDate")
    LocalDate createDate;

    @Column(name = "updateDate")
    LocalDate updateDate;

}
