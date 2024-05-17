package userManagement.example.UserManagementSystem.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import userManagement.example.UserManagementSystem.Repository.UserRepository;
import userManagement.example.UserManagementSystem.entity.User;
import userManagement.example.UserManagementSystem.entity.UserTest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestPropertySource("classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserService extends Base{
    private static final Logger logger = LoggerFactory.getLogger(UserTest.class);
    @Autowired
    private UserRepository userRepository;
    //all urls used in this class
    // Define the base URL of your API
    private final String baseUrl = "http://localhost:8080";
    @Value("${request.user.url}")
    private String serviceUrl;
    @Value("${offset.user.url}")
    private String userOffsetUrl;
    @Test
    public void createUser() throws IOException, InterruptedException {
        // Create a test instance for reporting
        test = extent.createTest("createUser TestCase");

        // Log information indicating the start of the test
        logger.info("starting createUser test...");

        // Instantiate a JsonReader to read JSON data from a file
        JsonReader jsonReader = new JsonReader();

        // Read JSON data for the user from the "user.json" file
        String requestData = jsonReader.readFile("user");
        System.out.println("request data " + requestData);

        // Print the service URL for debugging purposes
        System.out.println("service url " + serviceUrl);

        // Build a POST request to create a user
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create(serviceUrl + "/add")) // Endpoint for creating a user
                .header("Content-Type", "application/json") // Set request header for JSON content
                .POST(HttpRequest.BodyPublishers.ofString(requestData, StandardCharsets.UTF_8)) // Set request body with user data
                .build();

        // Send the POST request and capture the response
        HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());

        // Assert that the response status code is 200 (OK)
        assertEquals(200, postResponse.statusCode());

        // Log the status code of the createUser operation
        logger.info("createUser status code: {}", postResponse.statusCode());

        // Retrieve the created user from the database using the email address
        List<User> userList = userRepository.findByEmail("kajal@gmail.com");

        // Assert that the email of the created user matches the expected email
        assertEquals("kajal@gmail.com", userList.get(0).getEmail());

        // Log the email of the created user
        logger.info("created user email is: {}", userList.get(0).getEmail());
    }

    @Test
    public void testGetAllUsers() throws IOException, InterruptedException {
        // Construct the URL for fetching all users
        String serviceUrl = baseUrl + "/user/all";

        // Prepare the GET request
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create(serviceUrl))
                .GET()
                .build();

        // Send the GET HttpRequest to fetch all users and capture the response
        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

        // Assert that the response status code is 200 (OK), indicating successful retrieval
        assertEquals(200, getResponse.statusCode(), "Expected status code should be 200 OK");
    }

    @Test
    public void deleteUserById() throws IOException, InterruptedException {
        // Create a test instance for reporting
        test = extent.createTest("deleteUserById test Case");

        // Log information indicating the start of the test
        logger.info("starting deleteUserById test..");

        // Define the ID of the user to be deleted
        String Id = "102";

        // Construct the URL for deleting the user by replacing the {id} placeholder with the actual user ID
        String userDeleteUrl = userOffsetUrl.replace("{id}", Id);

        // Create the URI for the DELETE request
        URI uri = URI.create(userDeleteUrl);

        // Prepare the DELETE request
        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(uri)
                .DELETE()
                .build();

        // Send the DELETE HttpRequest to delete the user by ID and capture the response
        HttpResponse<String> deleteResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());

        // Assert that the response status code is 200 (OK), indicating successful deletion
        assertEquals(200, deleteResponse.statusCode());

        // Log the status code of the deleteUserById operation
        logger.info("deleteUserById status code: {}", deleteResponse.statusCode());
    }
    @Test
    public void getUserById() throws IOException ,InterruptedException{
        test = extent.createTest("getUserById Test Case");
        logger.info("Starting getUserById test..");

        String id = "152";
        String userGetUrl = userOffsetUrl.replace("{id}" , id);
        URI uri = URI.create(userGetUrl);

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(200 , getResponse.statusCode());
        logger.info("getUserById Status code: {}" , getResponse.statusCode());

    }

@Test
public void testFindUserByEmail() throws IOException, InterruptedException {
    // Define the email of the user to search for
    String email = "kajal@gmail.com";

    // Construct the URL for fetching user data by email
    String serviceUrl = "http://localhost:8080/user/email/" + email;

    // Create a new HttpClient instance
    HttpClient client = HttpClient.newHttpClient();

    // Prepare the HttpRequest for fetching user data by email
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(serviceUrl))
            .build();

    // Send the HttpRequest and capture the HttpResponse
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    // Assert that the HTTP response status code is 200 (OK)
    assertThat(response.statusCode()).isEqualTo(200);

    // Assert that the response body is not null
    assertThat(response.body()).isNotNull();

    // Assert that the response body contains the email "kajal@gmail.com"
    assertThat(response.body()).contains("\"email\":\"kajal@gmail.com\"");
}

@Test
public void testDeleteAll() throws IOException , InterruptedException{
        String serviceUrl = baseUrl+"/user/all";
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serviceUrl))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request , HttpResponse.BodyHandlers.ofString());
        assertThat(response.statusCode()).isEqualTo(200);
}

}
