package in.ac.iiitb.authorService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import in.ac.iiitb.authorService.models.Author;
import in.ac.iiitb.authorService.repositories.AuthorRepository;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    // Save operation
    @Override
    public Author registerAuthor(Author author) {
        try {
            return authorRepository.save(author);
        }
        // In case any DB constraints are violated while inserting
        catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return null;
        }
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

        if (loggedInAuthor.get().getPassword().equals(author.getPassword()))
            return loggedInAuthor.get();
        else
            return null;
    }
}