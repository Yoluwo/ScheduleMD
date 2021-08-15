package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.*;

/**
 * NotificationDB class that contains methods for retrieving and
 * inserting/updating notification objects in the database,for the Notification
 * model.
 *
 *
 * @author Thomas Skiffington
 */
public class NotificationDB {

    /**
     * insert method inserts and commits a new Notification object into the
     * database
     *
     * @param note Notification to insert
     * @throws Exception if note could not be inserted
     */
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

    /**
     * getAll method that retrieves all the Notification objects from the
     * database
     *
     * @return notifications
     * @throws Exception if notifications could not be retrieved
     */
    public List<Notification> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            List<Notification> notifications = em.createNamedQuery("Notification.findAll", Notification.class).getResultList();
            return notifications;
        } finally {
            em.close();
        }

    }

    /**
     * getByTimeOffID method which gets the Notification object from the
     * database with the matching ID
     *
     * @param notificationID ID of the notification to retrieve
     * @return notification
     * @throws Exception if notification could not be retrieved
     */
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

    /**
     * update method will update a Notification object in the database to
     * include newly added information
     *
     * @param notification
     * @throws Exception if notification could not be updated
     */
    public void update(Notification notification) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.merge(notification);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }

}
