package data.repository;

import data.models.Resident;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ResidentsTest {
    private ResidentRepository residentRepository;

    @BeforeEach
    public void setUp() {
        residentRepository = new Residents();
        residentRepository.deleteAll();
    }

    @Test
    public void testSaveNewResident() {
        Resident resident = new Resident();
        resident.setFullName("ola tunji");
        resident.setPhoneNumber("081");
        resident.setEmail("yusuf.usman1003@gmail.com");
        resident.setPassword("12345678");

        Resident savedResident = residentRepository.save(resident);

        assertNotNull(savedResident);
        assertEquals(1, savedResident.getId());
        assertEquals("ola tunji", savedResident.getFullName());
        assertEquals("081", savedResident.getPhoneNumber());
        assertEquals("yusuf.usman1003@gmail.com", savedResident.getEmail());
        assertEquals(1, residentRepository.count());
    }

    @Test
    public void testSaveExistingResident_UpdatesResident() {
        Resident resident = new Resident();
        resident.setFullName("ola tunji");
        resident.setPhoneNumber("081");
        resident.setEmail("yusuf.usman1003@gmail.com");
        resident.setPassword("12345678");
        Resident savedResident = residentRepository.save(resident);

        savedResident.setFullName("yusuf usman");
        Resident updatedResident = residentRepository.save(savedResident);

        assertEquals(1, updatedResident.getId());
        assertEquals("yusuf usman", updatedResident.getFullName());
        assertEquals("yusuf.usman1003@gmail.com", updatedResident.getEmail());
        assertEquals(1, residentRepository.count());
        assertEquals("yusuf usman", residentRepository.findAll().get(0).getFullName());
    }

    @Test
    public void testFindAll() {
        Resident resident1 = new Resident();
        resident1.setFullName("yusuf usman");
        resident1.setPhoneNumber("081");
        resident1.setEmail("yusuf@gmail.com");
        resident1.setPassword("12345678");
        residentRepository.save(resident1);

        Resident resident2 = new Resident();
        resident2.setFullName("ola tunji");
        resident2.setPhoneNumber("0811");
        resident2.setEmail("yusuf.usman@gmail.com");
        resident2.setPassword("123456789");
        residentRepository.save(resident2);

        List<Resident> allResidents = residentRepository.findAll();

        assertEquals(2, allResidents.size());
        assertEquals("yusuf usman", allResidents.get(0).getFullName());
        assertEquals("ola tunji", allResidents.get(1).getFullName());
    }

    @Test
    public void testFindById() {
        Resident resident = new Resident();
        resident.setFullName("yusuf usman");
        resident.setPhoneNumber("08169025216");
        resident.setEmail("yusuf@gmail.com");
        resident.setPassword("12345678");
        residentRepository.save(resident);

        Resident foundResident = residentRepository.findById(1).orElse(null);

        assertNotNull(foundResident);
        assertEquals(1, foundResident.getId());
        assertEquals("yusuf usman", foundResident.getFullName());
        assertEquals("yusuf@gmail.com", foundResident.getEmail());
    }

    @Test
    public void testFindById_NotFound() {
        Resident foundResident = residentRepository.findById(1).orElse(null);
        assertNull(foundResident);
    }

    @Test
    public void testFindByEmail() {
        Resident resident = new Resident();
        resident.setFullName("yusuf usman");
        resident.setPhoneNumber("091");
        resident.setEmail("yusuf@gmail.com");
        resident.setPassword("12345678");
        residentRepository.save(resident);

        Resident foundResident = residentRepository.findByEmail("yusuf@gmail.com").orElse(null);

        assertNotNull(foundResident);
        assertEquals("yusuf@gmail.com", foundResident.getEmail());
        assertEquals("yusuf usman", foundResident.getFullName());
    }

    @Test
    public void testFindByEmail_NotFound() {
        Resident foundResident = residentRepository.findByEmail("yusuf@gmail.com").orElse(null);
        assertNull(foundResident);
    }

    @Test
    public void testFindAllByFullName() {
        Resident resident1 = new Resident();
        resident1.setFullName("yusuf usman");
        resident1.setPhoneNumber("081");
        resident1.setEmail("yusuf@gmail.com");
        resident1.setPassword("12345678");
        residentRepository.save(resident1);

        Resident resident2 = new Resident();
        resident2.setFullName("yusuf usman");
        resident2.setPhoneNumber("091");
        resident2.setEmail("y@gmail.com");
        resident2.setPassword("1234567789");
        residentRepository.save(resident2);

        Resident resident3 = new Resident();
        resident3.setFullName("ola tunji");
        resident3.setPhoneNumber("0912");
        resident3.setEmail("ola@gmail.com");
        resident3.setPassword("12332123");
        residentRepository.save(resident3);

        List<Resident> yusufs = residentRepository.findAllByFullName("yusuf usman");
        assertEquals(2, yusufs.size());
        assertEquals("yusuf usman", yusufs.get(0).getFullName());
        assertEquals("yusuf usman", yusufs.get(1).getFullName());

        List<Resident> olas = residentRepository.findAllByFullName("ola tunji");
        assertEquals(1, olas.size());
        assertEquals("ola tunji", olas.get(0).getFullName());

        List<Resident> none = residentRepository.findAllByFullName("Bob Johnson");
        assertTrue(none.isEmpty());
    }

    @Test
    public void testDeleteById() {
        Resident resident = new Resident();
        resident.setFullName("yusuf usman");
        resident.setPhoneNumber("081");
        resident.setEmail("yusuf@gmail.com");
        resident.setPassword("12345678");
        residentRepository.save(resident);

        residentRepository.delete(1);
        assertEquals(0, residentRepository.count());
        assertFalse(residentRepository.findById(1).isPresent());
    }

    @Test
    public void testDeleteAll() {
        Resident resident = new Resident();
        resident.setFullName("yusuf usman");
        resident.setPhoneNumber("081");
        resident.setEmail("yusuf@mail.com");
        resident.setPassword("12345678");
        residentRepository.save(resident);

        residentRepository.deleteAll();
        assertEquals(0, residentRepository.count());
        assertTrue(residentRepository.findAll().isEmpty());
    }

    @Test
    public void testCount() {
        assertEquals(0, residentRepository.count());

        Resident resident1 = new Resident();
        resident1.setFullName("yusuf usman");
        resident1.setEmail("yusuf@gmail.com");
        residentRepository.save(resident1);

        assertEquals(1, residentRepository.count());

        Resident resident2 = new Resident();
        resident2.setFullName("ola tunji");
        resident2.setEmail("tunji@gmail");
        residentRepository.save(resident2);

        assertEquals(2, residentRepository.count());
    }
}