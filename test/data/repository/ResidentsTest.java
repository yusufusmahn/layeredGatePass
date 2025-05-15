package data.repository;

import data.models.Resident;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class  ResidentsTest {
    private ResidentRepository residents;

    @BeforeEach
    public void setUp() {
        residents = new Residents();
    }

    @Test
    public void testThatCountIsZero_BeforeCreation() {
        assertEquals(0, residents.count());
    }

    @Test
    public void testThatCountIsOne_AfterCreation() {
        Resident resident1 = new Resident();
        residents.save(resident1);
        assertEquals(1, residents.count());

    }

    @Test
    public void testThatCountIsTwo_AfterCreation() {
        Resident resident2 = new Resident();
        Resident resident3 = new Resident();
        residents.save(resident2);
        residents.save(resident3);
        assertEquals(2, residents.count());
    }

    @Test
    public void registerResident_countIsOne_findById() {
        Resident resident1 = new Resident();
        Resident saved = residents.save(resident1);
        Optional<Resident> foundResident = residents.findById(saved.getId());
        assertTrue(foundResident.isPresent());
        assertEquals(saved, foundResident.get());
    }

    @Test
    public void testFindAllByFullName() {
        Resident resident1 = new Resident();
        resident1.setFullName("yusuf usman");
        Resident resident2 = new Resident();
        resident2.setFullName("yusuf usman");
        Resident resident3 = new Resident();
        resident3.setFullName("usman yusuf");

        residents.save(resident1);
        residents.save(resident2);
        residents.save(resident3);

        List<Resident> result = residents.findAllByFullName("yusuf usman");
        assertEquals(2, result.size());
        assertEquals("yusuf usman", result.get(0).getFullName());
        assertEquals("yusuf usman", result.get(1).getFullName());
    }

    @Test
    public void testDeleteById() {
        Resident resident1 = new Resident();
        Resident saved = residents.save(resident1);
        assertEquals(1, residents.count());

        residents.delete(saved.getId());
        assertEquals(0, residents.count());
        assertFalse(residents.findById(saved.getId()).isPresent());
    }

    @Test
    public void testFindAll() {
        Resident resident1 = new Resident();
        Resident resident2 = new Resident();
        residents.save(resident1);
        residents.save(resident2);

        List<Resident> allResidents = residents.findAll();
        assertEquals(2, allResidents.size());
    }

    @Test
    public void testUpdateResident() {
        Resident resident1 = new Resident();
        resident1.setFullName("yusuf usman");
        Resident result = residents.save(resident1);
        result.setFullName("yusuf Updated");
        residents.save(result);

        Optional<Resident> updatedResident = residents.findById(result.getId());
        assertTrue(updatedResident.isPresent());
        assertEquals("yusuf Updated", updatedResident.get().getFullName());
    }

}