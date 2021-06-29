package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.User;


public class UserDB {
    public User get(String email) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            User user = em.createNamedQuery("User.findByEmail", User.class).setParameter("email", email).getSingleResult();
            return user;
        } finally {
            em.close();
        }
    }
    public User getUserByID(int userID) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            User user = em.createNamedQuery("User.findByUserID", User.class).setParameter("userID", userID).getSingleResult();
            return user;
        } finally {
            em.close();
        }
    }
    
     public List<User> getAll() throws Exception {
       EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            List<User> users = em.createNamedQuery("User.findAll", User.class).getResultList();
            return users;
        } finally {
            em.close();
        }

    }
     
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