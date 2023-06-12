package de.ls5.wt2.conf.auth.permission;

import de.ls5.wt2.entity.Quack;
import de.ls5.wt2.entity.User;
import org.apache.shiro.authz.Permission;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class EditQuackPermission implements Permission{

    private Quack quack;
    private User currentUser;

    @Autowired
    private EntityManager entityManager;

    // TODO: Diese Klasse soll die Berechtigung prüfen, ob der aktuelle User den Quack bearbeiten und löschen darf.

    public EditQuackPermission(){}

    @Override
    public boolean implies(Permission p) {

        return false;
    }
}
