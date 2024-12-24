package com.example.PetCareSystem.Repositories;

import com.example.PetCareSystem.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
