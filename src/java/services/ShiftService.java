package services;

import dataaccess.NotificationDB;
import dataaccess.ScheduleDB;
import dataaccess.ShiftDB;
import dataaccess.UserDB;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.*;

/**
 * ShiftService class that contains methods to create, alter, save and update
 * shift objects
 *
 * @author Thomas Skiffington, ePaul
 */
public class ShiftService {

    /**
     * generateShifts method which takes in parameters needed to create a set of
     * shifts for a schedule, and creates and returns all the shift objects
     * needed for that schedule
     *
     * @param startDate startDate of schedule
     * @param endDate endDate of schedule
     * @param schedule schedule to generate shifts for
     * @param role roleType that we are generating shifts for (access, trauma or
     * senior)
     * @return shiftList list of generated shifts for the given schedule
     */
    public ArrayList<Shift> generateShifts(Calendar startDate, Calendar endDate, Schedule schedule, Role role) {

        ArrayList<Shift> shiftList = new ArrayList<>();
        int dayOfWeek;
        Date startDateShift;
        Date endDateShift;

        Calendar dayIncrement = Calendar.getInstance();
        dayIncrement.setTime(startDate.getTime());

        // Loop that goes through all the days in the scheduling period and creates shift objects for them
        int increment = 1;
        while (!dayIncrement.equals(endDate)) {

            //Getting shiftDates in the form of Date objects
            startDateShift = dayIncrement.getTime();
            endDateShift = dayIncrement.getTime();

            // Constructing new shift object with date information
            Shift newShift = new Shift(0, startDateShift, endDateShift);
            dayOfWeek = shiftDayOfWeekCheck(newShift);
            newShift.setRole(role);
            newShift.setNumberInBlock(increment);
            newShift.setDayOfWeek(dayOfWeek);
            newShift.setIsWeekend(weekendCheck(dayOfWeek));
            newShift.setIsHoliday(holidayCheck(newShift));

            //Setting the shifts schedule to the schedule given as a parameter
            newShift.setSchedule(schedule);

            //Adding the new shift to the shiftList to return
            shiftList.add(newShift);

            // Incrementing to the next day
            dayIncrement.add(Calendar.DATE, 1);
            increment++;

        }
        //List of generated shifts to return
        return shiftList;

    }

    /**
     * sortShiftsForAssigning method that takes the shifts generated for a new
     * schedule and fills them with users in accordance to the PARA rules
     *
     * @param shifts list of shifts to add users to
     * @param users list of users to add to shifts
     * @param approvedTimeOffs list of approvedTimeOffs to check if a user can
     * be scheduled on a shift or not
     * @return shiftsFilled list of shifts that have been filled with users
     */
    public ArrayList<Shift> sortShiftsForAssigning(ArrayList<Shift> shifts, ArrayList<User> users, ArrayList<Timeoff> approvedTimeOffs) {
        //Creating ArrayLists for weekdays and weekends
        ArrayList<Shift> weekdays = new ArrayList<>();
        ArrayList<Shift> friday = new ArrayList<>();
        ArrayList<Shift> saturday = new ArrayList<>();
        ArrayList<Shift> sunday = new ArrayList<>();
        //List of users that have not been scheduled
        ArrayList<User> unScheduledUsers = new ArrayList<>();
        ArrayList<User> usersBeforeScheduling = users;
        unScheduledUsers.addAll(usersBeforeScheduling);
        //List to return after shifts have been filled with users
        ArrayList<Shift> shiftsFilled = new ArrayList<>();
        Integer[][] shiftCounter = new Integer[users.size()][2];

        //filling a list of users and their amount of shifts for counting to see if the user has been scheduled for 7 shifts 
        for (int i = 0; i < users.size(); i++) {
            shiftCounter[i][0] = users.get(i).getUserID();
            shiftCounter[i][1] = 0;
        }
        UserDB userDB = new UserDB();
        //Getting the extender user to fill shifts that need an extender
        User extender = null;
        try {
            extender = userDB.get("extender@gmail.com");
        } catch (Exception e) {
        }

        boolean canWork = false;
        boolean worksFriday = false;

        //Sorting all of the shifts into fridays, saturdays, sundays and weekdays and placing them into their own lists
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
        //Booking in all of the friday and sunday shifts in the scheduling period, if a user works friday they will also work the corresponding sunday
        for (int i = 0; i < friday.size(); i++) {
            canWork = false;
            //Looks for a user that can work the friday, exits when the person is found and canWork = true
            while (!canWork) {
                worksFriday = false;
                //Getting random number to choose a random person to work
                int rnd = new Random().nextInt(users.size());
                User userRandom = users.get(rnd);
                //Checking if they already work a friday/sunday, if they work one they cant work another
                for (int z = 0; z < shiftsFilled.size(); z++) {

                    if (shiftsFilled.get(z).getUser().getUserID() == userRandom.getUserID()) {
                        worksFriday = true;
                    }
                }
                // If = true then no one is avalibale to work friday/sunday, so we fill the shift with an extender
                if ((shiftsFilled.size() / 2) == users.size()) {
                    worksFriday = true;
                    canWork = false;
                    List<Shift> shiftList = new ArrayList<>();
                    shiftList = extender.getShiftList();
                    shiftList.add(friday.get(i));
                    shiftList.add(sunday.get(i));
                    //Adding the extender to the shifts
                    extender.setShiftList(shiftList);
                    shiftsFilled.add(friday.get(i));
                    shiftsFilled.add(sunday.get(i));

                }
                //If the user does not work on a friday, we check their time off requests to see if they have the friday booked off
                if (!worksFriday) {
                    canWork = shiftAvalibiltyCheck(approvedTimeOffs, userRandom, friday.get(i));
                }
                //If they dont work any fridays and they have no overlapping timeOffRequests we book them for the friday shift
                if (canWork && !worksFriday) {
                    canWork = shiftAvalibiltyCheck(approvedTimeOffs, userRandom, sunday.get(i));
                    if (canWork) {
                        for (int z = 0; z < shiftCounter.length; z++) {
                            if (shiftCounter[z][0] == userRandom.getUserID()) {
                                if (shiftCounter[z][1] < 7) {

                                    //Incrementing the number of shifts worked in the scheduling period
                                    shiftCounter[z][1]++;
                                    shiftCounter[z][1]++;
                                    friday.get(i).setUser(userRandom);
                                    sunday.get(i).setUser(userRandom);

                                    List<Shift> shiftList = new ArrayList<>();
                                    shiftList = userRandom.getShiftList();
                                    shiftList.add(friday.get(i));
                                    shiftList.add(sunday.get(i));
                                    userRandom.setShiftList(shiftList);

                                    //Adding the filled shift the shiftList to return
                                    shiftsFilled.add(friday.get(i));
                                    counter++;
                                    shiftsFilled.add(sunday.get(i));
                                    counter++;
                                } else {
                                    canWork = false;
                                }
                            }
                        }

                    }
                }
            }
        }
        //For a saturday shift
        for (int i = 0; i < saturday.size(); i++) {
            canWork = false;
            while (!canWork) {
                //Getting a random user to fill the saturday shift with
                int rnd = new Random().nextInt(unScheduledUsers.size());
                User userRandom = unScheduledUsers.get(rnd);
                //Checking to see if the user has any conflicting timeOffRequests
                canWork = shiftAvalibiltyCheck(approvedTimeOffs, userRandom, saturday.get(i));
                if (canWork) {
                    //checking to see if the user works the friday/sunday of that weekend, in which case they will not be able to work
                    if (weekendAvalibilityCheck(shiftsFilled, saturday.get(i), userRandom)) {
                        for (int z = 0; z < shiftCounter.length; z++) {
                            //If they can work
                            if (shiftCounter[z][0] == userRandom.getUserID()) {
                                //Checking if they have 7 days in the scheduling period already
                                if (shiftCounter[z][1] < 7) {
                                    //Can work
                                    //Increments shift counter for the user and adds them to the shift
                                    shiftCounter[z][1]++;
                                    saturday.get(i).setUser(userRandom);
                                    List<Shift> shiftList = new ArrayList<>();
                                    shiftList = userRandom.getShiftList();
                                    shiftList.add(saturday.get(i));
                                    userRandom.setShiftList(shiftList);
                                    //Adds the filled shift to the list of shifts to return
                                    shiftsFilled.add(saturday.get(i));
                                    //Removes the user from the list of users that have not been scheduled
                                    unScheduledUsers.remove(rnd);
                                } else {
                                    //Find another user
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
        //For weekday shifts
        for (int i = 0; i < weekdays.size(); i++) {
            canWork = false;
            while (!canWork) {
                //Getting a random user to fill the shift
                int rnd = new Random().nextInt(unScheduledUsers.size());
                User userRandom = unScheduledUsers.get(rnd);
                //Seeing if the user has any conflicting timeOffs
                canWork = shiftAvalibiltyCheck(approvedTimeOffs, userRandom, weekdays.get(i));
                if (canWork) {
                    //if the user can work

                    for (int z = 0; z < shiftCounter.length; z++) {
                        if (shiftCounter[z][0] == userRandom.getUserID()) {
                            //Check to see if the user has already worked 7 days
                            if (shiftCounter[z][1] < 7) {
                                //if they have worked less then 7 days
                                //Add them to the shift and increment the users counter
                                shiftCounter[z][1]++;
                                weekdays.get(i).setUser(userRandom);
                                List<Shift> shiftList = new ArrayList<>();
                                shiftList = userRandom.getShiftList();
                                shiftList.add(weekdays.get(i));
                                userRandom.setShiftList(shiftList);
                                //Add the filled shift to the list of shifts to return
                                shiftsFilled.add(weekdays.get(i));

                                canWork = true;
                            } else {
                                //find new user
                                canWork = false;
                            }
                        }
                    }
                }
            }
        }
        //List of shifts that are filled with users
        return shiftsFilled;
    }

    /**
     * shiftAvalibiltyCheck method that takes in time off requests and the user
     * and shift in question, finds if the user has a time off request during
     * the inputed shift and returns a boolean on whether they can work the
     * shift or not
     *
     * @param timeOffRequests list of time off requests to check
     * @param user user to check if we can schedule for the shift
     * @param shift shift that we are trying to schedule the user for
     * @return true can work, false can't work
     */
    public boolean shiftAvalibiltyCheck(List<Timeoff> timeOffRequests, User user, Shift shift) {

        Date timeOffStartDate;
        Date timeOffEndDate;
        Date shiftStartTime = shift.getStartTime();
        boolean canWork = false;
        if (timeOffRequests.isEmpty()) {
            return true;
        }
        //Going throught the request list to see if any belong to the user
        for (int i = 0; i < timeOffRequests.size(); i++) {

            timeOffStartDate = timeOffRequests.get(i).getStartDate();
            timeOffEndDate = timeOffRequests.get(i).getEndDate();

            //Checking if the time off request interfears with the shift
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
        //Returns true if can, false if can't
        return canWork;
    }

    /**
     * saveShifts method that takes a list of shifts and inserts them into the
     * database using ShiftDB insert
     *
     * @param shiftList list of shifts to save
     */
    public void saveShifts(ArrayList<Shift> shiftList) {
        ShiftDB shiftDB = new ShiftDB();
        for (Shift shift : shiftList) {
            try {
                shiftDB.insert(shift);
            } catch (Exception e) {
            }
        }

    }

    /**
     * shiftDayOfWeekCheck method that finds the day of the week that the shift
     * is on and returns
     *
     * @param shift
     * @return int day of week
     */
    public int shiftDayOfWeekCheck(Shift shift) {

        Calendar shiftDayCal = Calendar.getInstance();
        shiftDayCal.setTime(shift.getStartTime());

        return shiftDayCal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * holidayCheck method is an unfinished method that would check to see if a
     * shift falls on a holiday
     *
     * @param shift to check for holiday
     * @return true if holiday, false if not
     */
    public boolean holidayCheck(Shift shift) {
        boolean isHoliday = false;
        return isHoliday;
    }

    /**
     * weekendCheck method that takes in a dayOfWeek integer and checks to see
     * if its a weekend
     *
     * @param dayOfWeek integer of the day of week
     * @return true if weekend, false if not
     */
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
        //if not a weekend
        return false;
    }

    /**
     * weekendAvaliblityCheck method that takes
     *
     * @param shiftsFilled list of shifts that the user works already
     * @param saturday shift that is on saturday we want to add user to
     * @param user user we want to check for
     * @return
     */
    public boolean weekendAvalibilityCheck(ArrayList<Shift> shiftsFilled, Shift saturday, User user) {
        int userID = user.getUserID();
        int fridayShiftInt = 0;
        int sundayShiftInt = 0;
        int saturdayShiftInt = saturday.getNumberInBlock();
        boolean hasFriday = false;
        boolean hasSunday = false;

        //Checking to see if the user works on the friday or sunday of the same weekend as the saturday
        for (Shift shift : shiftsFilled) {

            if (shiftDayOfWeekCheck(shift) == 1) {
                if (userID == shift.getUser().getUserID()) {
                    //if user works friday
                    fridayShiftInt = shift.getNumberInBlock();
                    hasFriday = true;

                }
            } else if (shiftDayOfWeekCheck(shift) == 6) {
                if (userID == shift.getUser().getUserID()) {
                    //if user works sunday
                    sundayShiftInt = shift.getNumberInBlock();
                    hasSunday = true;

                }
            }

        }

        // if the user works the friday and sunday, he can not work the saturday of the same weekend so we return false
        if (hasFriday && hasSunday) {
            if ((saturdayShiftInt - 1) == fridayShiftInt) {
                return false;
            }

        }
        //Return true if the user is able to work the saturday
        return true;
    }

    /**
     * personalScheduleMaker method that creates a personal schedule of a user
     * to be seen on the users welcome page
     *
     * @param email email of user to find the user object
     * @return shifts list of shifts that the user works
     */
    public List<Shift> personalScheduleMaker(String email) {
        ShiftDB shiftDB = new ShiftDB();
        UserDB userDB = new UserDB();
        SchedulingService scheduleService = new SchedulingService();

        User user = null;
        ArrayList<Shift> shiftListArrayList = new ArrayList<>();
        //Getting a new date object to make sure that the shifts that we show come after the current date
        Date currentDate = new Date();
        try {
            //Getting the user by the users email
            user = userDB.get(email);
            List<Shift> shiftList = shiftDB.getAll();

            //Getting shifts that the user workd
            for (Shift shifts : shiftList) {
                int currentUserID = shifts.getUser().getUserID();
                Date shiftDate = shifts.getStartTime();

                //Checking to see if shift is after the current date
                if (currentUserID == user.getUserID() && shiftDate.after(currentDate)) {
                    //Adding the shift to the list to return
                    shiftListArrayList.add(shifts);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ShiftService.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Sorting the shifts in order of date 
        List<Shift> shiftListToReturn = sortShiftsPersonal(shiftListArrayList);
        //Returning the users personal schedule
        return shiftListToReturn;
    }

    /**
     * sortShiftsPersonal method that takes in an unsorted list of shifts for a
     * user and sorts them by date.
     *
     * @param shifts list of shifts to sort
     * @return shifts sorted
     */
    public List<Shift> sortShiftsPersonal(ArrayList<Shift> shifts) {
        //Sorting shifts by date
        Comparator<Shift> shiftComparator = Comparator.comparing(Shift::getStartTime);
        shifts.sort(shiftComparator);

        return shifts;
    }
}
