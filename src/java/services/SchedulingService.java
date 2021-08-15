package services;

import models.*;
import dataaccess.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import servlets.PasswordServlet;

/**
 * SchedulingService class contains all methods and functionality required for
 * generating a schedule
 *
 * @author epaul
 */
public class SchedulingService {

    /**
     * generateSchedule() method will generate and return a schedule, based on the parameters startDate, and hospital
     * @param startDate - Day of which the schedule will start on
     * @param hospital - Hospital for which the schedule will be created
     * @return schedule
     */
    public Schedule generateSchedule(Calendar startDate, Hospital hospital) {
        //initializing all Services needed
        HospitalService hospitalService = new HospitalService();
        UserService userService = new UserService();
        ShiftService shiftService = new ShiftService();
        TimeOffService timeOffService = new TimeOffService();
        
        //initializing all ArrayList needed
        ArrayList<User> acess = new ArrayList<>();
        ArrayList<User> trauma = new ArrayList<>();
        ArrayList<User> senior = new ArrayList<>();
        ArrayList<Shift> accessShiftsEmpty = new ArrayList<>();
        ArrayList<Shift> traumaShiftsEmpty = new ArrayList<>();
        ArrayList<Shift> seniorShiftsEmpty = new ArrayList<>();
        ArrayList<Shift> accessShifts = new ArrayList<>();
        ArrayList<Shift> seniorShifts = new ArrayList<>();
        ArrayList<Shift> traumaShifts = new ArrayList<>();
        ArrayList<Shift> loadedShiftsFinal = new ArrayList<>();
        ArrayList<Shift> sortedShifts = new ArrayList<>();
        ArrayList<User> usersToUpdate = new ArrayList<>();
        boolean hasTrauma = false;

        ArrayList<Timeoff> approvedTimeOff = new ArrayList<>();
        Calendar endDate;
        //Finding the Schedule endDate based on the StartDate
        endDate = findEndDate(startDate);
        //Loads in all roles for the given hospital
        ArrayList<Integer> rolesToLoad = hospitalService.findHospitalRoles(hospital);

        //Adds active resident's to their respective RoleList
        for (int roleID : rolesToLoad) {
            switch (roleID) {
                case 2:
                    acess = userService.findAllActiveUsersByRoleByHospital(roleID, hospital);
                    break;
                case 3:
                    trauma = userService.findAllActiveUsersByRoleByHospital(roleID, hospital);
                    hasTrauma = true;
                    break;
                case 4:
                    senior = userService.findAllActiveUsersByRoleByHospital(roleID, hospital);
                    break;
            }
        }
        //Creating Schedule
        Schedule schedule = new Schedule(0, startDate.getTime(), endDate.getTime(), false);
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
        Role role1 = new Role(2);
        Role role2 = new Role(3);
        Role role3 = new Role(4);
        //Generates all shifts for the access role
        accessShiftsEmpty = shiftService.generateShifts(startDate, endDate, schedule, role1);
        //Generates all shifts for the Trauma role
        traumaShiftsEmpty = shiftService.generateShifts(startDate, endDate, schedule, role2);
        //Generates all shifts for the Senior role
        seniorShiftsEmpty = shiftService.generateShifts(startDate, endDate, schedule, role3);
        
        //Loads in all approved time off requests
        approvedTimeOff = timeOffService.loadAllAprovedRequests(startDate, endDate);
        //Fills all access shifts with Resident's
        accessShifts = shiftService.sortShiftsForAssigning(accessShiftsEmpty, acess, approvedTimeOff);
        if (hasTrauma) {
            //Fills all Trauma shifts with Resident's
            traumaShifts = shiftService.sortShiftsForAssigning(traumaShiftsEmpty, trauma, approvedTimeOff);
        }
        //Fills all Senior shifts with Resident's
        seniorShifts = shiftService.sortShiftsForAssigning(seniorShiftsEmpty, senior, approvedTimeOff);
        //Adding all Access / Trauma / Senior shifts into one ArrayList
        loadedShiftsFinal.addAll(accessShifts);
        loadedShiftsFinal.addAll(traumaShifts);
        loadedShiftsFinal.addAll(seniorShifts);
        //Saving all shifts into the Database
        shiftService.saveShifts(loadedShiftsFinal);
        //Adding all users who have been scheduled to work into one ArrayList
        usersToUpdate.addAll(acess);
        usersToUpdate.addAll(senior);
        usersToUpdate.addAll(trauma);
        //Saves all updated User data to the database
        userService.usersToSave(usersToUpdate);
        //Set shift list on Schedule
        schedule.setShiftList(loadedShiftsFinal);
        //Update schedule in Database
        updateSchedule(schedule);
        
        return schedule;
    }

    /**
     * sortShifts() method goes through the passed in ArrayList, and sorts it based on the Data column NumberInBlock.
     * Once done sorting, returns the updated ArrayList.
     * @param shifts - shiftList to be sorted
     * @return shifts
     */
    public List<Shift> sortShifts(ArrayList<Shift> shifts) {
        Comparator<Shift> shiftComparator = Comparator.comparing(Shift::getNumberInBlock);
        shifts.sort(shiftComparator);

        return shifts;
    }

    /**
     * sortShiftsByRole1() method is a sorting algorithm which will sort the passed in ArrayList
     * based on each shift role ID, this method is used for the Foothills Medical center.
     * @param shifts - ShiftList to be sorted
     * @return shifts
     */
    public List<Shift> sortShiftsByRole1(ArrayList<Shift> shifts) {
        for (int i = 0; i < shifts.size(); i += 3) {
            if (shifts.get(i).getRole().getRoleID() != 2) {
                if (shifts.get(i).getRole().getRoleID() == 4) {
                    Shift temp = shifts.get(i + 2);
                    shifts.set(i + 2, shifts.get(i));
                    if (temp.getRole().getRoleID() == 3) {
                        Shift temp2 = shifts.get(i + 1);
                        shifts.set(i + 1, temp);
                        shifts.set(i, temp2);
                    } else {
                        shifts.set(i, temp);
                    }

                } else if (shifts.get(i).getRole().getRoleID() == 3) {
                    Shift temp = shifts.get(i + 1);
                    shifts.set(i + 1, shifts.get(i));
                    if (temp.getRole().getRoleID() == 2) {
                        shifts.set(i, temp);
                    } else {
                        Shift temp2 = shifts.get(i + 2);
                        shifts.set(i, temp2);
                        shifts.set(i + 2, temp);

                    }
                }
            } else {
                if (shifts.get(i + 1).getRole().getRoleID() != 3) {
                    Shift temp = shifts.get(i + 1);
                    Shift temp2 = shifts.get(i + 2);
                    shifts.set(i + 1, temp2);
                    shifts.set(i + 2, temp);
                }
            }
        }
        return shifts;
    }

    /**
     * sortShiftsByRole2() method is a sorting algorithm which will sort the passed in ArrayList
     * based on each shift role ID, this method is used for the Peter Lougheed Hospital.
     * @param shifts - ShiftList to be sorted
     * @return shifts
     */
    public List<Shift> sortShiftsByRole2(ArrayList<Shift> shifts) {
        for (int i = 0; i < shifts.size(); i += 2) {
            if (shifts.get(i).getRole().getRoleID() != 2) {
                Shift temp = shifts.get(i);
                shifts.set(i, shifts.get(i + 1));
                shifts.set(i + 1, temp);
            }
        }
        return shifts;
    }

    /**
     * updateSchedule() method update the passed in schedule object in the database to include newly added information
     * @param schedule - Schedule to be updated
     */
    public void updateSchedule(Schedule schedule) {
        ScheduleDB scDB = new ScheduleDB();
        try {
            scDB.update(schedule);
        } catch (Exception e) {

        }
    }

    /**
     * findEndDate() method will find the last day of the schedule, based on the startDate passed in.
     * @param startDate - Used to find the endDate
     * @return endDate
     */
    public Calendar findEndDate(Calendar startDate) {

        Calendar endDate = Calendar.getInstance();
        endDate.setTime(startDate.getTime());
        endDate.add(Calendar.DAY_OF_MONTH, 28);

        return endDate;
    }

    /**
     * saveSchedule() method will insert and commit a new Schedule into the Database
     * @param schedule - Schedule to be inserted/commited to the Database
     */
    public void saveSchedule(Schedule schedule) {
        ScheduleDB scdb = new ScheduleDB();
        try {

            scdb.insert(schedule);
        } catch (Exception e) {
            System.out.println("oh no");
        }
    }

    /**
     * loadSchedule() method will get and return a specific schedule based on the parameters startDate, and hospital
     * @param startDate - Day of which schedule to load will be 
     * @param hospital - Hospital for which schedule to be loaded
     * @return schedule
     * @throws Exception 
     */
    public Schedule loadSchedule(Date startDate, Hospital hospital) throws Exception {
        ScheduleDB scdb = new ScheduleDB();
        return scdb.getByStartDate(startDate, hospital);

    }

    /**
     * swapShifts() method will swap the Resident working on a specific shift, with a different resident.
     * Once Shifts have been swapped, will return the updated Schedule reflecting these changes.
     * @param scheduleID - Schedule which shifts are being swapped on
     * @param shiftID1 - ID of first shift being swapped
     * @param shiftID2 - ID of second shift being swapped
     * @return scheudle
     */
    public Schedule swapShifts(int scheduleID, int shiftID1, int shiftID2) {
        //Initializing DataAccess
        ShiftDB shiftdb = new ShiftDB();
        ScheduleDB scheduleDB = new ScheduleDB();
        
        Shift shift1 = null;
        Shift shift2 = null;
        User shift1User = null;
        User shift2User = null;
        Schedule schedule = null;
        int firstRemovalIndex = 0;
        int secondRemovalIndex = 0;

        try {
            //Get the schedule from the database
            schedule = scheduleDB.getByScheduleID(scheduleID);
        } catch (Exception ex) {
            Logger.getLogger(SchedulingService.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Get schedule ShiftList
        List<Shift> shiftList = schedule.getShiftList();
        ArrayList<Shift> shiftListArray = new ArrayList<>(shiftList);

        int i = 0;
        //Gets the User and shift object for each shift being swapped
        for (Shift shift : shiftListArray) {

            if (shift.getShiftID().equals(shiftID1)) {
                shift1 = shift;
                shift1User = shift.getUser();
                firstRemovalIndex = i;
            } else if (shift.getShiftID().equals(shiftID2)) {
                shift2 = shift;
                shift2User = shift.getUser();
                secondRemovalIndex = i;

            }
            i++;
        }
        //Remove the old shifts from the ArrayList
        shiftListArray.remove(firstRemovalIndex);
        if (firstRemovalIndex < secondRemovalIndex) {
            shiftListArray.remove(secondRemovalIndex - 1);
        }

        //Set the new user on each shift
        shift1.setUser(shift2User);
        shift2.setUser(shift1User);

        //Add the new shifts into the ArrayList
        shiftListArray.add(shift1);
        shiftListArray.add(shift2);

        List<Shift> shiftListUpdated = shiftListArray;

        try {
            //Updating both shifts in the database
            shiftdb.update(shift1);
            shiftdb.update(shift2);
        } catch (Exception ex) {
            Logger.getLogger(SchedulingService.class.getName()).log(Level.SEVERE, null, ex);
        }

        schedule.setShiftList(shiftListUpdated);
        //Updating schedule in the database
        updateSchedule(schedule);

        return schedule;
    }
    /**
     * fillExtender() method will replace an extender on a shift with the userID parameter
     * @param scheduleID - Schedule which extender are being filled on
     * @param shiftID - the ShiftID which is being filled
     * @param userID - The UserID who is filling the shift
     * @return schedule
     */
    public Schedule fillExtender(int scheduleID, int shiftID, int userID) {
        //initializing DataAccess
        ShiftDB shiftdb = new ShiftDB();
        ScheduleDB scheduleDB = new ScheduleDB();
        UserDB userDB = new UserDB();

        User user = null;
        Schedule schedule = null;
        ArrayList<Shift> shiftList = new ArrayList<>();

        try {
            //Get User to add into shift from database
            user = userDB.getUserByID(userID);
            //Get schedule from database
            schedule = scheduleDB.getByScheduleID(scheduleID);
        } catch (Exception ex) {
            Logger.getLogger(SchedulingService.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Get schedule shiftList
        shiftList = new ArrayList(schedule.getShiftList());
        int i = 0;
        //Loop through shiftList, find shiftID that is being changed.
        for (Shift shifts : shiftList) {
            if (shifts.getShiftID() == shiftID) {
                //Set the user on the shift
                shifts.setUser(user);
                try {
                    //update shift in Database
                    shiftdb.update(shifts);

                } catch (Exception ex) {
                    Logger.getLogger(SchedulingService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        List<Shift> shiftListUpdated = shiftList;

        schedule.setShiftList(shiftListUpdated);
        //Update schedule in Database
        updateSchedule(schedule);

        return schedule;

    }

}
