package de.ls5.wt2.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DBNews.class)
public abstract class DBNews_ extends de.ls5.wt2.entity.DBIdentified_ {

	public static volatile SingularAttribute<DBNews, Date> publishedOn;
	public static volatile SingularAttribute<DBNews, String> headline;
	public static volatile SingularAttribute<DBNews, String> content;

	public static final String PUBLISHED_ON = "publishedOn";
	public static final String HEADLINE = "headline";
	public static final String CONTENT = "content";

}

