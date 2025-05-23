package data.repository;

import data.models.Resident;

import java.util.List;
import java.util.Optional;

public interface ResidentRepository {
    Resident save(Resident resident);
    void delete(int id);
    Optional<Resident> findById(int id);
    List<Resident> findAllByFullName(String fullName);
    long count();
    List<Resident> findAll();
    void deleteAll();
    Optional<Resident> findByEmail(String email);
}
