package in.ac.iiitb.itemService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ac.iiitb.itemService.models.Author;
import in.ac.iiitb.itemService.repositories.AuthorRepository;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    // Save operation
    @Override
    public Author registerAuthor(Author author) {
        return authorRepository.save(author);
    } 

    // Fetch all Authors
    @Override
    public List<Author> getAuthors() {
        return (List<Author>) authorRepository.findAll();
    }

    /*
        - Takes in the JSON which contains author login data
        - Converts it to an object of class Author
        - Initiates Login Chain of Responsibility via the initiateCOR() method
        - If COR returns null, it means login failed, so return null
        - Otherwise return the logged in Author
    */
    @Override
    public Author loginAuthor(Author author) {
        Optional<Author> loggedInAuthor = authorRepository.findByLoginId(author.getLoginId());

        // If no login happens, then return null
        if (!loggedInAuthor.isPresent())
            return null;

        // Setting items to null to avoid cyclic dependency issues
        loggedInAuthor.get().setItems(null);

        // return loggedInAuthor;
        return loggedInAuthor.get();
    }
}