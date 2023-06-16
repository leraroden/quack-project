package de.ls5.wt2.auth;

import de.ls5.wt2.entity.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.*;

@Transactional
@RestController
@RequestMapping(path = {"rest/auth/basic/quacks"})
public class AuthQuacksREST {

    @Autowired
    private EntityManager entityManager;

    /*
     *    erzeugt einen neuen Quack, wenn der User angemeldet ist
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Quack> createQuack(@RequestBody final Quack param){

        final Subject subject = SecurityUtils.getSubject();
        final User author = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", subject.getPrincipals().toString())
                .getSingleResult();
        final String content = param.getContent();
        if(content != null){
            final Quack newQuack = new Quack();
            newQuack.setAuthor(author);
            newQuack.setAuthorName(author.getUsername());
            newQuack.setContent(content);
            newQuack.setPublishedOn(new Date());
            entityManager.persist(newQuack);
            return ResponseEntity.ok(newQuack);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /*
     *    gibt alle Quacks des Users zurück, wenn der User angemeldet ist
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Quack>> readAllUsersQuacks(){
        final Subject subject = SecurityUtils.getSubject();
        final User author = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", subject.getPrincipals().toString())
                .getSingleResult();
        final List<Quack> quacks = entityManager.createQuery("SELECT q FROM Quack q WHERE q.authorName = :userName", Quack.class)
                .setParameter("userName", author.getUsername())
                .getResultList();
        if( !quacks.isEmpty()){
            return ResponseEntity.ok(quacks);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /*
     *    gibt einen Quack zurück, wenn der User angemeldet ist und der Author des Quacks oder Admin ist
     */
    @GetMapping(path = "{userId}/{quackId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Quack> readQuackById(@PathVariable long userId, @PathVariable final long quackId){
        final Quack quack = entityManager.find(Quack.class, quackId);
        if(quack != null){
            final Subject subject = SecurityUtils.getSubject();
            final User author = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", subject.getPrincipals().toString())
                    .getSingleResult();
            // überprüfen ob User der Author des Quacks oder Admin ist
            if( (quack.getAuthor().equals(author) && userId == author.getId()) || subject.hasRole("admin")){
                quack.setAuthor(null);
                return ResponseEntity.ok(quack);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /*
     *    ändert einen existierenden Quack, wenn der angemeldete User Author des Quacks ist
     */
    @PutMapping(path = "{quackId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateQuackById(@PathVariable final long quackId,
                                         @RequestBody final Quack param){
        final Subject subject = SecurityUtils.getSubject();
        final Quack quack = entityManager.find(Quack.class, quackId);
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

    /*
     *    löscht einen existierenden Quack, wenn der angemeldete User Author des Quacks ist
     */
    @DeleteMapping(path = "{quackId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteQuackById(@PathVariable final long quackId){
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
