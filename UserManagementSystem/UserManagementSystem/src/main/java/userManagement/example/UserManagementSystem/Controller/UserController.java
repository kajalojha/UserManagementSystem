package userManagement.example.UserManagementSystem.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import userManagement.example.UserManagementSystem.Repository.UserRepository;
import userManagement.example.UserManagementSystem.Service.UserService;
import userManagement.example.UserManagementSystem.entity.User;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    @DeleteMapping("/all")
    public void deleteAllUsers(){
        userRepository.deleteAll();
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){
        return userRepository.findById(id).orElse(null);
    }
    @PostMapping("/add")
    public void addEmployee(@RequestBody @Valid User user){
        if(user.getUserName() == null || user.getUserName().isEmpty() ||
                        user.getEmail()==null || user.getEmail().isEmpty() ||
                        user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalStateException("all attributes of employee must be provided");
        }
        else if (user.getId() != null && userRepository.existsById(user.getId())) {
            throw new IllegalStateException("employee With Id already exist");
        }
       userService.addUser(user);
    }
//    @DeleteMapping("/{id}")
//    public void deleteUser(Long id){
//        if(!userRepository.existsById(id)){
//            throw new IllegalStateException("user with id " + id + "not found");
//        }
//        userRepository.deleteById(id);
//
//    }
@DeleteMapping("/{id}")
public ResponseEntity<String> deleteUser(@PathVariable Long id) {
    try {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    } catch (IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
    }
}
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
        Optional<User> optionalEmployee = userRepository.findById(id);
        if(optionalEmployee.isPresent()){
            User existingEmployee = optionalEmployee.get();
            // update the attribute if the new valye are not null
            if(user.getUserName() != null){
                existingEmployee.setUserName(user.getUserName());
            }
            if(user.getEmail()!= null){
                existingEmployee.setEmail(user.getEmail());
            }
            if(user.getPassword() != null){
                existingEmployee.setPassword(user.getPassword());
            }
           User savedEmployee = userRepository.save(existingEmployee);
            return ResponseEntity.ok(savedEmployee);
        }else {
            throw new IllegalStateException("Employee with id " + id + "does not exist..");
        }
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<List<User>> getUserByEmail(@PathVariable String email){
        List<User> users = userService.findByUserEmail(email , userRepository);
        return users.isEmpty() ? ResponseEntity.notFound().build():ResponseEntity.ok(users);

    }
}
