package data.repository;

import data.models.Visitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VisitorsTest {
    private Visitors visitors;

    @BeforeEach
    public void setUp() {
        visitors = new Visitors();
        visitors.deleteAll();
    }

    @Test
    public void testSaveNewVisitor() {
        Visitor visitor = new Visitor();
        visitor.setFullName("yusuf usman");
        visitor.setPhoneNumber("081");
        visitor.setEmail("yusuf.usman1003@gmail.com");

        Visitor savedVisitor = visitors.save(visitor);

        assertNotNull(savedVisitor);
        assertEquals(1, savedVisitor.getId());
        assertEquals("yusuf usman", savedVisitor.getFullName());
        assertEquals("081", savedVisitor.getPhoneNumber());
        assertEquals("yusuf.usman1003@gmail.com", savedVisitor.getEmail());
        assertEquals(1, visitors.count());
    }

    @Test
    public void testSaveExistingVisitor_UpdatesVisitor() {
        Visitor visitor = new Visitor();
        visitor.setFullName("yusuf usman");
        visitor.setPhoneNumber("081");
        visitor.setEmail("yusuf.usman1003@gmail.com");
        Visitor savedVisitor = visitors.save(visitor);

        savedVisitor.setFullName("ola tunji");
        Visitor updatedVisitor = visitors.save(savedVisitor);

        assertEquals(1, updatedVisitor.getId());
        assertEquals("ola tunji", updatedVisitor.getFullName());
        assertEquals(1, visitors.count());
        assertEquals("ola tunji", visitors.findAll().get(0).getFullName());
    }

    @Test
    public void testFindAll() {
        Visitor visitor1 = new Visitor();
        visitor1.setFullName("ola tunji");
        visitor1.setPhoneNumber("081");
        visitor1.setEmail("yusuf.usman1003@yahoo.com");
        visitors.save(visitor1);

        Visitor visitor2 = new Visitor();
        visitor2.setFullName("yusuf usman");
        visitor2.setPhoneNumber("0811");
        visitor2.setEmail("yusuf.usman1003@gmail.com");
        visitors.save(visitor2);

        List<Visitor> allVisitors = visitors.findAll();

        assertEquals(2, allVisitors.size());
        assertEquals("ola tunji", allVisitors.get(0).getFullName());
        assertEquals("yusuf usman", allVisitors.get(1).getFullName());
    }

    @Test
    public void testFindById() {
        Visitor visitor = new Visitor();
        visitor.setFullName("yusuf usman");
        visitor.setPhoneNumber("081");
        visitor.setEmail("yusuf.usman1003@gmail.com");
        visitors.save(visitor);

        Visitor foundVisitor = visitors.findById(1).orElse(null);

        assertNotNull(foundVisitor);
        assertEquals(1, foundVisitor.getId());
        assertEquals("yusuf usman", foundVisitor.getFullName());
    }

    @Test
    public void testFindById_NotFound() {
        Visitor foundVisitor = visitors.findById(1).orElse(null);
        assertNull(foundVisitor);
    }

    @Test
    public void testDeleteById() {
        Visitor visitor = new Visitor();
        visitor.setFullName("yusuf usman");
        visitor.setPhoneNumber("081");
        visitor.setEmail("yusuf.usman1003@gmail.com");
        visitors.save(visitor);

        visitors.delete(1);
        assertEquals(0, visitors.count());
        assertFalse(visitors.findById(1).isPresent());
    }

    @Test
    public void testDeleteAll() {
        Visitor visitor = new Visitor();
        visitor.setFullName("yusuf usman");
        visitor.setPhoneNumber("0812345678");
        visitor.setEmail("yusuf.usman1003@gmail.com");
        visitors.save(visitor);

        visitors.deleteAll();
        assertEquals(0, visitors.count());
        assertTrue(visitors.findAll().isEmpty());
    }

    @Test
    public void testCount() {
        assertEquals(0, visitors.count());

        Visitor visitor1 = new Visitor();
        visitor1.setFullName("usman yusuf");
        visitors.save(visitor1);

        assertEquals(1, visitors.count());

        Visitor visitor2 = new Visitor();
        visitor2.setFullName("yusuf usman");
        visitors.save(visitor2);

        assertEquals(2, visitors.count());
    }
}