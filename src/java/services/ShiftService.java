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
}
