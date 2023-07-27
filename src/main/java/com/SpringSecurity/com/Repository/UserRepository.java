package com.SpringSecurity.com.Repository;

import com.SpringSecurity.com.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User , String> {
    Optional<User> findAllUserByUsername(String userName) ;
}
