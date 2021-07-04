package services;

import dataaccess.ScheduleDB;
import dataaccess.ShiftDB;
import java.util.*;
import models.*;

public class ShiftService {

    public ArrayList<Shift> generateShifts(Calendar startDate, Calendar endDate, Schedule schedule) {

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
            newShift.setSchedule(schedule);

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

    public void sortShiftsForAssigning(ArrayList<Shift> shifts, ArrayList<User> users, ArrayList<Timeoff> approvedTimeOffs) {
        ArrayList<Shift> weekdays = new ArrayList<>();
        ArrayList<Shift> friday = new ArrayList<>();
        ArrayList<Shift> saturday = new ArrayList<>();
        ArrayList<Shift> sunday = new ArrayList<>();
        
        ArrayList<Shift> shiftsFilled = new ArrayList<>();
       
   
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
                for (int z = 0; z < shiftsFilled.size(); z++) {
                    
                    if (shiftsFilled.get(z).getUser().getUserID() == users.get(rnd).getUserID()) {
                        worksFriday = true;
                    }
                }
                canWork = shiftAvalibiltyCheck(approvedTimeOffs, users.get(rnd), friday.get(i));
                
                if (canWork && !worksFriday) {
                    canWork = shiftAvalibiltyCheck(approvedTimeOffs, users.get(rnd), sunday.get(i));
                    if (canWork) {
                        
                        friday.get(i).setUser(users.get(rnd));
                        sunday.get(i).setUser(users.get(rnd));
                        
                        List<Shift> shiftList = new ArrayList<>();
                        shiftList = users.get(rnd).getShiftList();
                        shiftList.add(friday.get(i));
                        shiftList.add(sunday.get(i));
                        users.get(rnd).setShiftList(shiftList);
                        
                        shiftsFilled.add(friday.get(i));
                        shiftsFilled.add(sunday.get(i));
                        canWork = true;
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
                    if (weekendAvalibilityCheck(shiftsFilled, saturday.get(i), users.get(rnd))) {
                        
                        List<Shift> shiftList = new ArrayList<>();
                        shiftList = users.get(rnd).getShiftList();
                        shiftList.add(saturday.get(i));
                        users.get(rnd).setShiftList(shiftList);
                        canWork = true;
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

                    List<Shift> shiftList = new ArrayList<>();
                    shiftList = users.get(rnd).getShiftList();
                    shiftList.add(weekdays.get(i));
                    users.get(rnd).setShiftList(shiftList);
                    
                    canWork = true;
                }
            }
        }
        
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

    public void saveShift(Shift shift) {
        ShiftDB shiftdb = new ShiftDB();
        try {

            shiftdb.insert(shift);
        } catch (Exception e) {
            System.out.println("oh no");
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
            if ((saturdayShiftInt - 1) != fridayShiftInt) {
                return true;
            }
        }
        return false;
    }
}
