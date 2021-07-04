package services;

import dataaccess.*;
import java.util.*;
import models.*;

public class UserService {

    public ArrayList<User> findAllActiveUsersByRoleByHospital(int roleID, Hospital hospital) {

        UserDB userDB = new UserDB();
        boolean isActive = true;
        List<User> activeUsers = new ArrayList<>();
        ArrayList<User> activeUsersByRole = new ArrayList<>();

        try {

            activeUsers = userDB.getAllActive(isActive);

            for (User user : activeUsers) {

                if (user.getRole().getRoleID() == roleID && hospital.getHospitalID() == user.getHospital().getHospitalID()) {
                    activeUsersByRole.add(user);
                }
            }
        } catch (Exception e) {
            System.out.println("Error in findAllActiveUsersByRole");
        }
        return activeUsersByRole;

    }
}
