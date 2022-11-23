package in.ac.iiitb.itemService.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.ac.iiitb.itemService.models.Author;
import in.ac.iiitb.itemService.models.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
  List<Item> findByAuthor(Author author);
}
