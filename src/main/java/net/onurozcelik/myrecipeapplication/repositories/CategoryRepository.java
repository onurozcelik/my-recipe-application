package net.onurozcelik.myrecipeapplication.repositories;

import net.onurozcelik.myrecipeapplication.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    public Optional<Category> findByDescription(String description);
}
