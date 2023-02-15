package com.example.redisbase.rep;

import com.example.redisbase.entity.RedisUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<RedisUser, UUID> {
    Optional<RedisUser> findByEmailAndPassword(String email, String password);
}
