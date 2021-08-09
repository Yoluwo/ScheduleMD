/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import models.*;
import java.util.*;
import dataaccess.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author epaul
 */
public class NotificationService {

    public List<Notification> findNotificationByUser(User user) {

        ArrayList notificationsToReturn = new ArrayList<>();
        int userID = user.getUserID();

        NotificationDB noteDB = new NotificationDB();
        try {

            List<Notification> notifications = noteDB.getAll();

            for (Notification note : notifications) {
                int currentUserID = note.getUser().getUserID();
                if (currentUserID == userID) {
                    notificationsToReturn.add(note);
                }
            }

        } catch (Exception e) {

        }

        return notificationsToReturn;
    }

    public void hideNotification(int noteID) {
        NotificationDB nDB = new NotificationDB();
        Notification note = null;
        try {
            note = nDB.getByTimeOffID(noteID);
            note.setIsHidden(true);
            updateNotification(note);
        } catch (Exception ex) {
            Logger.getLogger(NotificationService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void saveNotification(Notification note) {
        NotificationDB nDB = new NotificationDB();
        try {
            nDB.insert(note);
        } catch (Exception e) {
            System.out.println("Error in saving notification");
        }
    }

    public void updateNotification(Notification note) {
        NotificationDB nDB = new NotificationDB();
        try {
            nDB.update(note);
        } catch (Exception e) {
            System.out.println("Error in saving notification");
        }
    }

    public boolean notificationListCheck(List<Notification> notificationList) {
        for (Notification note : notificationList) {
            if (note.getIsHidden() == null) {
                return false;
            }
            if (note.getIsHidden() != null) {

                if (note.getIsHidden() == false) {
                    return false;
                }
            }
        }

        return true;
    }

}
