package userManagement.example.UserManagementSystem.Service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userManagement.example.UserManagementSystem.Repository.UserRepository;
import userManagement.example.UserManagementSystem.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUser(){
        List<User> userList = userRepository.findAll();
        return userList;
    }

    public void addUser(User user ){
        if(user.getId()!=null && userRepository.existsById(user.getId())){
            throw new IllegalStateException("user with id " + user.getId()+ "already exists");
        }
        LocalDateTime now = LocalDateTime.now();
        user.setCreateAt(LocalDateTime.now());
       userRepository.save(user);
    }

    public void deleteUser(Long id ){
        if(!userRepository.existsById(id)){
            throw new IllegalStateException("user with id" + id + "does not exist");
        }
        userRepository.deleteById(id);
    }
//    @Transactional
//    public User updateUser(Long id , String newName ){
//        Optional<User> optionalUser = userRepository.findById(id);
//        if(optionalUser.isPresent()){
//            User user = optionalUser.get();
//            user.setUserName(newName);
//           // LocalDateTime now = LocalDateTime.now();
//            user.setUpdateAt(LocalDateTime.now());
//            return userRepository.save(user);
//        }else {
//            throw new IllegalStateException(("employee with id " + id +" does not found.."));
//        }
//    }
@Transactional
public User updateUser(Long id, String newName) {
    Optional<User> optionalUser = userRepository.findById(id);
    if (optionalUser.isPresent()) {
        User user = optionalUser.get();
        user.setUserName(newName);
        return userRepository.save(user); // Changes will be persisted to the database
    } else {
        throw new IllegalStateException("User with id " + id + " does not exist");
    }
}

    public static List<User> findByUserEmail(String email , UserRepository userRepository){
        return userRepository.findByEmail(email);
    }
}
