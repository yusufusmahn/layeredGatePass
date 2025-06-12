package services;

import data.repository.Securities;
import data.repository.SecurityRepository;
import dtos.requests.LoginSecurityRequest;
import dtos.requests.RegisterSecurityRequest;
import dtos.responses.FindSecurityResponse;
import dtos.responses.LoginSecurityResponse;
import dtos.responses.RegisterSecurityResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SecurityServicesImplTest {
    private SecurityServices securityService;
    private SecurityRepository securityRepository;

    @BeforeEach
    public void setUp() {
        securityRepository = new Securities();
        securityService = new SecurityServicesImpl(securityRepository);
        securityRepository.deleteAll();
    }

    @Test
    public void testRegisterSecurity() {
        RegisterSecurityRequest request = new RegisterSecurityRequest();
        request.setFullName("Jane Doe");
        request.setPhoneNumber("09012345678");
        request.setEmail("jane.doe@example.com");
        request.setPassword("password123");

        RegisterSecurityResponse response = securityService.register(request);

        assertEquals(1, response.getSecurityId());
        assertEquals("Jane Doe", response.getFullName());
        assertEquals("Security registered successfully", response.getMessage());
    }

    @Test
    public void testRegisterSecurity_DuplicateEmail() {
        RegisterSecurityRequest request1 = new RegisterSecurityRequest();
        request1.setFullName("Jane Doe");
        request1.setPhoneNumber("09012345678");
        request1.setEmail("jane.doe@example.com");
        request1.setPassword("password123");
        securityService.register(request1);

        RegisterSecurityRequest request2 = new RegisterSecurityRequest();
        request2.setFullName("John Smith");
        request2.setPhoneNumber("09087654321");
        request2.setEmail("jane.doe@example.com");
        request2.setPassword("password456");

        assertThrows(IllegalArgumentException.class, () -> securityService.register(request2), "Email already exists: jane.doe@example.com");
    }

    @Test
    public void testRegisterSecurity_PasswordTooShort() {
        RegisterSecurityRequest request = new RegisterSecurityRequest();
        request.setFullName("Jane Doe");
        request.setPhoneNumber("09012345678");
        request.setEmail("jane.doe@example.com");
        request.setPassword("pass");

        assertThrows(IllegalArgumentException.class, () -> securityService.register(request), "Password must be at least 8 characters long");
    }

    @Test
    public void testRegisterSecurity_InvalidEmail() {
        RegisterSecurityRequest request = new RegisterSecurityRequest();
        request.setFullName("Jane Doe");
        request.setPhoneNumber("09012345678");
        request.setEmail("invalid-email");
        request.setPassword("password123");

        assertThrows(IllegalArgumentException.class, () -> securityService.register(request), "Valid email is required");
    }

    @Test
    public void testLoginSecurity_Success() {
        RegisterSecurityRequest regRequest = new RegisterSecurityRequest();
        regRequest.setFullName("Jane Doe");
        regRequest.setPhoneNumber("09012345678");
        regRequest.setEmail("jane.doe@example.com");
        regRequest.setPassword("password123");
        securityService.register(regRequest);

        LoginSecurityRequest loginRequest = new LoginSecurityRequest("jane.doe@example.com", "password123");
        LoginSecurityResponse response = securityService.login(loginRequest);

        assertEquals(1, response.getSecurityId());
        assertEquals("Login successful", response.getMessage());
    }

    @Test
    public void testLoginSecurity_Failure_InvalidPassword() {
        RegisterSecurityRequest regRequest = new RegisterSecurityRequest();
        regRequest.setFullName("Jane Doe");
        regRequest.setPhoneNumber("09012345678");
        regRequest.setEmail("jane.doe@example.com");
        regRequest.setPassword("password123");
        securityService.register(regRequest);

        LoginSecurityRequest loginRequest = new LoginSecurityRequest("jane.doe@example.com", "wrongpassword");
        assertThrows(IllegalArgumentException.class, () -> securityService.login(loginRequest), "Invalid password");
    }

    @Test
    public void testLoginSecurity_Failure_EmailNotFound() {
        LoginSecurityRequest loginRequest = new LoginSecurityRequest("nonexistent@example.com", "password123");
        assertThrows(IllegalArgumentException.class, () -> securityService.login(loginRequest), "Security with email nonexistent@example.com not found");
    }

    @Test
    public void testFindById_Success() {
        RegisterSecurityRequest request = new RegisterSecurityRequest();
        request.setFullName("Jane Doe");
        request.setPhoneNumber("09012345678");
        request.setEmail("jane.doe@example.com");
        request.setPassword("password123");
        securityService.register(request);

        FindSecurityResponse response = securityService.findById(1);

        assertNotNull(response);
        assertEquals("Jane Doe", response.getFullName());
        assertEquals("09012345678", response.getPhoneNumber());
        assertEquals("jane.doe@example.com", response.getEmail());
    }

    @Test
    public void testFindById_Failure_InvalidId() {
        assertThrows(IllegalArgumentException.class, () -> securityService.findById(0), "Invalid ID: 0");
    }

    @Test
    public void testFindById_Failure_NotFound() {
        assertThrows(IllegalArgumentException.class, () -> securityService.findById(1), "Security with ID 1 not found");
    }
}