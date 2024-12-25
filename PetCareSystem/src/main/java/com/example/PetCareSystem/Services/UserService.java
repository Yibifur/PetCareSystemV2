package com.example.PetCareSystem.Services;

import com.example.PetCareSystem.DTO.UserDTO;
import com.example.PetCareSystem.DTO.PetDTO;
import com.example.PetCareSystem.Entities.User;
import com.example.PetCareSystem.Entities.Role;
import com.example.PetCareSystem.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user, String roleName) {
        // Kullanıcıya rol ekle
        Role role = new Role();
        role.setRoleName(roleName);
        user.getRoles().add(role);

        // Kullanıcıyı kaydet ve döndür
        return userRepository.save(user);
    }

    public UserDTO getUserById(int userId) {
        // Kullanıcı kontrolü
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Kullanıcının petlerini dönüştürerek PetDTO listesi oluştur
        List<PetDTO> pets = user.getPets().stream()
                .map(pet -> new PetDTO(
                        pet.getId(),
                        pet.getName(),
                        pet.getType(),
                        pet.getAge(),
                        null, // FeedingScheduleDTO
                        null, // List<MedicationDTO>
                        null, // List<VaccinationDTO>
                        null, // List<VetAppointmentDTO>
                        null, // List<SupplyDTO>
                        new UserDTO(user.getId(), user.getUsername(), user.getEmail(), null) // Owner bilgisi
                ))
                .collect(Collectors.toList());

        // Kullanıcı DTO'sunu oluştur ve döndür
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), pets);
    }
}
