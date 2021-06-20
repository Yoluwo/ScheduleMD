
package models;

/**
 *
 * @author Thomas Skiffington
 */
public class Admin extends User{
    
    public Admin(Integer userID, String firstName, String lastName, String email, String password, boolean isActive) {
        super(userID, firstName, lastName, email,password,isActive);
        Role adminRole = new Role(0);
        super.setRoleID(adminRole);
    }
    
   // private Boolean addAdmin();
    
   // private void addResident();
    
   // private void postSchedule();
    
   // private void changeUserType();
    
   // private void approvePendingRequest();
    
   // private void denyPendingRequest();
    
   
    
}
