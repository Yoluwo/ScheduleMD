package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Timeoff;

/**
 * TimeoffDB contains all database access methods required for the TimeOff model.
 * model.
 *
 * @author epaul
 */
public class TimeoffDB {

    /**
     * getIsApproved() method will get and return a list of TimeOff request
     * which are approved
     *
     * @return approvedTimeoffs
     * @throws Exception
     */
    public List<Timeoff> getIsApproved() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            List<Timeoff> approvedTimeoffs = em.createNamedQuery("Timeoff.findByIsApproved", Timeoff.class).getResultList();
            return approvedTimeoffs;
        } finally {
            em.close();
        }
    }

    /**
     * getAll() method will get and return all TimeOff requests from the
     * database
     *
     * @return allTimeOffs
     * @throws Exception
     */
    public List<Timeoff> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            List<Timeoff> allTimeOffs = em.createNamedQuery("Timeoff.findAll", Timeoff.class).getResultList();
            return allTimeOffs;
        } finally {
            em.close();
        }

    }
    /**
     * getByTimeOffID() method will get and return a specific TimeOff Request based on the TimeOffID
     * @param timeOffID - ID of which TimeOffRequest will be selected from the database
     * @return timeOff
     * @throws Exception 
     */
    public Timeoff getByTimeOffID(int timeOffID) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        Timeoff timeOff = null;
        try {
            timeOff = em.createNamedQuery("Timeoff.findByTimeOffID", Timeoff.class).setParameter("timeOffID", timeOffID).getSingleResult();
            return timeOff;
        } finally {
            em.close();
        }
    }

    /**
     * insert() method inserts and commits a new TimeOff request into the database
     * @param timeoff - TimeOffRequest to be inserted/commited into the Database
     * @throws Exception 
     */
    public void insert(Timeoff timeoff) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.persist(timeoff);
            em.merge(timeoff);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }

    /**
     * update() will update a TimeOff object in the database to include newly added information
     * @param timeOffRequest - timeOffRequest to be updated in the database
     * @throws Exception 
     */
    public void update(Timeoff timeOffRequest) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.merge(timeOffRequest);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }

    /**
     * delete() method will delete a TimeOff object from the database
     * @param timeOffRequest - TimeOffRequest to be deleted from the database
     * @throws Exception 
     */
    public void delete(Timeoff timeOffRequest) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.remove(em.merge(timeOffRequest));
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }

}
