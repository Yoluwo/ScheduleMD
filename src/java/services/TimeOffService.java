/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.TimeoffDB;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import models.Timeoff;

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
}
