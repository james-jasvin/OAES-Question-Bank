package in.ac.iiitb.itemService.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// import org.springframework.beans.factory.annotation.Autowired;

import lombok.Data;
import lombok.NoArgsConstructor;

import in.ac.iiitb.itemService.models.Author;
import in.ac.iiitb.itemService.repositories.AuthorRepository;

/*
    COR Phase 3:
    Check whether specified Author's loginId and password match with an Author object in the Database
    Return the selected Author object if successful else NULL
*/
@NoArgsConstructor
@Data
@Service
public class LoginService implements Handler {
    @Autowired
    private AuthorRepository authorRepository;
    
    Handler next;

    @Override
    public Author handle(Author author) {

        // Return author will given loginId in the database
        System.out.println("\n" + author + "\n" + author.getLoginId() + "\n");
        Optional<Author> dbAuthor = authorRepository.findByLoginId(author.getLoginId());
    
        // If no such author exists, return null to fail the COR
        if (dbAuthor.get() == null)
            return null;
        
        // Check whether the existing author's password matches with the one given
        // If not, then return to again fail the COR
        if (dbAuthor.get().getPassword() != author.getPassword())
            return null;

        // Return the Author object on successful login
        return dbAuthor.get();
    }
}
