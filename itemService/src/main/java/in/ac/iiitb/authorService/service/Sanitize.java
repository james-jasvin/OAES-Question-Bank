package in.ac.iiitb.authorService.service;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import lombok.Data;
import lombok.NoArgsConstructor;

import in.ac.iiitb.authorService.models.Author;

/*
    COR Phase 2:
    Cleans the user login ID of SQL injection characters via Jsoup and calls the next 
    handler in the chain
*/
@Data
@NoArgsConstructor
public class Sanitize implements Handler {
    Handler next;

    @Override
    public Author handle(Author author) {
        if (next != null) {
            author.setLoginId(Jsoup.clean(author.getLoginId(), Safelist.basic()));
            return next.handle(author);
        }
        return null;
    }
}
