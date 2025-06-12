package data.repository;

import data.models.Security;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class SecuritiesTest {
    private SecurityRepository securityRepository;

    @BeforeEach
    public void setUp() {
        securityRepository = new Securities();
        securityRepository.deleteAll();
    }

    @Test
    public void testSaveNewSecurity() {
        Security security = new Security();
        security.setFullName("yusuf usman");
        security.setPhoneNumber("081");
        security.setEmail("yusuf.usman1003@gmail.com");
        security.setPassword("12345678");

        Security savedSecurity = securityRepository.save(security);

        assertNotNull(savedSecurity);
        assertEquals(1, savedSecurity.getId());
        assertEquals("yusuf usman", savedSecurity.getFullName());
        assertEquals("081", savedSecurity.getPhoneNumber());
        assertEquals("yusuf.usman1003@gmail.com", savedSecurity.getEmail());
        assertEquals("12345678", savedSecurity.getPassword());
    }

    @Test
    public void testSaveExistingSecurity_UpdatesSecurity() {
        Security security = new Security();
        security.setFullName("yusuf usman");
        security.setPhoneNumber("081");
        security.setEmail("yusuf.usman1003@gmail.com");
        security.setPassword("12345678");
        Security savedSecurity = securityRepository.save(security);

        savedSecurity.setFullName("ola tunji");
        Security updatedSecurity = securityRepository.save(savedSecurity);

        assertEquals(1, updatedSecurity.getId());
        assertEquals("ola tunji", updatedSecurity.getFullName());
        assertEquals("081", updatedSecurity.getPhoneNumber());
        assertEquals("yusuf.usman1003@gmail.com", updatedSecurity.getEmail());
        assertEquals("12345678", updatedSecurity.getPassword());
        assertEquals(1, securityRepository.count());
    }

    @Test
    public void testFindById() {
        Security security = new Security();
        security.setFullName("yusuf usman");
        security.setEmail("yusuf.usman1003@gmail.com");
        securityRepository.save(security);

        Security foundSecurity = securityRepository.findById(1).orElse(null);

        assertNotNull(foundSecurity);
        assertEquals(1, foundSecurity.getId());
        assertEquals("yusuf usman", foundSecurity.getFullName());
    }

    @Test
    public void testFindById_NotFound() {
        Security foundSecurity = securityRepository.findById(1).orElse(null);
        assertNull(foundSecurity);
    }

    @Test
    public void testFindByEmail() {
        Security security = new Security();
        security.setFullName("yusuf usman");
        security.setEmail("yusuf.usman1003@gmail.com");
        securityRepository.save(security);

        Security foundSecurity = securityRepository.findByEmail("yusuf.usman1003@gmail.com").orElse(null);

        assertNotNull(foundSecurity);
        assertEquals("yusuf usman", foundSecurity.getFullName());
        assertEquals("yusuf.usman1003@gmail.com", foundSecurity.getEmail());
    }

    @Test
    public void testFindByEmail_NotFound() {
        Security foundSecurity = securityRepository.findByEmail("nonexistent@example.com").orElse(null);
        assertNull(foundSecurity);
    }

    @Test
    public void testFindAll() {
        Security security1 = new Security();
        security1.setFullName("yusuf usman");
        security1.setEmail("yusuf.usman1003@gmail.com");
        securityRepository.save(security1);

        Security security2 = new Security();
        security2.setFullName("ola tunji");
        security2.setEmail("ola@gmail.com");
        securityRepository.save(security2);

        List<Security> allSecurities = securityRepository.findAll();

        assertEquals(2, allSecurities.size());
        assertEquals("yusuf usman", allSecurities.get(0).getFullName());
        assertEquals("ola tunji", allSecurities.get(1).getFullName());
    }

    @Test
    public void testDeleteById() {
        Security security1 = new Security();
        security1.setFullName("yusuf usman");
        security1.setEmail("yusuf.usman1003@gmail.com");
        securityRepository.save(security1);

        Security security2 = new Security();
        security2.setFullName("ola tunji");
        security2.setEmail("ola@gmail.com");
        securityRepository.save(security2);

        securityRepository.deleteById(1);

        assertEquals(1, securityRepository.count());
        assertFalse(securityRepository.findById(1).isPresent());
        assertTrue(securityRepository.findById(2).isPresent());
        assertEquals("ola tunji", securityRepository.findById(2).get().getFullName());
    }

    @Test
    public void testDeleteAll() {
        Security security1 = new Security();
        security1.setFullName("yusuf usman");
        security1.setEmail("yusuf.usman1003@gmail.com");
        securityRepository.save(security1);

        Security security2 = new Security();
        security2.setFullName("ola tunji");
        security2.setEmail("ola@gmail.com");
        securityRepository.save(security2);

        securityRepository.deleteAll();

        assertEquals(0, securityRepository.count());
        assertFalse(securityRepository.findById(1).isPresent());
        assertFalse(securityRepository.findById(2).isPresent());

        Security security3 = new Security();
        security3.setFullName("new security");
        Security savedSecurity = securityRepository.save(security3);
        assertEquals(1, savedSecurity.getId());
    }
}