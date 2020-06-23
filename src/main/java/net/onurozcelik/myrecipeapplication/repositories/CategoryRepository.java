package net.onurozcelik.myrecipeapplication.repositories;

import net.onurozcelik.myrecipeapplication.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
