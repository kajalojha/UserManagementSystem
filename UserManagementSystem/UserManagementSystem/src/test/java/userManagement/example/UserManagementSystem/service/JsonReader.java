package userManagement.example.UserManagementSystem.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JsonReader {
    public String readFile(String path) throws IOException {
        String jsonString = new String(getClass().getClassLoader().getResourceAsStream("payloads/" + path + ".json").readAllBytes(), StandardCharsets.UTF_8);
        return jsonString;
    }

}
