package ca.mcgill.ecse321.museum.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.museum.dao.EmployeeRepository;
import ca.mcgill.ecse321.museum.dao.VisitorRepository;
import ca.mcgill.ecse321.museum.dto.VisitorDto;
import ca.mcgill.ecse321.museum.model.Visitor;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AuthenticationIntegrationTest {
    @Autowired
    private TestRestTemplate client;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        employeeRepository.deleteAll();
        visitorRepository.deleteAll();
    }

    @Test
    public void testLogin() {
        // TODO

        Visitor visitor = createVisitor();

        ResponseEntity<String> response =
                client.postForEntity("/api/auth/login", visitor, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
        assertNotNull(response.getBody(), "Response has body");
        assertEquals("logged in", response.getBody(), "Response has correct name");
    }

    @Test
    public void testLoginInvalidPassword() {
        Visitor visitor = createVisitor();
        visitor.setPassword("Speed123#$");

        ResponseEntity<String> response =
                client.postForEntity("/api/auth/login", visitor, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(),
                "Response has correct status");
    }

    @Test
    public void testLoginInvalidEmail() {
        Visitor visitor = createVisitor();
        visitorRepository.save(visitor);
        visitor.setEmail("fernando.alonso@gmail.com");

        ResponseEntity<String> response =
                client.postForEntity("/api/auth/login", visitor, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(),
                "Response has correct status");
    }

    public Visitor createVisitor() {
        Visitor visitor = new Visitor();
        visitor.setEmail("sebastien.vettel@gmail.com");
        visitor.setPassword("#BrazilGp2022");
        visitor.setName("Sebastien Vettel");
        visitorRepository.save(visitor);
        return visitor;
    }

    // @Test
    // public void testLoginWhenLoggedin() {
    // loginBefore();
    // Visitor visitor = new Visitor();
    // visitor.setEmail("sebastien.vettel@gmail.com");
    // visitor.setPassword("#BrazilGp2022");
    // visitor.setName("Sebastien Vettel");
    // client.postForEntity("/api/auth/login", visitor, String.class);

    // ResponseEntity<String> response =
    // client.postForEntity("/api/auth/login", visitor, String.class);
    // assertNotNull(response);
    // assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode(),
    // "Cannot login while logged in");
    // }

    // public void loginBefore() {
    // Visitor visitor = new Visitor();
    // visitor.setEmail("sebastien.vettel@gmail.com");
    // visitor.setPassword("#BrazilGp2022");
    // visitor.setName("Sebastien Vettel");
    // visitorRepository.save(visitor);

    // client.postForEntity("/api/auth/login", visitor, String.class);
    // }
}