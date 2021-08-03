/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import javax.persistence.EntityManager;
import models.Role;

/**
 *
 * @author alexz
 */
public class RoleDB {
    public Role getByRoleName (String roleName) throws Exception {
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
