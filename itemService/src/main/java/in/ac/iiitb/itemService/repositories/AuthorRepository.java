package in.ac.iiitb.itemService.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.ac.iiitb.itemService.models.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer>{
  Optional<Author> findByLoginId(String loginId);
}
