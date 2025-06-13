package services;

import data.models.Visitor;
import data.repository.VisitorRepository;
import dtos.responses.FindVisitorResponse;
import utils.Mapper;


public class VisitorServiceImpl implements VisitorService {
    private final VisitorRepository visitorRepository;

    public VisitorServiceImpl(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    @Override
    public FindVisitorResponse findById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID: " + id);
        }

        Visitor foundVisitor = visitorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Visitor with ID " + id + " not found"));

        return Mapper.mapToFindVisitorResponse(foundVisitor);
    }
}