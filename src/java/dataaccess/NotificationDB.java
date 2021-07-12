package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.*;

public class NotificationDB {

    public List<Notification> getAllByUser(Integer userID) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
         try {
             List <Notification> notes = em.createNamedQuery("Notificaton.findByUserID", Notification.class).setParameter("userID", userID).getResultList();
             return notes;
         } finally {
             em.close();
         }
 
     }
     public void insert(Notification note) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
       

            trans.begin();
            em.persist(note);
            em.merge(note);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
}
