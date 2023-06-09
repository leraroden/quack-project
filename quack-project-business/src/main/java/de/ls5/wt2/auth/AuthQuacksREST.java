package de.ls5.wt2.auth;

import de.ls5.wt2.conf.auth.permission.EditQuackPermission;
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
        String content = param.getContent();

        final User user = subject.getPrincipals().oneByType(User.class);
        final Quack newQuack = new Quack();

        newQuack.setAuthor(user);
        newQuack.setContent(content);
        newQuack.setPublishedOn(new Date());
        entityManager.persist(newQuack);
        return ResponseEntity.ok("New Quack was added");
    }

    @PutMapping(path = "{quackId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@PathVariable final long quackId,
                                         @RequestBody final Quack param){
        final Quack quack = this.entityManager.find(Quack.class, quackId);
        if (quack != null) {
            // TODO überprüfen ob der User der Author des Quacks ist
            //EditQuackPermission editQuackPermission = new EditQuackPermission();
            //if(SecurityUtils.getSubject().isPermitted(editQuackPermission)){
                quack.setContent(param.getContent());
                entityManager.flush();
                return ResponseEntity.ok("Quack was updated");
            //}
            //return ResponseEntity.status(HttpStatus.FORBIDDEN)
            //        .body("You are not authorized to edit this Quack");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "{quackId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@PathVariable final long quackId){
        final Quack quack = this.entityManager.find(Quack.class, quackId);
        if (quack != null) {
            // TODO überprüfen ob der User der Author des Quacks ist
            //EditQuackPermission editQuackPermission = new EditQuackPermission();
            //if(SecurityUtils.getSubject().isPermitted(editQuackPermission)){
                entityManager.remove(quack);
                return ResponseEntity.ok("Quack was deleted");
            //}
            //return ResponseEntity.status(HttpStatus.FORBIDDEN)
            //        .body("You are not authorized to delete this Quack");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
