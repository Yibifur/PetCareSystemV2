package com.example.PetCareSystem.Controllers;

import com.example.PetCareSystem.DTO.GetPetDTO;
import com.example.PetCareSystem.DTO.PetDTO;
import com.example.PetCareSystem.DTO.UpdateDTOs.UpdatePetDTO;
import com.example.PetCareSystem.DTO.UserDTO;
import com.example.PetCareSystem.Entities.CustomPrincipal;
import com.example.PetCareSystem.Entities.Pet;
import com.example.PetCareSystem.Services.PetService;
import com.example.PetCareSystem.Services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PetService petService;

    @PreAuthorize("hasAuthority('ADMIN') and isAuthenticated()")
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable int userId) {
        try {
            UserDTO userDTO = userService.getUserById(userId);
            return ResponseEntity.ok(userDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: User not found with ID: " + userId);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Error: You are not authorized to access this user's data.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: An unexpected error occurred while fetching user data.");
        }
    }

    @PreAuthorize("hasRole('ADMIN') and isAuthenticated()")
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserDTO> users = userService.getAllUsers();

            if (users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Error: No users found in the system.");
            }

            return ResponseEntity.ok(users);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Error: You are not authorized to access user data.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: An unexpected error occurred while fetching user data.");
        }
    }


    @GetMapping("/{userId}/pets")
    @PreAuthorize("hasRole('USER') and isAuthenticated()")
    public ResponseEntity<List<GetPetDTO>> getPetsByUserId(
            @PathVariable int userId,
            @AuthenticationPrincipal CustomPrincipal principal) throws BadRequestException {

        // Kullanıcının kimliğini doğrula
        if (userId != principal.getUserId()) {
            throw new BadRequestException("You are not authorized to view this user's pets.");
        }

        // Kullanıcının pet detaylarını getir
        List<GetPetDTO> petDetails = petService.getPetsByUserId(userId);

        return ResponseEntity.ok(petDetails);
    }
    @GetMapping("{userId}/pets/{petId}")
    @PreAuthorize("hasRole('USER') and isAuthenticated()")
    public ResponseEntity<?> getPetDetails(@PathVariable int petId) {
        try {
            PetDTO petDTO = petService.getPetDetails(petId);
            return ResponseEntity.ok(petDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: Pet not found with ID: " + petId);
        }
    }

    @PreAuthorize("hasRole('USER') and isAuthenticated()")
    @PutMapping("/{userId}/pets/{petId}/update")
    public ResponseEntity<UpdatePetDTO> updatePet(@PathVariable int userId,@PathVariable int petId,@RequestBody UpdatePetDTO updatePetDTO) throws BadRequestException {
        UserDTO userDTO = userService.getUserById(userId);
        PetDTO petDTO=petService.getPetDetails(petId);
        if (userDTO != null && userDTO.getId() == petDTO.getOwner().getId()) {
            petService.updatePet(petId,updatePetDTO);
        } else {
            throw new BadRequestException("User is not authorized to update this pet.");
        }
        return ResponseEntity.ok(updatePetDTO);
    }

    @PreAuthorize("hasRole('USER') and isAuthenticated()")
    @PostMapping("/{userId}/pets/add")
    public ResponseEntity<GetPetDTO> addPet(@PathVariable int userId, @RequestBody Pet pet,@AuthenticationPrincipal CustomPrincipal principal) {
        if (userId!=(principal.getUserId())) {
            throw new AccessDeniedException("You are not authorized to access this resource");
        }
        GetPetDTO savedPet = petService.addPet(userId, pet);
        return ResponseEntity.ok(savedPet);
    }
    @DeleteMapping("/{userId}/pets/{petId}/delete")
    @PreAuthorize("hasRole('USER') and isAuthenticated()")
    public ResponseEntity<String> deletePet(@PathVariable int petId,@PathVariable int userId,@AuthenticationPrincipal CustomPrincipal principal) {
        if (userId!=(principal.getUserId())) {
            throw new AccessDeniedException("You are not authorized to access this resource");
        }
        petService.deletePet(petId);
        return ResponseEntity.ok("Pet deleted successfully");
    }

}
