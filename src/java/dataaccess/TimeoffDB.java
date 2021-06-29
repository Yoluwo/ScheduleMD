package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import models.Timeoff;


public class TimeoffDB {
    public List<Timeoff> getIsApproved() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            List<Timeoff> approvedTimeoffs = em.createNamedQuery("Timeoff.findByIsApproved", Timeoff.class).getResultList();
            return approvedTimeoffs;
        } finally {
            em.close();
        }
    }
}