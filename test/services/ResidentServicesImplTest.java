package services;

import static org.junit.jupiter.api.Assertions.*;


import data.repository.ResidentRepository;
import data.repository.Residents;
import dtos.requests.RegisterResidentRequest;
import dtos.responses.RegisterResidentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ResidentServicesImplTest {
    private ResidentServices residentServices;
    private ResidentRepository residentRepository;

    @BeforeEach
    public void setUp() {
        residentRepository = new Residents();
        residentServices = new ResidentServicesImpl(residentRepository);

    }

    @Test
    public void testRegisterResident() {
        RegisterResidentRequest request = new RegisterResidentRequest();
        request.setFullName("yusuf usman");
        request.setPhoneNumber("08169025216");
        request.setEmail("yusuf.usman1003@gmail.com");

        RegisterResidentResponse response = residentServices.register(request);

//      assertNotNull(response);
        assertEquals(1, response.getResidentId());
        assertEquals("yusuf usman", response.getFullName());
        assertEquals("Resident registered successfully", response.getMessage());
        assertEquals(1, residentRepository.count());
    }

    @Test
    public void testRegisterResidentWithInvalidEmail() {
        RegisterResidentRequest request = new RegisterResidentRequest();
        request.setFullName("John Doe");
        request.setPhoneNumber("1234567890");
        request.setEmail("invalid-email");

        assertThrows(IllegalArgumentException.class, () -> residentServices.register(request));
    }
}