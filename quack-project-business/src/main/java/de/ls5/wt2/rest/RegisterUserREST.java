package de.ls5.wt2.rest;

import de.ls5.wt2.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
@RestController
@RequestMapping(path = "rest/users")
public class RegisterUserREST {

    @Autowired
    private EntityManager entityManager;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addUser(@RequestBody User user) {
        if(user.getUsername() != null && user.getPassword() != null) {
            final TypedQuery<User> foundUsers= entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", user.getUsername());
            List<User> users = foundUsers.getResultList();
            if(users.isEmpty()){
                entityManager.persist(user);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
