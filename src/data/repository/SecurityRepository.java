package data.repository;

import data.models.Security;

import java.util.List;
import java.util.Optional;

public interface SecurityRepository {
    Security save(Security security);
    Optional<Security> findById(int id);
    Optional<Security> findByEmail(String email);
    void deleteAll();
    void deleteById(int id);
    List<Security> findAll();
    long count();
}