package de.ls5.wt2.rest;

import de.ls5.wt2.entity.Quack;
import de.ls5.wt2.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

@Transactional
@RestController
@RequestMapping(path = "rest/reset")
public class Reset {

    @Autowired
    private EntityManager entityManager;

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> resetDatabase() {

        final List<Quack> quacks = entityManager.createQuery("SELECT q FROM Quack q WHERE q.authorName != :userName", Quack.class)
                .setParameter("userName", "admin")
                .getResultList();
        for (Quack quack : quacks) {
            entityManager.remove(quack);
        }

        final List<User> users = entityManager.createQuery("SELECT u FROM User u WHERE u.username NOT IN (:userNames)", User.class)
                .setParameter("userNames", Arrays.asList("admin", "user"))
                .getResultList();


        for (User user : users) {
            entityManager.remove(user);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
