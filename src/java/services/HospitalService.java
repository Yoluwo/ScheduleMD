/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.ArrayList;
import models.Hospital;

/**
 *
 * @author 743851
 */
public class HospitalService {

     public ArrayList<Integer> findHospitalRoles(Hospital hospital) {

          String hospitalRoles = hospital.getRoleList();
          int length = hospitalRoles.length();
          ArrayList<Integer> rolesToReturn = new ArrayList<>();

          for (int i = 0; i < length; i++) {

               char roleCheck = hospitalRoles.charAt(i);

               switch (roleCheck) {

                    case 'A':
                         rolesToReturn.add(1);
                         break;
                    case 'T':
                         rolesToReturn.add(2);
                         break;
                    case 'S':
                         rolesToReturn.add(3);
                         break;
               }

          }

          return rolesToReturn;
     }

}
