package services;

import dataaccess.*;
import java.util.*;
import models.*;

/**
 * UserService class that allows us to find and save users
 * @author Thomas Skiffington, epaul
 */
public class UserService {

    /**
     * findAllActiveUsersByRoleByHospital method that finds users that are active, have a certain role, and work at a certain hospital
     * @param roleID role of users we are looking for
     * @param hospital hospital of users we are looking for
     * @return activeUsersByRole list that contains users that are of the specified role and hospital
     */
     public ArrayList<User> findAllActiveUsersByRoleByHospital(int roleID, Hospital hospital) {

          UserDB userDB = new UserDB();
          boolean isActive = true;
          List<User> activeUsers = new ArrayList<>();
          ArrayList<User> activeUsersByRole = new ArrayList<>();

          try {

               activeUsers = userDB.getAllActive(isActive);

               for (User user : activeUsers) {
                   //Checking to see if the user has the same role and hospital as the ones inserted into the method
                    if (user.getRole().getRoleID() == roleID && hospital.getHospitalID() == user.getHospital().getHospitalID()) {
                        //if they do, we add them to the list to return
                         activeUsersByRole.add(user);
                    }
               }
          } catch (Exception e) {
               System.out.println("Error in findAllActiveUsersByRole");
          }
          return activeUsersByRole;

     }

     /**
      * usersToSave method that takes a list of users and updates them in the database
      * @param userList list of users to update
      */
     public void usersToSave(ArrayList<User> userList) {
          UserDB userDB = new UserDB();
          //updating every user in the list
          for (User user : userList) {
               try {
                    userDB.update(user);
               } catch (Exception e) {
               }
          }
     }
}
