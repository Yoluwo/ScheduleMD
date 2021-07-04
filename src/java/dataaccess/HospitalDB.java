/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.*;


/**
 *
 * @author 743851
 */
public class HospitalDB {
    
        public Hospital getByHospitalID(int hospitalID) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        Hospital hospital = null;
        try {
            hospital = em.createNamedQuery("Hospital.findByHospitalID", Hospital.class).setParameter("hospitalID", hospitalID).getSingleResult();
            return hospital;
        } finally {
            em.close();
        }
    }
        
        public void insert(Hospital hospital) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
       
            trans.begin();
            em.persist(hospital);
            em.merge(hospital);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
        
        
}
