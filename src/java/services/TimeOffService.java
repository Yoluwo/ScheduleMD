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
 * TimeOffService Class contains all methods and functionality required for
 * Making / approving / denying TimeOff Requests
 *
 * @author epaul, Thomas Skiffington
 */
public class TimeOffService {

    /**
     * loadAllApprovedRequest() method will get and return all approved TimeOff
     * request that have a date in between the start and end parameters
     *
     * @param start - beginning period of when to load TimeOffRequests for
     * @param end - End period of when to load TimeOffRequests for
     * @return timeOffToLoad
     */
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

    /**
     * makeTimeOffRequest() method will create a new TimeOff object, set's all
     * attributes and saves to the database
     *
     * @param user - User making the TimeOffRequest
     * @param startDate - Start day of TimeOffRequest
     * @param endDate - End day of TimeOffRequest
     * @return newTimeOff
     */
    public Timeoff makeTimeOffRequest(User user, Date startDate, Date endDate) {
        //Making a new time off object with the request dates
        NotificationService noteService = new NotificationService();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Timeoff newTimeOff = new Timeoff(0, Calendar.getInstance().getTime(), startDate, endDate, false);
        String startDateFmt = format.format(newTimeOff.getStartDate());
        String endDateFmt = format.format(newTimeOff.getEndDate());
        Date date = new Date();
        //Setting user for TimeOff Request
        newTimeOff.setUser(user);
        //Creating a note object
        Notification note = new Notification(0, format.format(date) + ":  " + user.getFirstName() + ", your request for time off from " + startDateFmt + " until " + endDateFmt + " is pending review");
        //Set the user on the note
        note.setUser(user);
        //Save note to Database
        noteService.saveNotification(note);
        //Save TimeOff request to database
        saveTimeOff(newTimeOff);

        return newTimeOff;
    }

    /**
     * approveTimeOffRequest() method will set the TimeOffRequest isApproved to
     * be true, as well as send a note to the User telling them the Request has
     * been approved
     *
     * @param timeOffRequest - TimeOffRequest to approve
     * @return timeOffRequest
     */
    public Timeoff approveTimeOffRequest(Timeoff timeOffRequest) {
        //Approve TimeOff Request
        NotificationService noteService = new NotificationService();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = format.format(timeOffRequest.getStartDate());
        String endDate = format.format(timeOffRequest.getEndDate());
        User userOfTimeOffRequest = timeOffRequest.getUser();
        Date date = new Date();
        //Creating note object
        Notification note = new Notification(0, format.format(date) + ":  " + "Your time off request for " + startDate + " until " + endDate + " has been approved!");
        //Set the user on the note
        note.setUser(userOfTimeOffRequest);
        //Save note to database
        noteService.saveNotification(note);
        //Set TimeOffRequest.isApproved = true
        timeOffRequest.setIsApproved(true);
        //Update the TimeOff object in the database
        updateTimeOff(timeOffRequest);

        return timeOffRequest;

    }

    /**
     * denyTimeOffRequest() method will set the TimeOffRequest isApproved to be
     * false, as well as send a note to the User telling them the Request has
     * been denied
     *
     * @param timeOffRequest - TimeOffRequest to deny
     * @param reason - Reason to add onto note
     */
    public void denyTimeOffRequest(Timeoff timeOffRequest, String reason) {
        //Needs to have a way to say why its denied
        NotificationService noteService = new NotificationService();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = format.format(timeOffRequest.getStartDate());
        String endDate = format.format(timeOffRequest.getEndDate());
        User userOfTimeOffRequest = timeOffRequest.getUser();
        Date date = new Date();
        //Create a note to send to User
        if (reason.isEmpty()) {
            Notification note = new Notification(0, format.format(date) + ":  " + userOfTimeOffRequest.getFirstName() + " , your time off request for " + startDate + " until " + endDate + " has been denied.");
            note.setUser(userOfTimeOffRequest);
            noteService.saveNotification(note);
        } else {
            Notification note = new Notification(0, format.format(date) + ":  " + userOfTimeOffRequest.getFirstName() + " , your time off request for " + startDate + " until " + endDate + " has been denied : " + reason);
            note.setUser(userOfTimeOffRequest);
            noteService.saveNotification(note);
        }
        //Delete timeOffRequest from database
        deleteTimeOff(timeOffRequest);
    }

    /**
     * saveTimeOff() method will insert and commit a new TimeOffRequest object
     * into the database
     *
     * @param timeOffRequest - TimeOffRequest to be inserted/commited
     */
    public void saveTimeOff(Timeoff timeOffRequest) {

        TimeoffDB tDB = new TimeoffDB();
        try {
            tDB.insert(timeOffRequest);
        } catch (Exception e) {
            System.out.println("Error in saving timeOffRequest");
        }
    }

    /**
     * updateTimeOff() method will update the given timeOffRequest in the database to show any new information
     * @param timeOffRequest - TimeOffRequest to be updated
     */
    public void updateTimeOff(Timeoff timeOffRequest) {
        TimeoffDB tDB = new TimeoffDB();

        try {
            tDB.update(timeOffRequest);
        } catch (Exception e) {
            System.out.println("Error in updating timeOffRequest");
        }
    }

    /**
     * deleteTimeOff() method will delete the given timeOffRequest from the database
     * @param timeOffRequest - timeOffRequest to be deleted
     */
    public void deleteTimeOff(Timeoff timeOffRequest) {
        TimeoffDB tDB = new TimeoffDB();

        try {
            tDB.delete(timeOffRequest);
        } catch (Exception e) {
            System.out.println("Error in deleting timeOffRequest");
        }
    }

    /**
     * getPendingRequests() method will get and return a list of all pending TimeOff requests
     * @return pendingTimeOffs
     */
    public List<Timeoff> getPendingRequests() {

        TimeoffDB tDB = new TimeoffDB();
        ArrayList<Timeoff> allTimeOffs = new ArrayList<>();
        List pendingTimeOffs;

        try {
            //Get all timeOff requests
            allTimeOffs = new ArrayList<>(tDB.getAll());
        } catch (Exception e) {
        }

        for (int i = 0; i < allTimeOffs.size(); i++) {
            
            Timeoff currentTimeOffRequest = allTimeOffs.get(i);
            
            if (currentTimeOffRequest.getIsApproved()) {
                //Remove TimeOff object if it is approved
                allTimeOffs.remove(i);
            }

        }

        pendingTimeOffs = allTimeOffs;

        return pendingTimeOffs;
    }

    /**
     * getTimeOffByID() method will get and return a specific TimeOff object where the timeOffID matches
     * @param timeOffID - Current TimeOffRequest ID
     * @return timeOff
     */
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
