/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.DBUtil;
import javax.persistence.EntityManager;
import models.Hospital;
import models.Schedule;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;


/**
 *
 * @author Kilanga Maxwell
 */

/*
    The SchedulingService class is used to perform queries to the database 
    for data on all residents, shifts, hospitals and status of requests.
*/


public class SchedulingService {
    /**
    public getAllResidents() {
        
    }
    */
    /**
    public getAllResidents(Integer RoleID) {
        
    }
    
    public getAllActiveResdients() {
        
    }
     * @param hospitalID
     * @return  **/
    
    public static Hospital getHospital(Integer hospitalID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            Hospital hospital;
            hospital = em.find(Hospital.class, hospitalID);
            return hospital;
        } finally {
            em.close();
        }
        
    }
    
    public static Schedule getShift(DateTime startDate, Integer hospitalID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            Schedule shift = em.find(Schedule.class, startDate);
        return shift;
        } finally {
            em.close();
        }
    }
    
    /**
    public getAllAprovedRequests(DateTime startDate) {
        
    }
    
    public getAllDeniedRequests() {
        
    }
    
    public getAllPendingRequests() {
        
    } */
}
