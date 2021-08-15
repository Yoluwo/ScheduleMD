
package dataaccess;

import javax.persistence.EntityManager;
import models.Role;

/**
 * RoleDB class which contains a method to retrieve role from the database
 * For the Role model.
 * @author alexz
 */
public class RoleDB {

    /**
     * getByRoleName method that takes in a roleName string and retrieves the
     * role with the matching roleName from the database
     *
     * @param roleName
     * @return Role role
     * @throws Exception if role could not be retrieved
     */
    public Role getByRoleName(String roleName) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        Role role = null;
        try {
            role = em.createNamedQuery("Role.findByRoleName", Role.class).setParameter("roleName", roleName).getSingleResult();
            return role;
        } finally {
            em.close();
        }
    }
}
