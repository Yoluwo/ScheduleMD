/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Hospital;
import models.Schedule;
import models.User;

/**
 *
 * @author epaul
 * ScheduleDB contains all database access methods required for the Schedule model.
 */
public class ScheduleDB {
    /**
     * getScheduleByID() method will get and return a specific schedule based on it's ID
     * @param scheduleID - ID for which schedule will be selected from the Database
     * @return schedule
     * @throws Exception 
     */
    public Schedule getByScheduleID(int scheduleID) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            Schedule schedule = em.createNamedQuery("Schedule.findByScheduleID", Schedule.class).setParameter("scheduleID", scheduleID).getSingleResult();
            return schedule;
        } finally {
            em.close();
        }
    }
    /**
     * findByIsActive() method will get and return a list of Schedules that have an active status
     * @param isUsed - True/False to select Active / inactive schedules from database
     * @return schedule
     * @throws Exception 
     */
    public List<Schedule> findByIsActive(boolean isUsed) throws Exception{
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try{
            List<Schedule> schedule = em.createNamedQuery("Schedule.findByIsUsed", Schedule.class).setParameter("isUsed", isUsed).getResultList();
            return schedule;
        } finally {
            em.close();
        }
    }
    /**
     * insert() method inserts and commits a new schedule into the database
     * @param schedule - Schedule to be inserted/commited to the database
     * @throws Exception 
     */
    public void insert(Schedule schedule) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
       
            trans.begin();
            em.persist(schedule);
            em.merge(schedule);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    /**
     * getAll() method will get and return all schedules in the database
     * @return schedule
     * @throws Exception 
     */
    public List<Schedule> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            List<Schedule> schedule = em.createNamedQuery("Schedule.findAll", Schedule.class).getResultList();
            return schedule;
        } finally {
            em.close();
        }

    }    
    /**
     * getByStartDate() method will get and return a specific schedule by both startDate, and hospitalID
     * @param startDate - StartDate for which the schedule will be selected from the database
     * @param hospital - Hospital for which schedule will be selected from the database
     * @return schedule
     * @throws Exception 
     */
    public Schedule getByStartDate(Date startDate, Hospital hospital) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            Schedule schedule = em.createNamedQuery("Schedule.findByStartDate", Schedule.class).setParameter("StartDate", startDate).getSingleResult();
            if(hospital.getHospitalID() == schedule.getHospital().getHospitalID()){
                return schedule;
            }
            
        } finally {
            em.close();
        }
        return null;
    }
    /**
     * update() will update a schedule object in the database to include newly added information
     * @param schedule - Schedule to be updated in the database
     * @throws Exception 
     */
    public void update(Schedule schedule) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.merge(schedule);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
}
