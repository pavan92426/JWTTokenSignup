package com.proj.willow.repository;

import com.proj.willow.entity.WillowUserEntity;
import com.proj.willow.shared.dto.UserDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WillowUserRepository extends CrudRepository<WillowUserEntity,Long> {
     WillowUserEntity findByEmail(String email);
}
