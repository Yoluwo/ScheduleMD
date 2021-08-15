/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.util.List;
import models.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * ShiftDB class that contains all the methods for retrieving, updating, and inserting shift objects to and from the database.
 * ShiftDB is for the Shift Model.
 * @author epaul, Thomas Skiffington
 */
public class ShiftDB {

    /**
     * getByShiftID method that retrieves the shift object from the database
     * with the matching ID
     *
     * @param shiftID of shift to retrieve
     * @return shift
     * @throws Exception if shift could not be retrieved
     */
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

    /**
     * getAll method that retrieves all the Shift objects from the database
     *
     * @return shifts
     * @throws Exception if shifts could not be retrieved
     */
    public List<Shift> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            List<Shift> shifts = em.createNamedQuery("Shift.findAll", Shift.class).getResultList();
            return shifts;
        } finally {
            em.close();
        }

    }

    /**
     * insert method inserts and commits a new Shift object into the database
     *
     * @param shift to insert into database
     * @throws Exception if shift could not be inserted
     */
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

    /**
     * update will update a Shift object in the database to include newly added
     * information
     *
     * @param shift to update
     * @throws Exception if shift could not be updated
     */
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
