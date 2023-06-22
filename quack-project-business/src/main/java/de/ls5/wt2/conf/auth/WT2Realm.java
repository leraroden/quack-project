package de.ls5.wt2.conf.auth;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import de.ls5.wt2.entity.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class WT2Realm extends AuthorizingRealm implements Realm {

    final static String REALM = "WT2";

    @Autowired
    private EntityManager entityManager;

    /*
     * liefert die Informationen über die Credentials und Principals eines Users
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if(token instanceof UsernamePasswordToken) {
            final String user = ((UsernamePasswordToken) token).getUsername();
            final char[] password = ((UsernamePasswordToken) token).getPassword();
            final List<User> result = this.entityManager.createQuery(
                            "SELECT u from User u WHERE u.username = :username AND u.password = :password", User.class).
                    setParameter("username", user).setParameter("password", String.valueOf(password)).getResultList();
            if (result.size() == 1) {
                return new SimpleAccount(user, password, WT2Realm.REALM);
            } else {
                throw new AuthenticationException();
            }
        }
        throw new CredentialsException();
    }

    /*
     * liefert die Informationen über die Rollen und Rechte eines Users
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        return new AuthorizationInfo() {

            @Override
            public Collection<String> getRoles() {
                // gibt Admin-Rolle zurück, wenn der User "admin" heißt
                if ("admin".equals(principals.getPrimaryPrincipal())) {
                    return Collections.singleton("admin");
                }
                return Collections.emptyList();
            }

            @Override
            public Collection<String> getStringPermissions() {
                return Collections.emptyList();
            }

            @Override
            public Collection<Permission> getObjectPermissions() { return Collections.emptyList();}
        };
    }
}
