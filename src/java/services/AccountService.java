package services;
import model.User;
import dataaccess.UserDB;

public class AccountService {
    
    public User login(String email, String password) {
        UserDB userDB = new UserDB();
        User user = null;
        try {
            user = userDB.get(email);
            if (password.equals(user.getPassword())) {
                return user;
            }
        } catch (Exception e) {}
        return user;
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
