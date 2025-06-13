package services;

import data.models.Visitor;
import data.repository.VisitorRepository;
import data.repository.Visitors;
import dtos.responses.FindVisitorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VisitorServiceImplTest {
    private VisitorService visitorService;
    private VisitorRepository visitorRepository;

    @BeforeEach
    public void setUp() {
        visitorRepository = new Visitors();
        visitorService = new VisitorServiceImpl(visitorRepository);
        visitorRepository.deleteAll();
    }

    @Test
    public void testFindById_Success() {
        Visitor visitor = new Visitor();
        visitor.setFullName("yusuf usman");
        visitor.setPhoneNumber("08169025216");
        visitor.setEmail("yusuf.usman1003@gmail.com");
        Visitor savedVisitor = visitorRepository.save(visitor);

        FindVisitorResponse response = visitorService.findById(savedVisitor.getId());

        assertNotNull(response);
        assertEquals(1, response.getVisitorId());
        assertEquals("yusuf usman", response.getFullName());
        assertEquals("08169025216", response.getPhoneNumber());
        assertEquals("yusuf.usman1003@gmail.com", response.getEmail());
    }

    @Test
    public void testFindById_InvalidId() {
        assertThrows(IllegalArgumentException.class, () -> visitorService.findById(0));
    }

    @Test
    public void testFindById_NotFound() {
        assertThrows(IllegalArgumentException.class, () -> visitorService.findById(1));
    }
}