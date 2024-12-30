package com.example.PetCareSystem.Controllers;

import com.example.PetCareSystem.DTO.CreateDTOs.CreatePetDTO;
import com.example.PetCareSystem.DTO.GetPetDTO;
import com.example.PetCareSystem.DTO.PetDTO;
import com.example.PetCareSystem.DTO.UpdateDTOs.UpdatePetDTO;
import com.example.PetCareSystem.DTO.UserDTO;
import com.example.PetCareSystem.Entities.CustomPrincipal;
import com.example.PetCareSystem.Entities.Pet;
import com.example.PetCareSystem.Entities.User;
import com.example.PetCareSystem.Services.PetService;
import com.example.PetCareSystem.Services.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PetService petService;


    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserPets(@PathVariable int userId) {
        UserDTO userDTO = userService.getUserById(userId);
        return ResponseEntity.ok(userDTO);
    }
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUSers(){
        List<UserDTO> users=userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}/pets")
    @PreAuthorize("isAuthenticated()")
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

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{userId}/pets/add")
    public ResponseEntity<GetPetDTO> addPet(@PathVariable int userId, @RequestBody Pet pet,@AuthenticationPrincipal CustomPrincipal principal) {
        if (userId!=(principal.getUserId())) {
            throw new AccessDeniedException("You are not authorized to access this resource");
        }
        GetPetDTO savedPet = petService.addPet(userId, pet);
        return ResponseEntity.ok(savedPet);
    }
    @DeleteMapping("/{userId}/pets/{petId}/delete")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deletePet(@PathVariable int petId,@PathVariable int userId,@AuthenticationPrincipal CustomPrincipal principal) {
        if (userId!=(principal.getUserId())) {
            throw new AccessDeniedException("You are not authorized to access this resource");
        }
        petService.deletePet(petId);
        return ResponseEntity.ok("Pet deleted successfully");
    }

}
