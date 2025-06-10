package data.repository;

import data.models.AccessCode;
import data.models.Resident;
import data.models.Visitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AccessCodesTest {
    private AccessCodeRepository accessCodeRepository;

    @BeforeEach
    public void setUp() {
        accessCodeRepository = new AccessCodes();
//        accessCodeRepository.deleteAll();
    }

    @Test
    public void testSaveAccessCode() {
        Resident resident = new Resident();
        resident.setId(1);
        resident.setFullName("yusuf usman");
        resident.setEmail("yusuf.usman1003@yahoo.com");

        Visitor visitor = new Visitor();
        visitor.setId(1);
        visitor.setFullName("olatunji abdulsalam");
        visitor.setEmail("yusuf.usman1003@gmail.com");

        AccessCode accessCode = new AccessCode("123456", resident);
        accessCode.setVisitor(visitor);

        AccessCode savedAccessCode = accessCodeRepository.save(accessCode);

        assertNotNull(savedAccessCode);
        assertEquals("123456", savedAccessCode.getCode());
        assertEquals(resident, savedAccessCode.getResident());
        assertEquals(visitor, savedAccessCode.getVisitor());
        assertEquals(1, accessCodeRepository.count());
    }

    @Test
    public void testFindAll() {
        Resident resident = new Resident();
        resident.setId(1);
        resident.setFullName("yusuf usman");
        resident.setEmail("yusuf.usman3001@gmail.com");

        Visitor visitor1 = new Visitor();
        visitor1.setId(1);
        visitor1.setFullName("yusuf usman");
        visitor1.setEmail("yusuf.usman1003@yahoo.com");

        Visitor visitor2 = new Visitor();
        visitor2.setId(2);
        visitor2.setFullName("olatunji abdulsalam");
        visitor2.setEmail("yusuf.usman1003@gmail.com");

        AccessCode accessCode1 = new AccessCode("123456", resident);
        accessCode1.setVisitor(visitor1);
        accessCodeRepository.save(accessCode1);

        AccessCode accessCode2 = new AccessCode("654321", resident);
        accessCode2.setVisitor(visitor2);
        accessCodeRepository.save(accessCode2);

        List<AccessCode> allAccessCodes = accessCodeRepository.findAll();

        assertEquals(2, allAccessCodes.size());
        assertEquals("123456", allAccessCodes.get(0).getCode());
        assertEquals("654321", allAccessCodes.get(1).getCode());
    }

    @Test
    public void testFindByCode() {
        Resident resident = new Resident();
        resident.setId(1);
        resident.setFullName("yusuf usman");
        resident.setEmail("yusuf.usman1003@yahoo.com");

        Visitor visitor = new Visitor();
        visitor.setId(1);
        visitor.setFullName("yusuf usman");
        visitor.setEmail("yusuf.usman1003@gmail.com");

        AccessCode accessCode = new AccessCode("123456", resident);
        accessCode.setVisitor(visitor);
        accessCodeRepository.save(accessCode);

        AccessCode foundAccessCode = accessCodeRepository.findByCode("123456").orElse(null);

        assertNotNull(foundAccessCode);
        assertEquals("123456", foundAccessCode.getCode());
        assertEquals(resident, foundAccessCode.getResident());
        assertEquals(visitor, foundAccessCode.getVisitor());
    }

    @Test
    public void testFindByCode_NotFound() {
        AccessCode foundAccessCode = accessCodeRepository.findByCode("123456").orElse(null);
        assertNull(foundAccessCode);
    }

    @Test
    public void testDeleteAll() {
        Resident resident = new Resident();
        resident.setId(1);
        resident.setFullName("olatunji abdulsalam");
        resident.setEmail("yusuf.usman1003@yahoo.com");

        Visitor visitor = new Visitor();
        visitor.setId(1);
        visitor.setFullName("yusuf usman");
        visitor.setEmail("yusuf.usman1003@gmail.com");

        AccessCode accessCode = new AccessCode("123456", resident);
        accessCode.setVisitor(visitor);
        accessCodeRepository.save(accessCode);

        accessCodeRepository.deleteAll();
        assertEquals(0, accessCodeRepository.count());
        assertTrue(accessCodeRepository.findAll().isEmpty());
    }

    @Test
    public void testCount() {
        assertEquals(0, accessCodeRepository.count());

        Resident resident = new Resident();
        resident.setId(1);
        resident.setFullName("yusuf usman");
        resident.setEmail("yusuf.usman1003@gmail.com");

        Visitor visitor = new Visitor();
        visitor.setId(1);
        visitor.setFullName("olatunji abdulsalam");
        visitor.setEmail("yusuf.usman1003@yahoo.com");

        AccessCode accessCode1 = new AccessCode("123456", resident);
        accessCode1.setVisitor(visitor);
        accessCodeRepository.save(accessCode1);

        assertEquals(1, accessCodeRepository.count());

        AccessCode accessCode2 = new AccessCode("654321", resident);
        accessCode2.setVisitor(visitor);
        accessCodeRepository.save(accessCode2);

        assertEquals(2, accessCodeRepository.count());
    }
}