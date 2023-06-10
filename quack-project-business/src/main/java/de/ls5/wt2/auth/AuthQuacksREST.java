package de.ls5.wt2.auth;

import de.ls5.wt2.entity.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.*;

@Transactional
@RestController
@RequestMapping(path = {"rest/auth/basic/quacks"})
public class AuthQuacksREST {

    @Autowired
    private EntityManager entityManager;

    // diese Methode ist nicht nötig, da sie in QuacksREST schon vorhanden ist
    @GetMapping(path = "all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Quack>> getAllQuacks(){

        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Quack> query = builder.createQuery(Quack.class);
        final Root<Quack> from = query.from(Quack.class);
        query.select(from);
        List<Quack> quacks = this.entityManager.createQuery(query).getResultList();
        return ResponseEntity.ok(quacks);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody final Quack param){

        final Subject subject = SecurityUtils.getSubject();
        final User author = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", subject.getPrincipals().toString())
                .getSingleResult();
        final String content = param.getContent();
        if(content != null){
            final Quack newQuack = new Quack();
            newQuack.setAuthor(author);
            newQuack.setContent(content);
            newQuack.setPublishedOn(new Date());
            entityManager.persist(newQuack);
            return ResponseEntity.ok("New Quack was added");
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(path = "{quackId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@PathVariable final long quackId,
                                         @RequestBody final Quack param){
        final Subject subject = SecurityUtils.getSubject();
        final Quack quack = this.entityManager.find(Quack.class, quackId);
        if (quack != null) {
            final User user= entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", subject.getPrincipals().toString())
                    .getSingleResult();
            // überprüfen ob User der Author des Quacks oder Admin ist
            if(quack.getAuthor().equals(user) || subject.hasRole("admin")){
                final String content = param.getContent();
                if(content != null){
                    quack.setContent(content);
                    entityManager.flush();
                    return ResponseEntity.ok("Quack was updated");
                }
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You are not authorized to update this Quack");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "{quackId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@PathVariable final long quackId){
        final Subject subject = SecurityUtils.getSubject();
        final Quack quack = this.entityManager.find(Quack.class, quackId);
        if (quack != null) {
            final User user= entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", subject.getPrincipals().toString())
                    .getSingleResult();
            // überprüfen ob User der Author des Quacks oder Admin ist
            if(quack.getAuthor().equals(user) || subject.hasRole("admin")){
                entityManager.remove(quack);
                return ResponseEntity.ok("Quack was deleted");
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You are not authorized to delete this Quack");
        }
        return ResponseEntity.notFound().build();
    }


}
