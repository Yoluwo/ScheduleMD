package services;
import models.User;
import dataaccess.UserDB;

/*
    The AccountService class is used to validate a user and allow them access
    into the application. It also is used to change a users password.
    
*/

public class AccountService {
    //Confirms that user logging in has entered a valid login, and returns the User
    public User login(String email, String password) {
        UserDB userDB = new UserDB();
        User user = null;
        try {
            user = userDB.get(email);
            if (password.equals(user.getPassword())) {
                return user;
            }
        } catch (Exception e) {}
        return null;
/*
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
*/
    }
    //Validates the new password when User is changing password, ensures all password
    //requirements have been met
    public boolean changePassword(String email, String oldPassword, String newPassword) {
        //Passwords requirements -- 1 uppercase, 1 lowercase, 1 number, minimum 8 length
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasNumber = false;
        char ch;
        UserDB userDB = new UserDB();
        User user = null;
        try {
            user = userDB.get(email);
            if(!oldPassword.equals(newPassword)){
                if(newPassword.length() >= 8){
                    for(int i = 0; i < newPassword.length(); i++){
                        ch = newPassword.charAt(i);
                        if(Character.isDigit(ch)){
                            hasNumber = true;
                        }
                        else if(Character.isUpperCase(ch)){
                            hasUpperCase = true;
                        }
                        else if(Character.isLowerCase(ch)){
                            hasLowerCase = true;
                        }
                    }
                    if(hasNumber && hasUpperCase && hasLowerCase){
                        user.setPassword(newPassword);
                        userDB.update(user);
                        return true;
                    }
                }
            }
        } catch(Exception e) {}
        return false;
    }
}
