package userManagement.example.UserManagementSystem.entity;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import userManagement.example.UserManagementSystem.Repository.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRepository userRepository;
    private User user;
    JsonReader jsonReader = new JsonReader();
    Map<String , Object> dataMap = jsonReader.readFile("user");
    String userName = (String) dataMap.get("userName");
    String email = (String) dataMap.get("email");
    String password = (String) dataMap.get("password");

    public UserTest() throws IOException {
    }
    @BeforeEach
    void setUp(){
        user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(password);
        System.out.println(userName);
        System.out.println(email);
        System.out.println(password);
    }

    @Test
    public void testUserConstructor(){
        assertNotNull(user);
        assertEquals(userName , user.getUserName());
        assertEquals(email , user.getEmail());
        assertEquals(password , user.getPassword());
    }
    @Test
    @Transactional
    @Commit
    public void testSaveUserEntityToDatabase(){
        User saveUser = userRepository.save(user);
        List<User> retrieveUser = userRepository.findByEmail(user.getEmail());
        assertNotNull(retrieveUser);
        assertEquals(user.getUserName() , retrieveUser.get(0).getUserName());
        assertEquals(user.getEmail() , retrieveUser.get(0).getEmail());
        assertEquals(user.getPassword() , retrieveUser.get(0).getPassword());
    }
    @Test
    void testGetters(){
        // test getters to ensure they return expected value
        assertThat(user.getUserName()).isEqualTo("kajal");
        assertThat(user.getEmail()).isEqualTo("kajal@gmail.com");
        assertThat(user.getPassword()).isEqualTo("kajal29");
    }

    @Test
    void testSetters(){
        user.setUserName("newName");
        user.setEmail("newEmail");
        user.setPassword("newPassword");

        assertThat(user.getUserName()).isEqualTo("newName");
        assertThat(user.getEmail()).isEqualTo("newEmail");
        assertThat(user.getPassword()).isEqualTo("newPassword");
    }

}
