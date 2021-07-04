package services;

import models.*;
import dataaccess.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import servlets.PasswordServlet;

public class SchedulingService {

    public Schedule generateSchedule(Calendar startDate, Hospital hospital) {
        //Testingttessinfwefnwerjfbasjlkfbseadljkfbanserwjklfnbseajlfsdanjlfk;
      
        ArrayList<User> activeResidents = new ArrayList<>();
        ArrayList<User> accessResidents = new ArrayList<>();
        ArrayList<User> traumaResidents = new ArrayList<>();
        ArrayList<User> seniorResidents = new ArrayList<>();
        ArrayList<Timeoff> approvedTimeOffs = new ArrayList<>();
        ArrayList<Shift> shifts = new ArrayList<>();
        
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(startDate.getTime());
        endDate.add(Calendar.DAY_OF_MONTH, 28);
         
         
           //TEST STARTS HERE
        
        Schedule s = new Schedule(0, startDate.getTime(), endDate.getTime());
        Schedule s2 = null;
        s.setHospital(hospital); 
        ScheduleDB sDB =  new ScheduleDB();
        List<Schedule> sList = new ArrayList<>();
        try{
            sList = sDB.getAll();
        } catch(Exception e){}
        
        if(sList.size() == 0){
            try{
                sDB.insert(s);
                s2 = sDB.getByScheduleID(1);
            } catch(Exception e){}
        } else {
            try{
                sDB.insert(s);
                s2 = sDB.getByScheduleID(sList.size() + 1);
            } catch(Exception e){}
        }        

        
        //TEST ENDS HERE
        
        approvedTimeOffs = loadAllAprovedRequests(startDate, endDate);

        activeResidents = loadAllActiveResdients();

        for (int i = 0; i < activeResidents.size(); i++) {
            User currentUser = activeResidents.get(i);
            int currentUserRole = activeResidents.get(i).getRole().getRoleID();

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
        shifts = generateShifts(startDate, endDate,s2);
        
      
        ArrayList<PersonalSchedule> accessPersonalSchedules = GeneratePersonalSchedule(accessResidents);
        ArrayList<PersonalSchedule> traumaPersonalSchedules = GeneratePersonalSchedule(traumaResidents);
        ArrayList<PersonalSchedule> seniorPersonalSchedules = GeneratePersonalSchedule(seniorResidents);

        ArrayList<PersonalSchedule> accessPersonalSchedulesLoaded = sortShiftsForAssigning(shifts, accessResidents,
                approvedTimeOffs, accessPersonalSchedules);
        ArrayList<PersonalSchedule> traumaPersonalSchedulesLoaded = sortShiftsForAssigning(shifts, traumaResidents,
                approvedTimeOffs, traumaPersonalSchedules);
        ArrayList<PersonalSchedule> seniorPersonalSchedulesLoaded = sortShiftsForAssigning(shifts, seniorResidents,
                approvedTimeOffs, seniorPersonalSchedules);
        Schedule schedule = new Schedule();
        //schedule.setAccessPersonalSchedules(accessPersonalSchedulesLoaded);
      //  schedule.setTraumaPersonalSchedules(traumaPersonalSchedulesLoaded);
        //schedule.setSeniorPersonalSchedules(seniorPersonalSchedulesLoaded);
        return schedule;
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
        for (int i = 0; i < activeCheck.size(); i++) {

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

    public ArrayList<Shift> generateShifts(Calendar startDate, Calendar endDate,Schedule sc) {

        ArrayList<Shift> shiftList = new ArrayList<>();
        int dayOfWeek;
        Calendar dayIncrement = Calendar.getInstance();
        dayIncrement.setTime(startDate.getTime());
        Date startDateShift;
        Date endDateShift;
        
        
 

        
        // Need to boolean add boolean for weekend
        int increment = 1;
        while (!dayIncrement.equals(endDate)) {

            startDateShift = dayIncrement.getTime();
            //dayIncrement.add(Calendar.DATE, 1);
            System.out.println(dayIncrement.getTime());
            //System.out.println(endDate.());
            endDateShift = dayIncrement.getTime();

            // Constructing new shift object with date information
            Shift newShift = new Shift(0, startDateShift, endDateShift); // change constructor
            dayOfWeek = shiftDayOfWeekCheck(newShift);

            newShift.setNumberInBlock(increment);
            newShift.setDayOfWeek(dayOfWeek); // needs to be added
            newShift.setIsWeekend(weekendCheck(dayOfWeek));
            newShift.setIsHoliday(holidayCheck(newShift));
            
            //Testing adding sched to shift
            newShift.setSchedule(sc);
            
            //fix this by removing personal sched
            //newShift.setPersonalSchedule(new PersonalSchedule(new User(0)));
            shiftList.add(newShift);
            saveShift(newShift);

            // Incrementing to the next day
            dayIncrement.add(Calendar.DATE, 1);
            System.out.println(dayIncrement.getTime());
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
            try{
                ArrayList<PersonalSchedule> oldPersonalScheduleCollection = new ArrayList<>(
                        curUser.getPersonalScheduleList());
            
                oldPersonalScheduleCollection.add(p);
                curUser.setPersonalScheduleList(oldPersonalScheduleCollection);
            } catch(Exception e){}
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
        if(timeOffRequests.isEmpty()){
            return true;
        }
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
        ArrayList <Shift> weekdays = new ArrayList<>();
        ArrayList <Shift> friday = new ArrayList<>();
        ArrayList <Shift> saturday = new ArrayList<>();
        ArrayList <Shift> sunday = new ArrayList<>();
        ArrayList <User> fridaySundayShifts = new ArrayList<>();
        ArrayList <User> saturdayShifts = new ArrayList<>();
        ArrayList <User> weekdayShifts = new ArrayList<>();
        ArrayList <PersonalSchedule> personalSchedulesToReturn = new ArrayList<>();
        int personalScheduleShiftCounter = 0;
        boolean canWork = false;
        boolean worksFriday = false;
        
        for(int i = 0; i < shifts.size(); i++){
            Shift currentShift = shifts.get(i);
            
            if(currentShift.getIsWeekend()){
                int dayOfWeek = currentShift.getDayOfWeek();
                  switch (dayOfWeek) {
                        case 1:
                            sunday.add(currentShift);
                            break;
                        case 6:
                            friday.add(currentShift);
                            break;
                        case 7:
                            saturday.add(currentShift);
                            break;
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
                        PersonalSchedule ps = loadShiftToPersonalSchedule(users.get(rnd), personalSchedules, friday.get(i));
                        personalSchedulesToReturn.add(ps);
                        ps = loadShiftToPersonalSchedule(users.get(rnd), personalSchedules, sunday.get(i));
                        personalSchedulesToReturn.add(ps);
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
                            ArrayList <Shift> currentPersonalScheduleShifts = new ArrayList<>(currentPersonalSchedule.getShiftList());
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
                ArrayList<Shift> personalScheduleShifts = new ArrayList<>(currentPersonalSchedule.getShiftList());
                personalScheduleShifts.add(shift);
                currentPersonalSchedule.setShiftList(personalScheduleShifts);
            }
        }
        // Returning the personalSchedule object after adding the shift
        return currentPersonalSchedule;
    }
    //test
    public void saveSchedule(Schedule schedule){
        ScheduleDB scdb = new ScheduleDB();
           try{
               
              scdb.insert(schedule);
        }
        catch(Exception e){
            System.out.println("oh no");
        }
    }
    //test
      public Schedule loadSchedule(Date startDate,Hospital hospital) throws Exception{
        ScheduleDB scdb = new ScheduleDB();
        return  scdb.getByStartDate(startDate, hospital);
      
     
    }
      public void saveShift(Shift shift){
        ShiftDB shiftdb = new ShiftDB();
           try{
               
              shiftdb.insert(shift);
        }
        catch(Exception e){
            System.out.println("oh no");
        }
    }

}