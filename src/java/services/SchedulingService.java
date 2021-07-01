/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.*;
import java.util.*;
import models.*;



/**
 *
 * @author Tom Skiff
 * @author Ethan Paul
 */

/*
    The SchedulingService class is used to perform queries to the database 
    for data on all residents, shifts, hospitals and status of requests.

    LocalDate localStartDate = LocalDate.of(2021, Month.JANUARY, 1);
    LocalDate localEndDate = LocalDate.of(2021, Month.JANUARY, 1);

    long noOfDaysDifference = ChronoUnit.DAYS.between(localStartDate, localEndDate);


*/


public class SchedulingService {
 

    public void generateSchedule(Calendar startDate, Hospital hospital){

        ArrayList<User> activeResidents = new ArrayList<>();
        ArrayList<User> accessResidents = new ArrayList<>();
        ArrayList<User> traumaResidents = new ArrayList<>();
        ArrayList<User> seniorResidents = new ArrayList<>();
        ArrayList<Timeoff> approvedTimeOffs = new ArrayList<>();
        ArrayList<Shift> shift = new ArrayList<>();
      

        Calendar endDate = startDate;
        endDate.add(Calendar.DAY_OF_MONTH, 28);
      
        approvedTimeOffs = loadAllAprovedRequests(startDate,endDate);

        activeResidents = loadAllActiveResdients();

        for(int i = 0; i < activeResidents.size(); i++){
           User currentUser = accessResidents.get(i);
           int currentUserRole = accessResidents.get(i).getRole().getRoleID();
        
            switch(currentUserRole) {
                case 1:
                    accessResidents.add(currentUser);
                break;

                case 2:
                    traumaResidents.add(currentUser);
                break;

                case 3:
                    seniorResidents.add(currentUser);
                break;
            }
        }

       // ArrayList<Personalschedule> acess = GeneratePersonalSchedule(shifts, accessResidents, approvedTimeOffs);
        

    }



        //2 weekends per block, housecall 7 in a block, homecall max 9 

    public ArrayList<User> loadAllActiveResdients() {
        UserDB userDB = new UserDB();
        
        //List of residents to check if active
        ArrayList<User> activeCheck = new ArrayList<>();

        //List of active residents to return
        ArrayList<User> activeResidents = new ArrayList<>();

        //Getting a list of all the residents in the database
        try {
            activeCheck = (ArrayList) userDB.getAll();
        } catch (Exception e) {
            //*** add sum here later
        }

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
    
    public ArrayList<Timeoff> loadAllAprovedRequests(Calendar start, Calendar end) {
        TimeoffDB timeOff = new TimeoffDB();
        ArrayList<Timeoff> timeOffToLoad = new ArrayList<>();
        ArrayList<Timeoff> approvedTimeOffs = new ArrayList<>();
        
        Date startDate= start.getTime();
        Date endDate = end.getTime();

        //Getting list of approved time off requests
        try {
            approvedTimeOffs = (ArrayList) timeOff.getIsApproved();
        } catch (Exception e) {
            //***change this later probs
        }
        //Looping through list of approved time off requests
        for(int i = 0; i > approvedTimeOffs.size(); i++){

            //Getting timeOffRequest object information
            Timeoff currentTimeOff = approvedTimeOffs.get(i);
            Date currentRequestStartDate = currentTimeOff.getStartDate();
            Date currentRequestEndDate = currentTimeOff.getEndDate();

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

    public ArrayList<Shift> generateShifts(Calendar startDate,Calendar endDate){
        
        ArrayList<Shift> shiftList = new ArrayList<>();
        Calendar dayIncrement = startDate;
        endDate.add(Calendar.DATE,1);
       
        //Need to boolean add boolean for weekend 
        while(!dayIncrement.equals(endDate)){

            //Setting booleans for if the shift is a weekend or a holiday
            boolean isWeekend = false;
            boolean isHoliday = false;

            //Getting the day of the week 
            int dayOfWeek = dayIncrement.get(Calendar.DAY_OF_WEEK);

            //Checking to see if the day of the week is a weekend(friday,saturday,sunday)
            if(dayOfWeek == 1 || dayOfWeek == 7 || dayOfWeek == 6){
                isWeekend = true;
            }

            //Creating date objects from the calender objects to be inserted into the shift contructor
            Date startDateShift = dayIncrement.getTime();
            dayIncrement.add(Calendar.DATE,1);
            Date endDateShift = dayIncrement.getTime();

            //Constructing new shift object with date information
            Shift newShift =  new Shift(0,startDateShift,endDateShift);//needs to be added : isWeekend,isHoldiay);
            shiftList.add(newShift);

            //Incrementing to the next day 
            dayIncrement.add(Calendar.DATE, 1);
        }

        return shiftList;

    }


    public void GeneratePersonalSchedule(ArrayList<Shift> shift,ArrayList<User> user,ArrayList<Timeoff> approvedTimeOffs){
        ArrayList<PersonalSchedule> personalSchedules = new ArrayList<>();


        for(int l = 0; l < user.size(); l++){
            User curUser = user.get(l);
            PersonalSchedule p = new PersonalSchedule();
            p.setUser(curUser);
            personalSchedules.add(p);
            ArrayList <PersonalSchedule> oldPersonalScheduleCollection = new ArrayList<>(curUser.getPersonalScheduleCollection()); 
            oldPersonalScheduleCollection.add(p);
            curUser.setPersonalScheduleCollection(oldPersonalScheduleCollection);
        }   
        for(int i = 0; i < shift.size(); i++){
            boolean canWork = false;
            Shift curShift = shift.get(i);
            while(!canWork){
                int rnd = new Random().nextInt(user.size());
                for(int b = 0; b < approvedTimeOffs.size(); b++){
                    User curUser = user.get(rnd);
                    if(curUser.getUserID() == approvedTimeOffs.get(b).getUser().getUserID()){
                        Date timeOffStartDate = approvedTimeOffs.get(b).getStartDate();
                        Date timeOffEndDate = approvedTimeOffs.get(b).getEndDate();
                        Date shiftStartTime = curShift.getStartTime();
                        // Test case: 
                        // timeOffStartDate = July 2, timeOffEndDate = July 6
                        // shiftStartTime = July 4
                        if(!timeOffStartDate.equals(shiftStartTime) || !timeOffEndDate.equals(shiftStartTime) ){
                            if(shiftStartTime.before(timeOffStartDate)) {
                                    //can work                                   
                                    user.remove(rnd);
                                    canWork = true;
                            }
                            else if(shiftStartTime.after(timeOffStartDate) && shiftStartTime.before(timeOffEndDate)){
                                //cant work
                            }
                            else if(shiftStartTime.after(timeOffStartDate) && shiftStartTime.after(timeOffEndDate)){
                                //can work
                                user.remove(rnd);
                                canWork = true;
                            }
                        }
                        else{
                            //cant work
                        }

                        if(canWork){
                            if(curShift.getIsWeekend()){
                              PersonalSchedule
                            }
                        }                        
                    }
                }
            }
            
        }

        
    
    }
}
