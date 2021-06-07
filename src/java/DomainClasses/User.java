
package DomainClasses;

/**
 *
 * @author Thomas Skiffington
 */
public class User {
    
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private int roleTypeID;
    private int userID;
    
    public User (String email, String password, String firstName, String lastName, int roleTypeID, int userID){
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleTypeID = roleTypeID;
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getRoleTypeID() {
        return roleTypeID;
    }

    public void setRoleTypeID(int roleTypeID) {
        this.roleTypeID = roleTypeID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
    
    
}
