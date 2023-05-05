package de.ls5.wt2;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import de.ls5.wt2.entity.DBNews;
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
        final DBNews firstNewsItem = this.entityManager.find(DBNews.class, 1L);

        // only initialize once
        if (firstNewsItem == null) {
            final DBNews news = new DBNews();

            news.setHeadline("Startup");
            news.setContent("Startup Bean successfully executed");
            news.setPublishedOn(new Date());

            this.entityManager.persist(news);
        }
    }
}
