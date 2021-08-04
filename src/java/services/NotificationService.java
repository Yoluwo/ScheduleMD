/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import models.*;
import java.util.*;
import dataaccess.*;

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
}
