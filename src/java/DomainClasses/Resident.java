/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DomainClasses;

/**
 *
 * @author epaul
 */
public class Resident extends User {
    
    //private Request approvedRequests;
    //private Request pendingRequests;
    //private Request deniedRequests;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private int roleType;
    private int userID;
    public Resident(String email, String password, String firstName, String lastName, int roleType, int userID){ //All 3 Requests variables will be added when Requests has been created
        super(email, password, firstName, lastName, roleType, userID);
        //this.approvedRequests = approvedRequests;
        //this.pendingRequests = pendingRequests;
        //this.deniedRequests = deniedRequests;
    }
    //public Request makeTimeOffRequest(DateTime startDate,DateTime endDate,String residentEmail);
    
    //public void deleteRequest(Request request);
    
    //public Request modifyRequest(DateTime startDate,DateTime endDate, Request requestToModify);
    
    //public Request[] getDeniedRequests();
    
    //public void setDeniedRequest(Request [] deniedRequests);
    
    //public Request[] getPendingRequests();
    
    //public void setPendingRequests(Request [] pendingRequests);
    
    //public Request[] getApprovedRequests();
    
    //public void setApprovedRequests(Request [] approvedRequests);
}
