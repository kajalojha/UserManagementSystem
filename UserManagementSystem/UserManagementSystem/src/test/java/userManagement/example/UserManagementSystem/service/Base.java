package userManagement.example.UserManagementSystem.service;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;

import java.net.http.HttpClient;
import java.sql.SQLException;

public class Base {
    HttpClient client;
    String port;

    static ExtentReports extent;
    static ExtentTest test;
    static ExtentHtmlReporter htmlReporter;

    @BeforeEach
    public void setUp() throws SQLException {
        client = HttpClient.newHttpClient();
        port = "8080";

        htmlReporter = new ExtentHtmlReporter("test-output/extent.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @AfterAll
    public static void tearDown(){
        extent.flush();
    }
}
