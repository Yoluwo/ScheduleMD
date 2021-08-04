package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.*;

public class NotificationDB {

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

    public List<Notification> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            List<Notification> notifications = em.createNamedQuery("Notification.findAll", Notification.class).getResultList();
            return notifications;
        } finally {
            em.close();
        }

    }

    public Notification getByTimeOffID(int notificationID) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        Notification notification = null;
        try {
            notification = em.createNamedQuery("Notification.findByNotificationID", Notification.class).setParameter("notificationID", notificationID).getSingleResult();
            return notification;
        } finally {
            em.close();
        }
    }
    
   

}
