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

        HospitalService hospitalService = new HospitalService();
        UserService userService = new UserService();
        ShiftService shiftService = new ShiftService();

        ArrayList<User> acess = new ArrayList<>();
        ArrayList<User> trauma = new ArrayList<>();
        ArrayList<User> senior = new ArrayList<>();
        ArrayList<Shift> shifts = new ArrayList<>();
        Calendar endDate;

        endDate = findEndDate(startDate);
        ArrayList<Integer> rolesToLoad = hospitalService.findHospitalRoles(hospital);

        for (int roleID : rolesToLoad) {
            switch (roleID) {
                case 1:
                    acess = userService.findAllActiveUsersByRoleByHospital(roleID, hospital);
                    break;
                case 2:
                    trauma = userService.findAllActiveUsersByRoleByHospital(roleID, hospital);
                    break;
                case 3:
                    senior = userService.findAllActiveUsersByRoleByHospital(roleID, hospital);
                    break;
            }
        }

        Schedule schedule = new Schedule(0, startDate.getTime(), endDate.getTime());
        Schedule scheduleToLoad = null;

        schedule.setHospital(hospital);
        ScheduleDB scheduleDB = new ScheduleDB();
        List<Schedule> sList = new ArrayList<>();

        try {
            sList = scheduleDB.getAll();
        } catch (Exception e) {
        }

        if (sList.size() == 0) {
            try {
                scheduleDB.insert(schedule);
                scheduleToLoad = scheduleDB.getByScheduleID(1);
            } catch (Exception e) {
            }
        } else {
            try {
                scheduleDB.insert(schedule);
                scheduleToLoad = scheduleDB.getByScheduleID(sList.size() + 1);
            } catch (Exception e) {
            }
        }

        shifts = shiftService.generateShifts(startDate, endDate, schedule);
        System.out.println("XD");
        
        
        
        
        
        
        
        
        

        /* ArrayList<User> activeResidents = new ArrayList<>();
        ArrayList<User> accessResidents = new ArrayList<>();
        ArrayList<User> traumaResidents = new ArrayList<>();
        ArrayList<User> seniorResidents = new ArrayList<>();
        ArrayList<Timeoff> approvedTimeOffs = new ArrayList<>();
        ArrayList<Shift> shifts = new ArrayList<>();
        
       
        
        
         
         
           //TEST STARTS HERE
        
              

        
        //TEST ENDS HERE
        
        approvedTimeOffs = loadAllAprovedRequests(startDate, endDate);

        activeResidents = loadAllActiveResdients();

       /* for (int i = 0; i < activeResidents.size(); i++) {
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
**/
        return new Schedule();
    }

    public Calendar findEndDate(Calendar startDate) {

        Calendar endDate = Calendar.getInstance();
        endDate.setTime(startDate.getTime());
        endDate.add(Calendar.DAY_OF_MONTH, 28);

        return endDate;
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

    public boolean shiftAvalibiltyCheck(List<Timeoff> timeOffRequests, User user, Shift shift) {

        Date timeOffStartDate;
        Date timeOffEndDate;
        Date shiftStartTime = shift.getStartTime();
        boolean canWork = false;
        if (timeOffRequests.isEmpty()) {
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

    public ArrayList<PersonalSchedule> sortShiftsForAssigning(ArrayList<Shift> shifts, ArrayList<User> users, ArrayList<Timeoff> approvedTimeOffs, ArrayList<PersonalSchedule> personalSchedules) {
        ArrayList<Shift> weekdays = new ArrayList<>();
        ArrayList<Shift> friday = new ArrayList<>();
        ArrayList<Shift> saturday = new ArrayList<>();
        ArrayList<Shift> sunday = new ArrayList<>();
        ArrayList<User> fridaySundayShifts = new ArrayList<>();
        ArrayList<User> saturdayShifts = new ArrayList<>();
        ArrayList<User> weekdayShifts = new ArrayList<>();
        ArrayList<PersonalSchedule> personalSchedulesToReturn = new ArrayList<>();
        int personalScheduleShiftCounter = 0;
        boolean canWork = false;
        boolean worksFriday = false;

        for (int i = 0; i < shifts.size(); i++) {
            Shift currentShift = shifts.get(i);

            if (currentShift.getIsWeekend()) {
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
            } else {
                weekdays.add(currentShift);
            }
        }

        for (int i = 0; i < friday.size(); i++) {
            while (!canWork) {
                int rnd = new Random().nextInt(users.size());
                for (int z = 0; z < fridaySundayShifts.size(); z++) {
                    if (fridaySundayShifts.get(z).getUserID() == users.get(rnd).getUserID()) {
                        worksFriday = true;
                    }
                }
                canWork = shiftAvalibiltyCheck(approvedTimeOffs, users.get(rnd), friday.get(i));
                if (canWork && !worksFriday) {
                    canWork = shiftAvalibiltyCheck(approvedTimeOffs, users.get(rnd), sunday.get(i));
                    if (canWork) {
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
        for (int i = 0; i < saturday.size(); i++) {
            while (!canWork) {
                int rnd = new Random().nextInt(users.size());
                canWork = shiftAvalibiltyCheck(approvedTimeOffs, users.get(rnd), saturday.get(i));
                if (canWork) {
                    for (int z = 0; z < personalSchedules.size(); z++) {
                        PersonalSchedule currentPersonalSchedule = personalSchedulesToReturn.get(z);
                        int personalScheduleUserID = currentPersonalSchedule.getUser().getUserID();

                        if (users.get(rnd).getUserID() == personalScheduleUserID) {
                            ArrayList<Shift> currentPersonalScheduleShifts = new ArrayList<>(currentPersonalSchedule.getShiftList());
                            if (!currentPersonalScheduleShifts.isEmpty()) {

                                Date fridayShift = currentPersonalScheduleShifts.get(0).getStartTime();
                                Date sundayShift = currentPersonalScheduleShifts.get(1).getStartTime();

                                if (!saturday.get(i).getStartTime().after(fridayShift) && !saturday.get(i).getStartTime().before(sundayShift)) {
                                    personalSchedulesToReturn.add(loadShiftToPersonalSchedule(users.get(rnd), personalSchedules, saturday.get(i)));
                                }

                            } else {
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
        for (int i = 0; i < weekdays.size(); i++) {
            while (!canWork) {
                int rnd = new Random().nextInt(users.size());
                canWork = shiftAvalibiltyCheck(approvedTimeOffs, users.get(rnd), weekdays.get(i));
                if (canWork) {

                    personalSchedulesToReturn.add(loadShiftToPersonalSchedule(users.get(rnd), personalSchedules, weekdays.get(i)));
                }
            }
        }
        return personalSchedulesToReturn;
        //shiftAvalibiltyCheck(timeOffRequests, user, weekdays);

        //Calendar calendar = Calendar.getInstance();
        //calendar.setTime(weekdays.get(i).getStartTime());
    }


    //test
    public void saveSchedule(Schedule schedule) {
        ScheduleDB scdb = new ScheduleDB();
        try {

            scdb.insert(schedule);
        } catch (Exception e) {
            System.out.println("oh no");
        }
    }

    //test
    public Schedule loadSchedule(Date startDate, Hospital hospital) throws Exception {
        ScheduleDB scdb = new ScheduleDB();
        return scdb.getByStartDate(startDate, hospital);

    }

}
