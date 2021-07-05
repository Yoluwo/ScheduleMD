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

public class SchedulingService {

    public Schedule generateSchedule(Calendar startDate, Hospital hospital) {

        HospitalService hospitalService = new HospitalService();
        UserService userService = new UserService();
        ShiftService shiftService = new ShiftService();
        TimeOffService timeOffService = new TimeOffService();
        
        ArrayList<User> acess = new ArrayList<>();
        ArrayList<User> trauma = new ArrayList<>();
        ArrayList<User> senior = new ArrayList<>();
        ArrayList<Shift> shifts = new ArrayList<>();
        ArrayList<Shift> accessShifts = new ArrayList<>();
        ArrayList<Shift> seniorShifts = new ArrayList<>();
        ArrayList<Shift> traumaShifts = new ArrayList<>();
        ArrayList<Shift> loadedShiftsFinal = new ArrayList<>();
        ArrayList<Shift> sortedShifts = new ArrayList<>();
        ArrayList<User> usersToUpdate = new ArrayList<>();

        ArrayList<Timeoff> approvedTimeOff = new ArrayList<>();
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
        approvedTimeOff = timeOffService.loadAllAprovedRequests(startDate, endDate);
        accessShifts = shiftService.sortShiftsForAssigning(shifts, acess, approvedTimeOff);
        //traumaShifts = shiftService.sortShiftsForAssigning(shifts, trauma, approvedTimeOff);
        //seniorShifts = shiftService.sortShiftsForAssigning(shifts, senior, approvedTimeOff);
        loadedShiftsFinal.addAll(accessShifts);
        //loadedShiftsFinal.addAll(traumaShifts);
        //loadedShiftsFinal.addAll(seniorShifts);
        sortedShifts = sortShifts(loadedShiftsFinal);
        shiftService.saveShifts(sortedShifts);
        usersToUpdate.addAll(acess);
        //usersToUpdate.addAll(senior);
        //usersToUpdate.addAll(trauma);
        userService.usersToSave(usersToUpdate);
        return schedule;
    }

    public ArrayList<Shift> sortShifts(ArrayList<Shift> shifts){
        Comparator<Shift> shiftComparator = Comparator.comparing(Shift::getNumberInBlock);
        shifts.sort(shiftComparator);
        
        return shifts;
    }
   
    
    public Calendar findEndDate(Calendar startDate) {

        Calendar endDate = Calendar.getInstance();
        endDate.setTime(startDate.getTime());
        endDate.add(Calendar.DAY_OF_MONTH, 28);

        return endDate;
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
