package services;
import dataaccess.HospitalDB;
import dataaccess.RoleDB;
import models.User;
import dataaccess.UserDB;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Hospital;
import models.Role;

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
    public User getUser(String email) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        return user;
    }
    
    public User getUserByID(int userID) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.getUserByID(userID);
        return user;
    }
    
    public List<User> getAll() throws Exception {
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAll();
        return users;
    }

    public void insert(String addFirstName, String addLastName, String addEmail, String addPassword, String addHospital, String addRole, boolean isActive, boolean isExtender) throws Exception {
        UserDB userDB = new UserDB();
        HospitalDB hospitalDB = new HospitalDB();
        RoleDB roleDB = new RoleDB();
        
        User user = new User(0, addFirstName, addLastName, addEmail, addPassword, isActive);
        
        Hospital hospital = hospitalDB.getByHospitalName(addHospital);
        user.setHospital(hospital);
        
        Role role = roleDB.getByRoleName(addRole);
        user.setRole(role);
        
        user.setIsExtender(isExtender);
        
        userDB.insert(user); 
    }

    public void delete(int deleteUser) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.getUserByID(deleteUser);
        System.out.println(user.getFirstName());
        if (user.getRole().getRoleID() != 1) {
            userDB.delete(user);
        } else {
            throw new Exception();
        }
    }

    public void update(int editID, String editFirstName, String editLastName, String editEmail, String editHospital, String editRole) throws Exception {
        UserDB userDB = new UserDB();
        HospitalDB hospitalDB = new HospitalDB();
        RoleDB roleDB = new RoleDB();
        
        User user = userDB.getUserByID(editID);
        
        System.out.println(user.getFirstName());
        
        Hospital hospital = hospitalDB.getByHospitalName(editHospital);
        Role role = roleDB.getByRoleName(editRole);
        
        user.setFirstName(editFirstName);
        user.setLastName(editLastName);
        user.setEmail(editEmail);
        user.setHospital(hospital);
        user.setRole(role);
        
        userDB.update(user);
    }
}
