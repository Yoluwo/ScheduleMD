/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.*;
import java.util.*;
import java.text.*;
import models.*;
import services.*;

/**
 *
 * @author 743851
 */
public class TimeOffService {

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

    public Timeoff makeTimeOffRequest(User user, Date startDate, Date endDate) {
        //Making a new time off object with the request dates
        NotificationService noteService = new NotificationService();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Timeoff newTimeOff = new Timeoff(0, Calendar.getInstance().getTime(), startDate, endDate, false);
        String startDateFmt = format.format(newTimeOff.getStartDate());
        String endDateFmt = format.format(newTimeOff.getEndDate());
        Date date = new Date();
        newTimeOff.setUser(user);
        Notification note = new Notification(0,format.format(date) +":  "+  user.getFirstName() + ", your request for time off from" + startDateFmt +" until " +endDateFmt+" is pending review");
        note.setUser(user);
        noteService.saveNotification(note);
        saveTimeOff(newTimeOff);
        return newTimeOff;
    }

    //Requests will be displayed through the servlet
    public Timeoff approveTimeOffRequest(Timeoff timeOffRequest) {
        //Approve TimeOff Request
        NotificationService noteService = new NotificationService();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = format.format(timeOffRequest.getStartDate());
        String endDate = format.format(timeOffRequest.getEndDate());
        User userOfTimeOffRequest = timeOffRequest.getUser();
        Date date = new Date();
        Notification note = new Notification(0,format.format(date) +":  "+ "Your time off request for " + startDate + " until " + endDate + " has been approved!");
        note.setUser(userOfTimeOffRequest);
        noteService.saveNotification(note);
        timeOffRequest.setIsApproved(true);

        updateTimeOff(timeOffRequest);
        return timeOffRequest;

    }

    public void denyTimeOffRequest(Timeoff timeOffRequest, String reason) {
        //Needs to have a way to say why its denied
        NotificationService noteService = new NotificationService();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = format.format(timeOffRequest.getStartDate());
        String endDate = format.format(timeOffRequest.getEndDate());
        User userOfTimeOffRequest = timeOffRequest.getUser();
        Date date = new Date();

        if (reason.isEmpty()) {
            Notification note = new Notification(0, format.format(date) +":  "+ userOfTimeOffRequest.getFirstName() + " , your time off request for " + startDate + " until " + endDate + " has been denied.");
            note.setUser(userOfTimeOffRequest);
            noteService.saveNotification(note);
        } else {
            Notification note = new Notification(0, format.format(date) +":  "+ userOfTimeOffRequest.getFirstName() + " , your time off request for " + startDate + " until " + endDate + " has been denied : " + reason);
            note.setUser(userOfTimeOffRequest);
            noteService.saveNotification(note);
        }

        deleteTimeOff(timeOffRequest);
    }

    public void saveTimeOff(Timeoff timeOffRequest) {

        TimeoffDB tDB = new TimeoffDB();
        try {
            tDB.insert(timeOffRequest);
        } catch (Exception e) {
            System.out.println("Error in saving timeOffRequest");
        }
    }

    public void updateTimeOff(Timeoff timeOffRequest) {
        TimeoffDB tDB = new TimeoffDB();

        try {
            tDB.update(timeOffRequest);
        } catch (Exception e) {
            System.out.println("Error in updating timeOffRequest");
        }
    }

    public void deleteTimeOff(Timeoff timeOffRequest) {
        TimeoffDB tDB = new TimeoffDB();

        try {
            tDB.delete(timeOffRequest);
        } catch (Exception e) {
            System.out.println("Error in deleting timeOffRequest");
        }
    }

    public List<Timeoff> getPendingRequests() {

        TimeoffDB tDB = new TimeoffDB();
        ArrayList<Timeoff> allTimeOffs = new ArrayList<>();
        List pendingTimeOffs;

        try {
            allTimeOffs = new ArrayList<>(tDB.getAll());
        } catch (Exception e) {
        }

        for (int i = 0; i < allTimeOffs.size(); i++) {

            Timeoff currentTimeOffRequest = allTimeOffs.get(i);

            if (currentTimeOffRequest.getIsApproved()) {
                allTimeOffs.remove(i);
            }

        }

        pendingTimeOffs = allTimeOffs;

        return pendingTimeOffs;
    }

    public Timeoff getTimeOffByID(int timeOffID) {
        TimeoffDB tDB = new TimeoffDB();
        Timeoff timeOff = null;

        try {
            timeOff = tDB.getByTimeOffID(timeOffID);
        } catch (Exception e) {
        }
        return timeOff;
    }
}
