package com.example.converse.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int totalItems;

    private long totalPrices;

    private String orderStatus;

    private Date requiredDate;

    private Date shippedDate;

    private String name;

    private String country;

    private String email;

    private String phone;

    private String address;

    private String note;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "order")
    @JsonBackReference
    public List<OrderDetail> orderDetail;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}
