/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import model.User;
import dataaccess.UserDB;

/**
 *
 * @author 743851
 */
public class AccountService {
    
    public User login(String email, String password) {

        UserDB userDB = new UserDB();
        User user = null;
        try {
            user = userDB.get(email);
            if (password.equals(user.getPassword()) && user.getIsActive()) {
                return user;
            } else {
                user = null;
            }
        } catch (Exception e) {
        }

        return user;
    }
    public void changePassword(String email, String oldPassword, String newPassword) {

    }
}
