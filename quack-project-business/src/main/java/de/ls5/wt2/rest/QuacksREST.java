package de.ls5.wt2.rest;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import de.ls5.wt2.entity.Quack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Transactional
@RestController
@RequestMapping(path = "rest/quacks/all")
public class QuacksREST {

    @Autowired
    private EntityManager entityManager;

    /*
     *    gibt alle Quacks zurück
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Quack>> readAllQuacks(){
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Quack> query = builder.createQuery(Quack.class);
        final Root<Quack> from = query.from(Quack.class);
        query.select(from);
        List<Quack> quacks = this.entityManager.createQuery(query).getResultList();
        return ResponseEntity.ok(quacks);
    }

    /*
     *  gibt alle Quacks in der chronologischen Reihenfolge zurück
     */
    @GetMapping(path = "sortedByDate",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Quack>> readAllQuacksSortedByDate(){
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Quack> query = builder.createQuery(Quack.class);
        final Root<Quack> from = query.from(Quack.class);
        query.select(from);
        query.orderBy(builder.desc(from.get("publishedOn")));
        List<Quack> quacks = this.entityManager.createQuery(query).getResultList();
        return ResponseEntity.ok(quacks);
    }
}
