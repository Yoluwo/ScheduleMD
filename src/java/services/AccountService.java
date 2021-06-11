package services;
import model.User;
import dataaccess.UserDB;

public class AccountService {
    
    public User login(String email, String password) {
        UserDB userDB = new UserDB();
        
        try {
            User user = userDB.get(email);
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
    public void changePassword(String email, String oldPassword, String newPassword) {

    }
}
