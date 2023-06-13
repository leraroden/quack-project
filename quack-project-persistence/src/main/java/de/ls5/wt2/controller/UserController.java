package de.ls5.wt2.controller;

import de.ls5.wt2.entity.User;
import de.ls5.wt2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@RestController()
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @PostMapping(path = "rest/users")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        if(user.getUsername() != null && user.getPassword() != null) {
            final TypedQuery<User> foundUsers= entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", user.getUsername());
            List<User> users = foundUsers.getResultList();
            if(users.isEmpty()){
                userRepository.save(user);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
