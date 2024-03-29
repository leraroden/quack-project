package de.ls5.wt2;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import de.ls5.wt2.entity.Quack;
import de.ls5.wt2.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class StartupBean implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        // erstellt den Admin
        final User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin");
        this.entityManager.persist(admin);

        // erstellt den ersten Quack
        final Quack quack = new Quack();
        quack.setAuthor(admin);
        quack.setAuthorName(admin.getUsername());
        quack.setContent("Willkommen bei Quackr!");
        quack.setPublishedOn(new Date());
        this.entityManager.persist(quack);

        // erstellt einen User
        final User user = new User();
        user.setUsername("user");
        user.setPassword("user");
        this.entityManager.persist(user);
    }
}
