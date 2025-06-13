package data.repository;

import data.models.Visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Visitors implements VisitorRepository {
    private List<Visitor> visitors = new ArrayList<>();
    private int counter = 0;

    @Override
    public Visitor save(Visitor visitor) {
        if (isNew(visitor)) {
            visitor.setId(++counter);
            visitors.add(visitor);
        } else {
            update(visitor);
        }
        return visitor;
    }

    private boolean isNew(Visitor visitor) {
        return visitor.getId() == 0;
    }

    private void update(Visitor visitor) {
        delete(visitor.getId());
        visitors.add(visitor);
    }

    @Override
    public void delete(int id) {
        Visitor tempVisitor = new Visitor();
        tempVisitor.setId(id);
        delete(tempVisitor);
    }

    public void delete(Visitor visitor) {
        visitors.remove(visitor);
    }

    @Override
    public Optional<Visitor> findById(int id) {
        for (Visitor visitor : visitors) {
            if (visitor.getId() == id) {
                return Optional.of(visitor);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Visitor> findAll() {
        return new ArrayList<>(visitors);
    }

    @Override
    public long count() {
        return visitors.size();
    }

    @Override
    public void deleteAll() {
        visitors.clear();
        counter = 0;
    }
}