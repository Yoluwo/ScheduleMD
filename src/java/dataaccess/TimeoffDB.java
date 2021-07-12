package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Timeoff;


public class TimeoffDB {

    //Returns a lists of approved requests
    public List<Timeoff> getIsApproved() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            List<Timeoff> approvedTimeoffs = em.createNamedQuery("Timeoff.findByIsApproved", Timeoff.class).getResultList();
            return approvedTimeoffs;
        } finally {
            em.close();
        }
    }
    //Used for saving a new timeoff request to the database
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
    //Used for updating a prexisting timeoff request
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
    //Used for deleting a timeoff request, called when a request is disapproved
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