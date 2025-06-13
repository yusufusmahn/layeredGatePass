package data.repository;

import data.models.Visitor;

import java.util.List;
import java.util.Optional;

public interface VisitorRepository {
    Visitor save(Visitor visitor);
    void delete(int id);
    Optional<Visitor> findById(int id);
    List<Visitor> findAll();
    long count();
    void deleteAll();
}