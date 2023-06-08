package com.swati.ecommerce.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends CustomFields{

    @Id
    @Column(name = "user_id")
    private String userId;
    @Column(name = "user_name",unique = true,nullable = false)
    private String name;
    @Column(name = "user_email")
    private String email;
    @Column(name = "user_password")
    private String password;
    private String gender;
    private String about;
    private String imageName;

}

