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

/**
 * The AccountService class is used to validate a user and allow them access
 * into the application. It also is used to change a users password.
 *
 * @author Thomas Skiffington , ePaul
 */
public class AccountService {

    /**
     * login method that confirms that user logging in has entered a valid
     * login, and returns the User object user
     *
     * @param email of user logging in
     * @param password of user logging in
     * @return user
     */
    public User login(String email, String password) {
        UserDB userDB = new UserDB();
        User user = null;

        try {
            //Getting user with that email from DB
            user = userDB.get(email);
            //Checking to see if the passwords match, return true if they do
            if (password.equals(user.getPassword())) {
                return user;
            }
        } catch (Exception e) {
        }
        // returns null if the user was not found or the password didnt match
        return null;

    }

    /**
     * changePassword method that Validates the new password when User is
     * changing password, ensures all password requirements have been met
     *
     * @param email of the user
     * @param oldPassword of the user
     * @param newPassword to replace old password
     * @return boolean True if the password was changed, false if not
     */
    public boolean changePassword(String email, String oldPassword, String newPassword) {
        //Passwords requirements -- 1 uppercase, 1 lowercase, 1 number, minimum 8 length
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasNumber = false;
        char ch;
        UserDB userDB = new UserDB();
        User user = null;
        try {
            //Getting the user by their email
            user = userDB.get(email);
            //Seeing if password meets requirments
            if (!oldPassword.equals(newPassword)) {
                if (newPassword.length() >= 8) {
                    for (int i = 0; i < newPassword.length(); i++) {
                        ch = newPassword.charAt(i);
                        if (Character.isDigit(ch)) {
                            hasNumber = true;
                        } else if (Character.isUpperCase(ch)) {
                            hasUpperCase = true;
                        } else if (Character.isLowerCase(ch)) {
                            hasLowerCase = true;
                        }
                    }
                    //If requirments have been met, return true
                    if (hasNumber && hasUpperCase && hasLowerCase) {
                        user.setPassword(newPassword);
                        userDB.update(user);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
        }
        // If requirments have not been met, return false.
        return false;
    }

    /**
     * getUser method that takes in an email and uses the UserDB class to get a
     * user with that email
     *
     * @param email of user to get
     * @return user with matching email
     * @throws Exception if user could not be retrieved
     */
    public User getUser(String email) throws Exception {
        //Creates a new UserDB and uses its get method to find the user with the matching email
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        return user;
    }

    /**
     * getUserByID method that takes in a userID and returns the user with that
     * ID, uses the UserDB class
     *
     * @param userID ID of user to retrieve
     * @return user
     * @throws Exception if user could not be retrieved
     */
    public User getUserByID(int userID) throws Exception {
        //Creates a new UserDB and uses its get method to find the user with the matching ID
        UserDB userDB = new UserDB();
        User user = userDB.getUserByID(userID);
        return user;
    }

    /**
     * getAll method that returns a list of users from the database, uses the
     * UserDB class
     *
     * @return users list of users
     * @throws Exception if users could not be retrieved
     */
    public List<User> getAll() throws Exception {
        //Creates a new UserDB object and uses the UserDB getAll method to return a list of users
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAll();
        return users;
    }

    /**
     * insert method that takes in all the parameters needed to make a new user,
     * creates a new user, and then uses UserDB insert to add the new user into
     * the database.
     *
     * @param addFirstName users first name
     * @param addLastName users last name
     * @param addEmail users email
     * @param addPassword users password
     * @param addHospital users hospital
     * @param addRole users role
     * @param isActive true if active, false if not
     * @param isExtender true if extender, false if not
     * @throws Exception if user could not be inserted
     */
    public void insert(String addFirstName, String addLastName, String addEmail, String addPassword, String addHospital, String addRole, boolean isActive, boolean isExtender) throws Exception {
        //Creating needed DB objects
        UserDB userDB = new UserDB();
        HospitalDB hospitalDB = new HospitalDB();
        RoleDB roleDB = new RoleDB();
        //Creating the new user with the parameters
        User user = new User(0, addFirstName, addLastName, addEmail, addPassword, isActive);
        //Getting the hospital and adding the hospital to the user
        Hospital hospital = hospitalDB.getByHospitalName(addHospital);
        user.setHospital(hospital);
        //Getting the role and adding the role to the user
        Role role = roleDB.getByRoleName(addRole);
        user.setRole(role);
        //Setting isExtender to either true or false
        user.setIsExtender(isExtender);
        //Inserting user into database
        userDB.insert(user);
    }

    /**
     * delete method that takes in the userID and deletes the user if the user
     * is not an Admin.
     *
     * @param deleteUser ID of user to delete
     * @throws Exception if delete failed
     */
    public void delete(int deleteUser) throws Exception {
        UserDB userDB = new UserDB();
        //Getting the user using the userID and UserDB getUserByID
        User user = userDB.getUserByID(deleteUser);

        //Making sure the user is not type admin as they cannot be deleted
        if (user.getRole().getRoleID() != 1) {
            //Deleting user
            userDB.delete(user);
        } else {
            throw new Exception();
        }
    }

    /**
     * update method that takes in a users parameters that need updating and
     * updates the user in the database using UserDB update.
     *
     * @param editID ID of user
     * @param editFirstName users firstName
     * @param editLastName users lastName
     * @param editEmail users Email
     * @param editHospital users hospital
     * @param editRole users role
     * @throws Exception if user could not be updated
     */
    public void update(int editID, String editFirstName, String editLastName, String editEmail, String editHospital, String editRole) throws Exception {
        //Setting up DB's
        UserDB userDB = new UserDB();
        HospitalDB hospitalDB = new HospitalDB();
        RoleDB roleDB = new RoleDB();

        //Getting user by the field which cannot be edited, their ID
        User user = userDB.getUserByID(editID);

        //Getting the hospital and role of the user
        Hospital hospital = hospitalDB.getByHospitalName(editHospital);
        Role role = roleDB.getByRoleName(editRole);

        //resetting all the user attributes including ones that did not get edited
        user.setFirstName(editFirstName);
        user.setLastName(editLastName);
        user.setEmail(editEmail);
        user.setHospital(hospital);
        user.setRole(role);

        //updating the user in the database
        userDB.update(user);
    }
}
