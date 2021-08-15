package services;

import java.util.ArrayList;
import models.Hospital;

/**
 * Hospital Service class which contains method to find the roles of a given
 * hospital
 *
 * @author Thomas Skiffington
 */
public class HospitalService {

    /**
     * findHospitalRoles method that takes in a hospital and checks to see which
     * roles it has and returns a list of integers which are the role types for
     * that hospital
     *
     * @param hospital
     * @return rolesToReturn list of integers which represent roles
     */
    public ArrayList<Integer> findHospitalRoles(Hospital hospital) {
        //Getting the role list of the hospitla
        String hospitalRoles = hospital.getRoleList();
        int length = hospitalRoles.length();
        ArrayList<Integer> rolesToReturn = new ArrayList<>();

        //Getting each character in the role string and adding the integer value to the return array if they are found.
        for (int i = 0; i < length; i++) {

            char roleCheck = hospitalRoles.charAt(i);

            switch (roleCheck) {

                case 'A':
                    //Access
                    rolesToReturn.add(2);
                    break;
                case 'T':
                    //Trauma
                    rolesToReturn.add(3);
                    break;
                case 'S':
                    //Senior
                    rolesToReturn.add(4);
                    break;
            }

        }

        return rolesToReturn;
    }

}
