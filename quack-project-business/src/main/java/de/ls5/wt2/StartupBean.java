package de.ls5.wt2;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import de.ls5.wt2.entity.Quack;
import de.ls5.wt2.entity.User;
import de.ls5.wt2.entity.DBNews;
import org.apache.shiro.authc.UsernamePasswordToken;
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

        /*
         * Die Inizialisierung von News hier kann später gelöscht werden.
         * Sie dient nur dazu, dass die News-Liste nicht leer ist und
         * wir keine Fehlermeldung bekommen.
         */
        // only initialize once
        final DBNews firstNewsItem = this.entityManager.find(DBNews.class, 1L);
        if (firstNewsItem == null) {
            final DBNews news = new DBNews();

            news.setHeadline("Startup");
            news.setContent("Startup Bean successfully executed");
            news.setPublishedOn(new Date());

            this.entityManager.persist(news);
        }


        // erstellt den Admin
        final User admin = new User();

        admin.setUsername("admin");
        admin.setPassword("admin");

        UsernamePasswordToken token = new UsernamePasswordToken(admin.getUsername(), admin.getPassword());
        token.setRememberMe(true);

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
