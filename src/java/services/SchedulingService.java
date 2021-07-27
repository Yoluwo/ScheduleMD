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

        endDate = findEndDate(startDate);
        ArrayList<Integer> rolesToLoad = hospitalService.findHospitalRoles(hospital);

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
        accessShiftsEmpty = shiftService.generateShifts(startDate, endDate, schedule, role1);
        traumaShiftsEmpty = shiftService.generateShifts(startDate, endDate, schedule, role2);
        seniorShiftsEmpty = shiftService.generateShifts(startDate, endDate, schedule, role3);
        approvedTimeOff = timeOffService.loadAllAprovedRequests(startDate, endDate);
        accessShifts = shiftService.sortShiftsForAssigning(accessShiftsEmpty, acess, approvedTimeOff);
        if(hasTrauma){
            traumaShifts = shiftService.sortShiftsForAssigning(traumaShiftsEmpty, trauma, approvedTimeOff);        
        }
        seniorShifts = shiftService.sortShiftsForAssigning(seniorShiftsEmpty, senior, approvedTimeOff);
        loadedShiftsFinal.addAll(accessShifts);
        loadedShiftsFinal.addAll(traumaShifts);
        loadedShiftsFinal.addAll(seniorShifts);
        shiftService.saveShifts(loadedShiftsFinal);
        usersToUpdate.addAll(acess);
        usersToUpdate.addAll(senior);
        usersToUpdate.addAll(trauma);
        userService.usersToSave(usersToUpdate);
        schedule.setShiftList(loadedShiftsFinal);
        updateSchedule(schedule);
        return schedule;
    }

    public List<Shift> sortShifts(ArrayList<Shift> shifts){
        Comparator<Shift> shiftComparator = Comparator.comparing(Shift::getNumberInBlock);
        shifts.sort(shiftComparator);
        
        return shifts;
    }
    public List<Shift> sortShiftsByRole1(ArrayList<Shift> shifts){
        for(int i = 0; i < shifts.size(); i+=3){
            if(shifts.get(i).getRole().getRoleID() != 2) {
                if(shifts.get(i).getRole().getRoleID() == 4) {
                    Shift temp = shifts.get(i+2);
                    shifts.set(i + 2, shifts.get(i)); 
                    if(temp.getRole().getRoleID() == 3) {
                        Shift temp2 = shifts.get(i+1);
                        shifts.set(i+1, temp); 
                        shifts.set(i, temp2); 
                    }
                    else {
                        shifts.set(i, temp);
                    }
                    
                }
                else if(shifts.get(i).getRole().getRoleID() == 3) {
                    Shift temp = shifts.get(i+1);
                    shifts.set(i+1, shifts.get(i));
                    if(temp.getRole().getRoleID() == 2) {
                        shifts.set(i, temp);
                    }
                    else {
                        Shift temp2 = shifts.get(i+2);
                        shifts.set(i, temp2);
                        shifts.set(i+2, temp); 
                        
                    }
                }
            }
            else {
                if(shifts.get(i+1).getRole().getRoleID() != 3) {
                    Shift temp = shifts.get(i+1);
                    Shift temp2 = shifts.get(i+2);
                    shifts.set(i+1, temp2);
                    shifts.set(i+2, temp);
                }
            }
        }
        return shifts;
    }
    public List<Shift> sortShiftsByRole2(ArrayList<Shift> shifts){
        for(int i = 0; i < shifts.size(); i+=2){
            if(shifts.get(i).getRole().getRoleID() != 2){
                Shift temp = shifts.get(i);
                shifts.set(i, shifts.get(i+1));
                shifts.set(i+1, temp);
            }
        }
        return shifts;
    }
   public void updateSchedule(Schedule schedule){
       ScheduleDB scDB = new ScheduleDB();
       try{
           scDB.update(schedule);
       }
       catch(Exception e){
           
       }
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
