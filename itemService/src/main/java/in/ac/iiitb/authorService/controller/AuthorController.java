package in.ac.iiitb.authorService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;

import in.ac.iiitb.authorService.models.Author;
import in.ac.iiitb.authorService.service.AuthorService;

@RestController
@CrossOrigin
@RequestMapping("author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    /*
        URL: POST /api/author/login

        Input: Login User Data
        {
            "loginId": "jasvin_james",
            "password": "Jasvin@123"
        }

        Description:
        Authenticates login of the specified Author and returns 401 if failed and 200 along with the Author object
        if successful.

        Passes JSON data as string to the login() method of AuthorService which returns the Author DB object as response
        If response is NULL it means that login failed due to authentication, so return 401 Status code
        Otherwise return 200 status code with the logged in Author object as the response
     */
    @PostMapping(
      path="login",
      consumes=MediaType.APPLICATION_JSON,
      produces=MediaType.APPLICATION_JSON
    )
    public ResponseEntity<Author> login(@RequestBody Author author) throws JsonProcessingException {
        Author loggedInAuthor = authorService.loginAuthor(author);

        if (loggedInAuthor == null)
            return new ResponseEntity<Author>(loggedInAuthor, HttpStatus.UNAUTHORIZED);
        else
            return new ResponseEntity<Author>(loggedInAuthor, HttpStatus.CREATED);
    }

    @PostMapping(
      path="register",
      consumes=MediaType.APPLICATION_JSON,
      produces=MediaType.APPLICATION_JSON
    )
    public ResponseEntity<Author> register(@RequestBody Author author) throws JsonProcessingException {
        Author registeredAuthor = authorService.registerAuthor(author);

        if (registeredAuthor == null)
            return new ResponseEntity<Author>(registeredAuthor, HttpStatus.UNAUTHORIZED);
        else
            return new ResponseEntity<Author>(registeredAuthor, HttpStatus.CREATED);
    }
}