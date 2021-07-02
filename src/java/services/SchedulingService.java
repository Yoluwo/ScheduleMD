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
 * The SchedulingService class is used to perform queries to the database for
 * data on all residents, shifts, hospitals and status of requests.
 * 
 * 
 * 
 */

public class SchedulingService {

    public void generateSchedule(Calendar startDate, Hospital hospital) {

        ArrayList<User> activeResidents = new ArrayList<>();
        ArrayList<User> accessResidents = new ArrayList<>();
        ArrayList<User> traumaResidents = new ArrayList<>();
        ArrayList<User> seniorResidents = new ArrayList<>();
        ArrayList<Timeoff> approvedTimeOffs = new ArrayList<>();
        ArrayList<Shift> shifts = new ArrayList<>();

        Calendar endDate = startDate;
        endDate.add(Calendar.DAY_OF_MONTH, 28);

        approvedTimeOffs = loadAllAprovedRequests(startDate, endDate);

        activeResidents = loadAllActiveResdients();

        for (int i = 0; i < activeResidents.size(); i++) {
            User currentUser = accessResidents.get(i);
            int currentUserRole = accessResidents.get(i).getRole().getRoleID();

            switch (currentUserRole) {
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
        shifts = generateShifts(startDate, endDate);
        ArrayList<PersonalSchedule> accessPersonalSchedules = GeneratePersonalSchedule(accessResidents);
        ArrayList<PersonalSchedule> traumaPersonalSchedules = GeneratePersonalSchedule(traumaResidents);
        ArrayList<PersonalSchedule> seniorPersonalSchedules = GeneratePersonalSchedule(seniorResidents);

        ArrayList<PersonalSchedule> accessPersonalSchedulesLoaded = sortShiftsForAssigning(shifts, accessResidents,
                approvedTimeOffs, accessPersonalSchedules);
        ArrayList<PersonalSchedule> traumaPersonalSchedulesLoaded = sortShiftsForAssigning(shifts, traumaResidents,
                approvedTimeOffs, traumaPersonalSchedules);
        ArrayList<PersonalSchedule> seniorPersonalSchedulesLoaded = sortShiftsForAssigning(shifts, seniorResidents,
                approvedTimeOffs, seniorPersonalSchedules);

    }

    // 2 weekends per block, housecall 7 in a block, homecall max 9

    public ArrayList<User> loadAllActiveResdients() {
        UserDB userDB = new UserDB();

        // List of residents to check if active
        ArrayList<User> activeCheck = new ArrayList<>();

        // List of active residents to return
        ArrayList<User> activeResidents = new ArrayList<>();

        // Getting a list of all the residents in the database
        try {
            activeCheck = new ArrayList<>(userDB.getAll());
        } catch (Exception e) {
            // *** add sum here later
        }

        // Loop that check the list for active residents, then loads them into a list
        for (int i = 0; i > activeCheck.size(); i++) {

            User currentUser = activeCheck.get(i);

            if (currentUser.getIsActive()) {

                activeResidents.add(currentUser);
            }

        }
        // List of active residents to return
        return activeResidents;

    }

    public ArrayList<Timeoff> loadAllAprovedRequests(Calendar start, Calendar end) {
        TimeoffDB timeOff = new TimeoffDB();
        ArrayList<Timeoff> timeOffToLoad = new ArrayList<>();
        ArrayList<Timeoff> approvedTimeOffs = new ArrayList<>();

        Date startDate = start.getTime();
        Date endDate = end.getTime();

        // Getting list of approved time off requests
        try {
            approvedTimeOffs = new ArrayList<>(timeOff.getIsApproved());
        } catch (Exception e) {
            // ***change this later probs
        }
        // Looping through list of approved time off requests
        for (int i = 0; i > approvedTimeOffs.size(); i++) {

            // Getting timeOffRequest object information
            Timeoff currentTimeOff = approvedTimeOffs.get(i);
            Date currentRequestStartDate = currentTimeOff.getStartDate();
            Date currentRequestEndDate = currentTimeOff.getEndDate();

            // Checking if the requst endDate is before the schedule startdate
            if (currentRequestEndDate.after(startDate)) {
                // Checking if the start date is within the schedule period
                if ((currentRequestStartDate.before(startDate) || currentRequestStartDate.equals(startDate))
                        && currentRequestStartDate.before(endDate)) {
                    timeOffToLoad.add(currentTimeOff);
                }
            }
        }
        return timeOffToLoad;
    }

    public ArrayList<Shift> generateShifts(Calendar startDate, Calendar endDate) {

        ArrayList<Shift> shiftList = new ArrayList<>();
        int dayOfWeek;
        Calendar dayIncrement = startDate;
        Date startDateShift;
        Date endDateShift;

        endDate.add(Calendar.DATE, 1);

        // Need to boolean add boolean for weekend
        int increment = 1;
        while (!dayIncrement.equals(endDate)) {

            startDateShift = dayIncrement.getTime();
            dayIncrement.add(Calendar.DATE, 1);

            endDateShift = dayIncrement.getTime();

            // Constructing new shift object with date information
            Shift newShift = new Shift(0, startDateShift, endDateShift); // change constructor
            dayOfWeek = shiftDayOfWeekCheck(newShift);

            newShift.setNumberInBlock(increment);
            newShift.setDayOfWeek(dayOfWeek); // needs to be added
            newShift.setIsWeekend(weekendCheck(dayOfWeek));
            newShift.setIsHoliday(holidayCheck(newShift));

            shiftList.add(newShift);

            // Incrementing to the next day
            dayIncrement.add(Calendar.DATE, 1);
            increment++;
        }

        return shiftList;

    }

    public ArrayList<PersonalSchedule> GeneratePersonalSchedule(ArrayList<User> user) {
        // List for personal schedules we are creating
        ArrayList<PersonalSchedule> personalSchedules = new ArrayList<>();

        for (int i = 0; i < user.size(); i++) {
            // Getting a user from the list
            User curUser = user.get(i);
            // Creating a new schedule object for the user and adding it to the list to
            // return
            PersonalSchedule p = new PersonalSchedule();
            p.setUser(curUser);
            personalSchedules.add(p);
            // Getting the users list of personlSchedules and adding the new one
            ArrayList<PersonalSchedule> oldPersonalScheduleCollection = new ArrayList<>(
                    curUser.getPersonalScheduleCollection());
            oldPersonalScheduleCollection.add(p);
            curUser.setPersonalScheduleCollection(oldPersonalScheduleCollection);
        }
        // Returning List of personal Schedules
        return personalSchedules;
    }

    public int shiftDayOfWeekCheck(Shift shift) {

        Calendar shiftDayCal = Calendar.getInstance();
        shiftDayCal.setTime(shift.getStartTime());

        return shiftDayCal.get(Calendar.DAY_OF_WEEK);
    }

    public boolean holidayCheck(Shift shift) {
        boolean isHoliday = false;
        return isHoliday;
    }

    public boolean weekendCheck(int dayOfWeek) {
        switch (dayOfWeek) {
            case 1:
                // sunday
                return true;
            case 6:
                // friday
                return true;
            case 7:
                // saturday
                return true;
        }
        return false;
    }

    public boolean shiftAvalibiltyCheck(List<Timeoff> timeOffRequests, User user, Shift shift) {

        Date timeOffStartDate;
        Date timeOffEndDate;
        Date shiftStartTime = shift.getStartTime();
        boolean canWork = false;

        for (int i = 0; i < timeOffRequests.size(); i++) {

            timeOffStartDate = timeOffRequests.get(i).getStartDate();
            timeOffEndDate = timeOffRequests.get(i).getEndDate();

            if (user.getUserID() == timeOffRequests.get(i).getUser().getUserID()) {

                if (!timeOffStartDate.equals(shiftStartTime) || !timeOffEndDate.equals(shiftStartTime)) {

                    if (shiftStartTime.before(timeOffStartDate)) {
                        canWork = true;

                    } else if (shiftStartTime.after(timeOffStartDate) && shiftStartTime.before(timeOffEndDate)) {
                        // Cant work
                        return canWork;

                    } else if (shiftStartTime.after(timeOffStartDate) && shiftStartTime.after(timeOffEndDate)) {
                        canWork = true;
                    }
                }
            }

        }

        return canWork;
    }

    public ArrayList<PersonalSchedule> sortShiftsForAssigning(ArrayList<Shift> shifts,ArrayList<User> users, ArrayList<Timeoff> approvedTimeOffs, ArrayList<PersonalSchedule> personalSchedules){
        ArrayList <Shift> weekdays;
        ArrayList <Shift> friday;
        ArrayList <Shift> saturday;
        ArrayList <Shift> sunday;
        ArrayList <User> fridaySundayShifts;
        ArrayList <User> saturdayShifts;
        ArrayList <User> weekdayShifts;
        ArrayList <PersonalSchedule> personalSchedulesToReturn;
        int personalScheduleShiftCounter = 0;
        boolean canWork = false;
        boolean worksFriday = false;
        
        for(int i = 0; i < shifts.size(); i++){
            Shift currentShift = shifts.get(i);
            
            if(currentShift.getIsWeekend()){
                int dayOfWeek = currentShift.getDayOfWeek());
                  switch (dayOfWeek) {
                        case 1:
                            sunday.add(currentShift);
                        case 6:
                            friday.add(currentShift);
                        case 7:
                            saturday.add(currentShift);
                  }
            }
            else{
                weekdays.add(currentShift);
            }
        }
        
        for(int i = 0; i < friday.size(); i++){
            while(!canWork){
                int rnd = new Random().nextInt(users.size());
                for(int z = 0; z < fridaySundayShifts.size(); z++){
                    if(fridaySundayShifts.get(z).getUserID() == users.get(rnd).getUserID()){
                        worksFriday = true;
                    }
                }
                canWork = shiftAvalibiltyCheck(approvedTimeOffs, users.get(rnd), friday.get(i));
                if(canWork && !worksFriday){
                    canWork = shiftAvalibiltyCheck(approvedTimeOffs, users.get(rnd), sunday.get(i));
                    if(canWork){
                        personalSchedulesToReturn.add(loadShiftToPersonalSchedule(users.get(rnd), personalSchedules, friday.get(i)));
                        personalSchedulesToReturn.add(loadShiftToPersonalSchedule(users.get(rnd), personalSchedules, sunday.get(i)));
                        fridaySundayShifts.add(users.get(rnd));
                    }
                }
            }
        }
        canWork = false; 
        for(int i = 0; i < saturday.size(); i++){
            while(!canWork){
                int rnd = new Random().nextInt(users.size());
                canWork = shiftAvalibiltyCheck(approvedTimeOffs, users.get(rnd), saturday.get(i));
                if(canWork){
                    for(int z = 0; z < personalSchedules.size(); z++){
                        PersonalSchedule currentPersonalSchedule = personalSchedulesToReturn.get(z);
                        int personalScheduleUserID = currentPersonalSchedule.getUser().getUserID(); 
                    
                        if(users.get(rnd).getUserID() == personalScheduleUserID){
                            ArrayList <Shift> currentPersonalScheduleShifts = new ArrayList<>(currentPersonalSchedule.getShiftCollection());
                            if(!currentPersonalScheduleShifts.isEmpty()){
                                
                                Date fridayShift = currentPersonalScheduleShifts.get(0).getStartTime();
                                Date sundayShift = currentPersonalScheduleShifts.get(1).getStartTime();

                                if(!saturday.get(i).getStartTime().after(fridayShift) && !saturday.get(i).getStartTime().before(sundayShift)){
                                    personalSchedulesToReturn.add(loadShiftToPersonalSchedule(users.get(rnd), personalSchedules, saturday.get(i)));
                                }
                                
                                
                            }
                            else{
                                personalSchedulesToReturn.add(loadShiftToPersonalSchedule(users.get(rnd), personalSchedules, saturday.get(i)));
                            }
                           
                        }
                       
                    }
                    
                }
            }
        }

        //if they work x numbers days in a row or if they have worked over 7 total days.1L
        //if they have a timeOffRequest 
        canWork = false;
        for(int i = 0; i < weekdays.size(); i++){
            while(!canWork){
                int rnd = new Random().nextInt(users.size());
                canWork = shiftAvalibiltyCheck(approvedTimeOffs, users.get(rnd), weekdays.get(i));
                if(canWork){
                    
                    personalSchedulesToReturn.add(loadShiftToPersonalSchedule(users.get(rnd), personalSchedules, weekdays.get(i)));
                }
            }
        }
        return personalSchedulesToReturn;
        //shiftAvalibiltyCheck(timeOffRequests, user, weekdays);
         
        //Calendar calendar = Calendar.getInstance();
        //calendar.setTime(weekdays.get(i).getStartTime());

    }

    public void daysInARowCheck(ArrayList<PersonalSchedule> personalSchedules, User user, Shift shift) {

    }

    public PersonalSchedule loadShiftToPersonalSchedule(User user, ArrayList<PersonalSchedule> personalSchedules,
            Shift shift) {

        int currentUserID = user.getUserID();
        PersonalSchedule currentPersonalSchedule = null;
        int personalScheduleUserID;

        // Loop for searching through the personal schedules to find the one that
        // matches the user
        for (int i = 0; i < personalSchedules.size(); i++) {

            currentPersonalSchedule = personalSchedules.get(i);
            personalScheduleUserID = currentPersonalSchedule.getUser().getUserID();

            // If we find the personal schedule that belongs to the user we add the shift to
            // the users personal schedule
            if (currentUserID == personalScheduleUserID) {
                ArrayList<Shift> personalScheduleShifts = new ArrayList<>(currentPersonalSchedule.getShiftCollection());
                personalScheduleShifts.add(shift);
                currentPersonalSchedule.setShiftCollection(personalScheduleShifts);
            }
        }
        // Returning the personalSchedule object after adding the shift
        return currentPersonalSchedule;
    }
}