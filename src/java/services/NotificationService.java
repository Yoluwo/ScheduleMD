package services;

import models.*;
import java.util.*;
import dataaccess.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * NotificationService class that contains methods for creating,deleting,saving
 * and updating notification objects.
 *
 * @author epaul, Thomas Skiffington
 */
public class NotificationService {

    /**
     * findNotificationByUser method that takes in a user object and finds the
     * notification objects for that user.
     *
     * @param user that notifications belong to
     * @return notifcationsToReturn list of notifications for the user
     */
    public List<Notification> findNotificationByUser(User user) {

        ArrayList notificationsToReturn = new ArrayList<>();
        int userID = user.getUserID();

        // Using NotificationDB getAll method to retrieve all the notification
        NotificationDB noteDB = new NotificationDB();
        try {

            List<Notification> notifications = noteDB.getAll();

            //Seeing if any of the notification belong to the user
            for (Notification note : notifications) {
                int currentUserID = note.getUser().getUserID();
                if (currentUserID == userID) {
                    //Adding the notification that is the users to the list to return
                    notificationsToReturn.add(note);
                }
            }

        } catch (Exception e) {

        }
        //Returning the users list of notifications
        return notificationsToReturn;
    }

    /**
     * hideNotification method that takes in the notificationID of the
     * notification to hide and sets the isHidden parameter to true which hides
     * the notification for a user.
     *
     * @param noteID notification ID of the notification to hide
     */
    public void hideNotification(int noteID) {
        NotificationDB nDB = new NotificationDB();
        Notification note = null;
        try {
            //Getting the notification by its ID and setting isHidden attribute to true
            note = nDB.getByTimeOffID(noteID);
            note.setIsHidden(true);
            //Updating notification object in the database
            updateNotification(note);
        } catch (Exception ex) {
            Logger.getLogger(NotificationService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * saveNotification method that takes in a notification object and inserts
     * it into the database
     *
     * @param note notification object to save in the database
     */
    public void saveNotification(Notification note) {
        NotificationDB nDB = new NotificationDB();
        try {
            //Inserting notification object into database
            nDB.insert(note);
        } catch (Exception e) {
            System.out.println("Error in saving notification");
        }
    }

    /**
     * updateNotification method that takes in an updated notification object
     * and updates it in the database
     *
     * @param note notification object to update
     */
    public void updateNotification(Notification note) {
        NotificationDB nDB = new NotificationDB();
        try {
            nDB.update(note);
        } catch (Exception e) {
            System.out.println("Error in saving notification");
        }
    }

    /**
     * notificationListCheck method that is used to see if a notification
     * isHidden when loading a users notifications, used so that a hidden
     * notification does not show in the non hidden notification list of a user
     *
     * @param notificationList list of notifications to check
     * @return true if a notification is of type hidden, false if not
     */
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
