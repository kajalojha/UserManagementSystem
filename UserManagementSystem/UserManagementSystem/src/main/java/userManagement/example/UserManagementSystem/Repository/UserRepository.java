package userManagement.example.UserManagementSystem.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import userManagement.example.UserManagementSystem.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User , Long> {
    List<User> findByEmail(String email);
}
