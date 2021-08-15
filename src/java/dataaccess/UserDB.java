package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.User;

/**
 * UserDB contains all database access methods required for the User model.
 * @author epaul
 */
public class UserDB {
    /**
     * get() method will get and return a User object which matches the parameter: email
     * @param email - The email which will be selected from the database to get the user
     * @return user
     * @throws Exception 
     */
    public User get(String email) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            User user = em.createNamedQuery("User.findByEmail", User.class).setParameter("email", email).getSingleResult();
            return user;
        } finally {
            em.close();
        }
    }
    /**
     * getUserByID() method will get and return a User object which matches the parameter: userID
     * @param userID - UserID will be used to get the User from the Database
     * @return user
     * @throws Exception 
     */
    public User getUserByID(int userID) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            User user = em.createNamedQuery("User.findByUserID", User.class).setParameter("userID", userID).getSingleResult();
            return user;
        } finally {
            em.close();
        }
    }
    /**
     * getAll() method will get and return a list of all Users in the database
     * @return users
     * @throws Exception 
     */
     public List<User> getAll() throws Exception {
       EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            List<User> users = em.createNamedQuery("User.findAll", User.class).getResultList();
            return users;
        } finally {
            em.close();
        }

    }
    /**
     * getAllActive() method will get and return all active/inactive Users from the database, depending on the isActive boolean being true/false
     * @param isActive - True/False depending on if wanting to get all active or inactive users
     * @return users
     * @throws Exception 
     */
    public List<User> getAllActive(boolean isActive) throws Exception{
         EntityManager em = DBUtil.getEmFactory().createEntityManager();
           try {
            List<User> users = em.createNamedQuery("User.findByIsActive", User.class).setParameter("isActive", isActive).getResultList();
            return users;
        } finally {
            em.close();
        }
    }
        
    /**
     * delete() method will delete a User object from the database
     * @param user - User to be deleted from the database
     * @throws Exception 
     */
    public void delete(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.remove(em.merge(user));
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }

    /**
     * update() will update a User object in the database to include newly added information
     * @param user - User to be updated in the database
     * @throws Exception 
     */
    public void update(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.merge(user);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }

    /**
     * insert() method inserts and commits a new User object into the database
     * @param user - User to be inserted/commited into the database
     * @throws Exception 
     */
    public void insert(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.persist(user);
            em.merge(user);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
}