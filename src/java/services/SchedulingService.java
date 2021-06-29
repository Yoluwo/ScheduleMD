/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.*;
import javax.persistence.EntityManager;
import models.Hospital;
import models.Schedule;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import java.time.temporal.ChronoUnit; //***Idk what this is xD
import java.util.*;
import models.Timeoff;
import models.User;


/**
 *
 * @author Tom Skiff
 * @author Ethan Paul
 */

/*
    The SchedulingService class is used to perform queries to the database 
    for data on all residents, shifts, hospitals and status of requests.
*/


public class SchedulingService {
 

    public void generateSchedule(Date startDate, Date endDate, Hospital hospital){

        List<User> accessResidents = null;
        List<User> traumaResidents = null;
        List<User> seniorResidents = null;
        List<Timeoff> approvedTimeOffs = null;
        List<User> activeResidents;

        long daysBetween = DAYS.between(startDate,endDate); //***Missing proper import

        approvedTimeOffs = loadAllAprovedRequests(); //***Needs to be updated to reflect method below

        activeResidents = loadAllActiveResdients();
        loadResidentsByRole(activeResidents); //***Missing method
        
        for(int i = 0; i < activeResidents.size(); i++){
           User currentUser = accessResidents.get(i);
           if (currentUser.getRole().getRoleID() == 1) { //***This should be replaced by a switch statment
               accessResidents.add(currentUser);
           }
           else if (currentUser.getRole().getRoleID() == 2){
               traumaResidents.add(currentUser);
           }
           else if (currentUser.getRole().getRoleID() == 3){
               seniorResidents.add(currentUser);
           }
        }

        //2 weekends per block, housecall 7 in a block, homecall max 9 

    }

    public List<User> loadAllActiveResdients() {
        UserDB userDB = new UserDB();
        
        //List of residents to check if active
        List<User> activeCheck = new List<>(); //***Abstract need to specify type of List

        //List of active residents to return
        List<User> activeResidents = new List<>(); //***Abstract need to specify type of List

        //Getting a list of all the residents in the database
        activeCheck = userDB.getAll(); //***Exception here

        //Loop that check the list for active residents, then loads them into a list
        for(int i = 0; i > activeCheck.size(); i++){

            User currentUser = activeCheck.get(i);

            if(currentUser.getIsActive()){

                activeResidents.add(currentUser);
            }

        }
        //List of active residents to return
        return activeResidents;

    }
    
    //*** This needs to be removed
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
    
    //** This needs to be removed
    public static Schedule getShift(DateTime startDate, Integer hospitalID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            Schedule shift = em.find(Schedule.class, startDate);
        return shift;
        } finally {
            em.close();
        }
    }
    
    
    public List<Timeoff> loadAllAprovedRequests(Date startDate, Date endDate) {
        TimeoffDB timeOff = new TimeoffDB();
        List<Timeoff> timeOffToLoad = new List<>(); //***Abstract need to specify type of List
        List<Timeoff> approvedTimeOffs = null;
        //Getting list of approved time off requests
        try {
            approvedTimeOffs = timeOff.getIsApproved();
        } catch (Exception e) {
            //***change this later probs
        }
        //Looping through list of approved time off requests
        for(int i = 0; i > approvedTimeOffs.size(); i++){

            //Getting timeOffRequest object information
            Timeoff currentTimeOff = approvedTimeOffs.get(i);
            Date currentRequestStartDate = request.getStartDate(); //***Where request come from?
            Date currentRequestEndDate = request.getEndDate(); //***Where request come from?

            //Checking if the requst endDate is before the schedule startdate
            if(currentRequestEndDate.after(startDate)){
                //Checking if the start date is within the schedule period 
                if((currentRequestStartDate.before(startDate) || currentRequestStartDate.equals(startDate)) && currentRequestStartDate.before(endDate)){
                    timeOffToLoad.add(currentTimeOff);
                }
            }
        }
        return timeOffToLoad;
    }
    
}
