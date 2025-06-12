package services;

import data.repository.ResidentRepository;
import data.repository.Residents;
import dtos.requests.LoginRequest;
import dtos.requests.RegisterResidentRequest;
import dtos.responses.FindResidentResponse;
import dtos.responses.LoginResponse;
import dtos.responses.RegisterResidentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ResidentServicesImplTest {
    private ResidentServices residentService;
    private ResidentRepository residentRepository;

    @BeforeEach
    public void setUp() {
        residentRepository = new Residents();
        residentService = new ResidentServicesImpl(residentRepository);
        residentRepository.deleteAll();
    }

    @Test
    public void testRegisterResident() {
        RegisterResidentRequest request = new RegisterResidentRequest();
        request.setFullName("yusuf usman");
        request.setPhoneNumber("08169025216");
        request.setEmail("yusuf.usman1003@gmail.com");
        request.setPassword("12345678");

        RegisterResidentResponse response = residentService.register(request);

        assertNotNull(response);
        assertEquals(1, response.getResidentId());
        assertEquals("yusuf usman", response.getFullName());
        assertEquals("Resident registered successfully", response.getMessage());
        assertEquals(1, residentRepository.count());
    }

    @Test
    public void testRegisterResident_PasswordTooShort() {
        RegisterResidentRequest request = new RegisterResidentRequest();
        request.setFullName("yusuf usman");
        request.setPhoneNumber("08169025216");
        request.setEmail("yusuf.usman1003@gmail.com");
        request.setPassword("12345");

        assertThrows(IllegalArgumentException.class, () -> residentService.register(request));
    }



    @Test
    public void testLogin_Success() {
        RegisterResidentRequest regRequest = new RegisterResidentRequest();
        regRequest.setFullName("yusuf usman");
        regRequest.setPhoneNumber("08169025216");
        regRequest.setEmail("yusuf.usman1003@gmail.com");
        regRequest.setPassword("12345678");
        residentService.register(regRequest);

        LoginRequest loginRequest = new LoginRequest("yusuf.usman1003@gmail.com", "12345678");
        LoginResponse response = residentService.login(loginRequest);

        assertTrue(response.isSuccess());
        assertEquals("Login successful", response.getMessage());
        assertEquals(1, response.getResidentId());
    }

    @Test
    public void testLogin_Failure_InvalidPassword() {
        RegisterResidentRequest regRequest = new RegisterResidentRequest();
        regRequest.setFullName("yusuf usman");
        regRequest.setPhoneNumber("08169025216");
        regRequest.setEmail("yusuf.usman1003@gmail.com");
        regRequest.setPassword("12345678");
        residentService.register(regRequest);

        LoginRequest loginRequest = new LoginRequest("yusuf.usman1003@gmail.com", "wrongPassword");
        LoginResponse response = residentService.login(loginRequest);

        assertFalse(response.isSuccess());
        assertEquals("Invalid password", response.getMessage());
    }

    @Test
    public void testRegister_DuplicateEmail() {
        RegisterResidentRequest request1 = new RegisterResidentRequest();
        request1.setFullName("yusuf usman");
        request1.setPhoneNumber("098");
        request1.setEmail("y@gmail.com");
        request1.setPassword("12321234");
        residentService.register(request1);

        RegisterResidentRequest request2 = new RegisterResidentRequest();
        request2.setFullName("yusuf usman");
        request2.setPhoneNumber("081");
        request2.setEmail("y@gmail.com");
        request2.setPassword("12345678");
        RegisterResidentResponse response = residentService.register(request2);

        assertFalse(response.isSuccess());
        assertEquals("Email already exists: y@gmail.com", response.getMessage());
        assertEquals(0, response.getResidentId());
        assertNull(response.getFullName());
        assertEquals(1, residentRepository.count());
    }

    @Test
    public void testRegister_InvalidEmail() {
        RegisterResidentRequest request = new RegisterResidentRequest();
        request.setFullName("yusuf usman");
        request.setPhoneNumber("0897");
        request.setEmail("invalid-email");
        request.setPassword("12345678");

        assertThrows(IllegalArgumentException.class, () -> residentService.register(request));
    }


    @Test
    public void testFindById() {
        RegisterResidentRequest request = new RegisterResidentRequest();
        request.setFullName("yusuf usman");
        request.setPhoneNumber("08169025216");
        request.setEmail("yusuf.usman1003@gmail.com");
        request.setPassword("12345678");
        residentService.register(request);

        FindResidentResponse response = residentService.findById(1);

        assertNotNull(response);
        assertEquals("yusuf usman", response.getFullName());
        assertEquals("08169025216", response.getPhoneNumber());
        assertEquals("yusuf.usman1003@gmail.com", response.getEmail());
    }


}