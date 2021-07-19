package services;

import dataaccess.ScheduleDB;
import dataaccess.ShiftDB;
import dataaccess.UserDB;
import java.util.*;
import models.*;

public class ShiftService {

    public ArrayList<Shift> generateShifts(Calendar startDate, Calendar endDate, Schedule schedule, Role role) {

        ArrayList<Shift> shiftList = new ArrayList<>();
        int dayOfWeek;
        Date startDateShift;
        Date endDateShift;

        Calendar dayIncrement = Calendar.getInstance();
        dayIncrement.setTime(startDate.getTime());

        // Need to boolean add boolean for weekend
        int increment = 1;
        while (!dayIncrement.equals(endDate)) {

            startDateShift = dayIncrement.getTime();
            //dayIncrement.add(Calendar.DATE, 1);
            endDateShift = dayIncrement.getTime();

            // Constructing new shift object with date information
            Shift newShift = new Shift(0, startDateShift, endDateShift); // change constructor
            dayOfWeek = shiftDayOfWeekCheck(newShift);
            newShift.setRole(role);
            newShift.setNumberInBlock(increment);
            newShift.setDayOfWeek(dayOfWeek); // needs to be added
            newShift.setIsWeekend(weekendCheck(dayOfWeek));
            newShift.setIsHoliday(holidayCheck(newShift));

            //Testing adding sched to shift
            newShift.setSchedule(schedule);

            //fix this by removing personal sched
            //newShift.setPersonalSchedule(new PersonalSchedule(new User(0)));
            shiftList.add(newShift);

            // Incrementing to the next day
            dayIncrement.add(Calendar.DATE, 1);
            increment++;

        }

        return shiftList;

    }

    public ArrayList<Shift> sortShiftsForAssigning(ArrayList<Shift> shifts, ArrayList<User> users, ArrayList<Timeoff> approvedTimeOffs) {
        ArrayList<Shift> weekdays = new ArrayList<>();
        ArrayList<Shift> friday = new ArrayList<>();
        ArrayList<Shift> saturday = new ArrayList<>();
        ArrayList<Shift> sunday = new ArrayList<>();
        ArrayList<User> unScheduledUsers = new ArrayList<>();
        ArrayList<User> usersBeforeScheduling = users;
        unScheduledUsers.addAll(usersBeforeScheduling);
        ArrayList<Shift> shiftsFilled = new ArrayList<>();
        Integer[][] shiftCounter = new Integer[users.size()][2];
       
        for(int i = 0; i < users.size(); i++){
            shiftCounter[i][0] =  users.get(i).getUserID();
            shiftCounter[i][1] = 0;
        }
        UserDB userDB = new UserDB();
        User extender = null;
        try{
            extender = userDB.get("extender@gmail.com");
        } catch(Exception e){}
        
        boolean canWork = false;
        boolean worksFriday = false;
        int counter = 0;

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
            canWork = false;
            while (!canWork) {
                
                worksFriday = false;
                int rnd = new Random().nextInt(users.size());
                User userRandom = users.get(rnd);
                for (int z = 0; z < shiftsFilled.size(); z++) {
                    
                    if (shiftsFilled.get(z).getUser().getUserID() == userRandom.getUserID()) {
                        worksFriday = true;
                    }
                }
                if((shiftsFilled.size() / 2) == users.size()){
                    worksFriday = true;
                    canWork = false;
                    List<Shift> shiftList = new ArrayList<>();
                    shiftList = extender.getShiftList();
                    shiftList.add(friday.get(i));
                    shiftList.add(sunday.get(i));
                    extender.setShiftList(shiftList);
                    shiftsFilled.add(friday.get(i));
                    shiftsFilled.add(sunday.get(i));
                    
                }
                if(!worksFriday){
                    canWork = shiftAvalibiltyCheck(approvedTimeOffs, userRandom, friday.get(i));
                }
                if (canWork && !worksFriday) {
                    canWork = shiftAvalibiltyCheck(approvedTimeOffs, userRandom, sunday.get(i));
                    if (canWork) {
                        for(int z = 0; z < shiftCounter.length; z++){
                            if(shiftCounter[z][0] == userRandom.getUserID()){
                                if(shiftCounter[z][1] < 7){
                                    
                                
                                    shiftCounter[z][1]++;
                                    shiftCounter[z][1]++;
                                    friday.get(i).setUser(userRandom);
                                    sunday.get(i).setUser(userRandom);
                        
                                    List<Shift> shiftList = new ArrayList<>();
                                    shiftList = userRandom.getShiftList();
                                    shiftList.add(friday.get(i));
                                    shiftList.add(sunday.get(i));
                                    userRandom.setShiftList(shiftList);
                        
                                    shiftsFilled.add(friday.get(i));
                                    counter++;
                                    shiftsFilled.add(sunday.get(i));
                                    counter++;
                                } else{
                                    canWork = false;
                                } 
                            }
                        }

                    }
                }
            }
        }
        for (int i = 0; i < saturday.size(); i++) {
            canWork = false;
            while (!canWork) {
                int rnd = new Random().nextInt(unScheduledUsers.size());
                User userRandom = unScheduledUsers.get(rnd);
                canWork = shiftAvalibiltyCheck(approvedTimeOffs, userRandom, saturday.get(i));
                if (canWork) {
                    if (weekendAvalibilityCheck(shiftsFilled, saturday.get(i), userRandom)) {
                        for(int z = 0; z < shiftCounter.length; z++){
                            if(shiftCounter[z][0] == userRandom.getUserID()){
                                if(shiftCounter[z][1] < 7){
                                    shiftCounter[z][1]++;
                                    saturday.get(i).setUser(userRandom);
                                    List<Shift> shiftList = new ArrayList<>();
                                    shiftList = userRandom.getShiftList();
                                    shiftList.add(saturday.get(i));
                                    userRandom.setShiftList(shiftList);
                                    shiftsFilled.add(saturday.get(i));
                                    unScheduledUsers.remove(rnd);
                                } else{
                                    canWork = false;
                                }
                            }
                        } 
                    }

                }
            }
        }
        unScheduledUsers.clear();
        unScheduledUsers.addAll(usersBeforeScheduling);
        //if they work x numbers days in a row or if they have worked over 7 total days.1L
        //if they have a timeOffRequest 
        for (int i = 0; i < weekdays.size(); i++) {
            canWork = false;
            while (!canWork) {
                int rnd = new Random().nextInt(unScheduledUsers.size());
                User userRandom = unScheduledUsers.get(rnd);
                canWork = shiftAvalibiltyCheck(approvedTimeOffs, userRandom, weekdays.get(i));
                if (canWork) {
                    for(int z = 0; z < shiftCounter.length; z++){
                        if(shiftCounter[z][0] == userRandom.getUserID()){
                            if(shiftCounter[z][1] < 7){
                                shiftCounter[z][1]++;
                                weekdays.get(i).setUser(userRandom);
                                List<Shift> shiftList = new ArrayList<>();
                                shiftList = userRandom.getShiftList();
                                shiftList.add(weekdays.get(i));
                                userRandom.setShiftList(shiftList);
                                shiftsFilled.add(weekdays.get(i));
                    
                                canWork = true;
                            } else{
                                canWork = false;
                            }
                        }
                    }
                }
            }
        }
        return shiftsFilled;
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

    public void saveShifts(ArrayList<Shift> shiftList) {
        ShiftDB shiftDB = new ShiftDB();
        for(Shift shift : shiftList){
            try{
                shiftDB.insert(shift);
            } catch(Exception e){}
        }

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

    public boolean weekendAvalibilityCheck(ArrayList<Shift> shiftsFilled, Shift saturday, User user) {
        int userID = user.getUserID();
        int fridayShiftInt = 0;
        int sundayShiftInt = 0;
        int saturdayShiftInt = saturday.getNumberInBlock();
        boolean hasFriday = false;
        boolean hasSunday = false;

        for (Shift shift : shiftsFilled) {
            
            if (shiftDayOfWeekCheck(shift) == 1) {
                if (userID == shift.getUser().getUserID()) {
                    fridayShiftInt = shift.getNumberInBlock();
                    hasFriday = true;

                }
            } else if (shiftDayOfWeekCheck(shift) == 6) {
                if (userID == shift.getUser().getUserID()) {
                    sundayShiftInt = shift.getNumberInBlock();
                    hasSunday = true;

                }
            }

        }

        if (hasFriday && hasSunday) {
            if ((saturdayShiftInt - 1) == fridayShiftInt) {
                return false;
            }
            
        }
        return true;
    }
}
