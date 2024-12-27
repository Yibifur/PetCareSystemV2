package com.example.PetCareSystem.Controllers;

import com.example.PetCareSystem.DTO.UserDTO;
import com.example.PetCareSystem.Entities.User;
import com.example.PetCareSystem.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user, @RequestParam String role) {
        User savedUser = userService.registerUser(user, role);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/{userId}/pets")
    public ResponseEntity<UserDTO> getUserPets(@PathVariable int userId) {
        UserDTO userDTO = userService.getUserById(userId);
        return ResponseEntity.ok(userDTO);
    }
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUSers(){
        List<UserDTO> users=userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
