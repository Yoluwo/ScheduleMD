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

    public Timeoff makeTimeOffRequest(User user, Calendar startDate, Calendar endDate){
        //Making a new time off object with the request dates
        Timeoff newTimeOff = new Timeoff(0,Calendar.getInstance().getTime(), startDate.getTime(),endDate.getTime(),false);
        newTimeOff.setUser(user);
        Notification note = new Notification(0, "User: " + user.getFirstName() + " " + user.getLastName() + " has made a Timeoff Request");
        note.setUser(user);
        saveNotification(note);
        saveTimeOff(newTimeOff);
        return newTimeOff;
    }

    //Requests will be displayed through the servlet
    public Timeoff approveTimeOffRequest(Timeoff timeOffRequest){
        //Approve TimeOff Request
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = format.format(timeOffRequest.getStartDate());
        String endDate = format.format(timeOffRequest.getEndDate());
        User userOfTimeOffRequest = timeOffRequest.getUser();
        Notification note = new Notification(0, "Your time off request for " + startDate + " until " + endDate + " has been approved!");
        note.setUser(userOfTimeOffRequest);
        saveNotification(note);
        timeOffRequest.setIsApproved(true);
        
        updateTimeOff(timeOffRequest);
        return timeOffRequest;


    }

    public void denyTimeOffRequest(Timeoff timeOffRequest, String reason){
        //Needs to have a way to say why its denied

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = format.format(timeOffRequest.getStartDate());
        String endDate = format.format(timeOffRequest.getEndDate());
        User userOfTimeOffRequest = timeOffRequest.getUser();
       
        

        if(reason.isEmpty()){
            Notification note = new Notification(0,userOfTimeOffRequest.getFirstName() + " , your time off request for "+ startDate+" until "+ endDate +" has been denied.");
            saveNotification(note);
        }

        else{
           Notification note = new Notification(0,userOfTimeOffRequest.getFirstName() + " , your time off request for "+ startDate+" until "+ endDate +" has been denied : " + reason);
           saveNotification(note);
        }

        deleteTimeOff(timeOffRequest);
    }

    public void saveTimeOff(Timeoff timeOffRequest){

        TimeoffDB tDB = new TimeoffDB();
        try{
        tDB.insert(timeOffRequest);
        }
        catch(Exception e){
            System.out.println("Error in saving timeOffRequest");
        }
    }

    public void updateTimeOff(Timeoff timeOffRequest){
        TimeoffDB tDB = new TimeoffDB();

        try{
            tDB.update(timeOffRequest);
         }
        catch(Exception e){
            System.out.println("Error in updating timeOffRequest");
        }
    }

    public void deleteTimeOff(Timeoff timeOffRequest){
        TimeoffDB tDB = new TimeoffDB();

        try{
            tDB.delete(timeOffRequest);
         }
        catch(Exception e){
            System.out.println("Error in deleting timeOffRequest");
        }
    }
    public void saveNotification(Notification note){
        NotificationDB nDB = new NotificationDB();
        try{
            nDB.insert(note);
        }
        catch(Exception e){
            System.out.println("Error in saving notification");
        }
    }
  
}