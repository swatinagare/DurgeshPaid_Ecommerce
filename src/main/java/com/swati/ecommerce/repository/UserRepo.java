package com.swati.ecommerce.repository;

import com.swati.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface UserRepo extends JpaRepository<User,String> {


    Optional<User> findByEmail(String email);

    List<User> findByNameContaining(String keywords);
}
