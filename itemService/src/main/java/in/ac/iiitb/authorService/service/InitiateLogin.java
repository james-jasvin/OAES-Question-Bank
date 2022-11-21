package in.ac.iiitb.authorService.service;

import lombok.Data;
import lombok.NoArgsConstructor;

import in.ac.iiitb.authorService.models.Author;

/*
    COR Phase 1:
    Checks whether Author's login ID and password and non-empty and passes request to 
    the next handler in the chain. Otherwise returns null
*/
@Data
@NoArgsConstructor
public class InitiateLogin implements Handler {

    Handler next;

    @Override
    public Author handle(Author author) {
        if (author.getLoginId().equals("") || author.getPassword().equals(""))
            return null;
        if (next != null)
            return next.handle(author);
        return null;
    }
}
