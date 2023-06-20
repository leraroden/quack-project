package de.ls5.wt2.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends de.ls5.wt2.entity.DBIdentified_ {

	public static volatile ListAttribute<User, Quack> quackList;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, String> username;

	public static final String QUACK_LIST = "quackList";
	public static final String PASSWORD = "password";
	public static final String USERNAME = "username";

}

