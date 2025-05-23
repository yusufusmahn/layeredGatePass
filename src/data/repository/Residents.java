package data.repository;

import data.models.Resident;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Residents implements ResidentRepository {
    private int counter = 0;
    private static List<Resident> residents = new ArrayList<>();

    @Override
    public Resident save(Resident resident) {
        if (isNew(resident)) {
            resident.setId(++counter);
            residents.add(resident);
        } else {
            update(resident);
        }
        return resident;
    }

    private boolean isNew(Resident resident) {
        return resident.getId() == 0;
    }

    private void update(Resident resident) {
            delete(resident.getId());
            residents.add(resident);

    }

    @Override
    public void delete(int id) {
        Resident tempResident = new Resident();
        tempResident.setId(id);
        delete(tempResident);
    }

    public void delete(Resident resident) {
        residents.remove(resident);
    }

    @Override
    public Optional<Resident> findById(int id) {
        for (Resident resident : residents) {
            if (resident.getId() == id) {
                return Optional.of(resident);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Resident> findAllByFullName(String fullName) {
        List<Resident> result = new ArrayList<>();
        for (Resident resident : residents) {
            if (resident.getFullName() != null && resident.getFullName().equals(fullName)) {
                result.add(resident);
            }
        }
        return result;
    }

    @Override
    public long count() {
        return residents.size();
    }

    @Override
    public List<Resident> findAll() {
        return new ArrayList<>(residents);
    }

    @Override
    public void deleteAll() {
        residents.clear();
        counter = 0;
    }

    @Override
    public Optional<Resident> findByEmail(String email) {
        for (Resident resident : residents) {
            if (resident.getEmail().equalsIgnoreCase(email)) {
                return Optional.of(resident);
            }
        }
        return Optional.empty();
    }

}
