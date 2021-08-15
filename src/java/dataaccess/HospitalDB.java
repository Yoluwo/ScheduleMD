package dataaccess;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.*;

/**
 * HospitalDB Class that is the database broker for the hospital object in the
 * database. Has methods to get and input hospital data to and from the database
 *
 * @author Thomas Skiffington
 */
public class HospitalDB {

    /**
     * getByHopitalID method that gets the hospital from the database where the
     * hospitalID equals to the hospitalID
     *
     * @param hospitalID ID of hospital to retrieve
     * @return hospital
     * @throws Exception if hospital could not be retrieved
     */
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

    /**
     * getByHospitalName method that gets the hospital object from the database
     * where the hospitalName equals the hospitalName in the database.
     *
     * @param hospitalName name of hospital to retrieve
     * @return hospital 
     * @throws Exception if hospital could not be retrieved
     */
    public Hospital getByHospitalName(String hospitalName) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        Hospital hospital = null;
        try {
            hospital = em.createNamedQuery("Hospital.findByHospitalName", Hospital.class).setParameter("hospitalName", hospitalName).getSingleResult();
            return hospital;
        } finally {
            em.close();
        }
    }

    /**
     * insert method that inserts a hospital object into the database
     *
     * @param hospital Hospital Object to insert
     * @throws Exception if hospital could not be inserted
     */
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
