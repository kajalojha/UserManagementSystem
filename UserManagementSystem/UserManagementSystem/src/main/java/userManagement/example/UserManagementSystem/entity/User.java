package userManagement.example.UserManagementSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
@Entity
@Table(name="UserTable")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    @NotEmpty(message = "userName must be provided")
    @NotNull(message = "userName must be provided")
    private String userName;
    @NotEmpty(message = "email must be provided")
    @NotNull(message = "email must be provided")
    @Email
    private String email;
    @NotEmpty(message = "password must be provided")
    @NotNull(message = "password must be provided")

    private String password;
    private LocalDateTime createAt;


    public User() {
    }

    public User(Long id, String userName, String email, String password, LocalDateTime createAt) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.createAt = createAt;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", createAt=" + createAt +
                '}';
    }
}
