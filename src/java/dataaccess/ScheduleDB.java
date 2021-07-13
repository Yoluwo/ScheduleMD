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
 */
public class ScheduleDB {
    public Schedule getByScheduleID(int scheduleID) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            Schedule schedule = em.createNamedQuery("Schedule.findByScheduleID", Schedule.class).setParameter("scheduleID", scheduleID).getSingleResult();
            return schedule;
        } finally {
            em.close();
        }
    }
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
    public List<Schedule> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            List<Schedule> schedule = em.createNamedQuery("Schedule.findAll", Schedule.class).getResultList();
            return schedule;
        } finally {
            em.close();
        }

    }    
        
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
