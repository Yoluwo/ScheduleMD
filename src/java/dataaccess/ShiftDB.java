/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;
import models.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author epaul
 */
public class ShiftDB {
        public Shift getByShiftID(int shiftID) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        Shift shift = null;
        try {
            shift = em.createNamedQuery("Shift.findByShiftID", Shift.class).setParameter("shiftID", shift).getSingleResult();
            return shift;
        } finally {
            em.close();
        }
    }

        public void insert(Shift shift) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
       

            trans.begin();
            em.persist(shift);
            em.merge(shift);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }

        public void update(Shift shift) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.merge(shift);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
}
