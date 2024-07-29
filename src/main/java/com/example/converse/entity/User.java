package com.example.converse.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fullname;

    private String username;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String address;

    private String phone;

    private String note;

    @ManyToMany(fetch = FetchType.EAGER)//lay ca cac doi tuong lien quan khi query du lieu
    @JsonBackReference
    @JoinTable(name = "user_role",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy = "user")
    @JsonBackReference
    private ShoppingCart shoppingCart;


}
