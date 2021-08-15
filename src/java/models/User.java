package models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Auto generated model. This class models all of the data stored in the User
 * table in the database as a User object.
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
    , @NamedQuery(name = "User.findByUserID", query = "SELECT u FROM User u WHERE u.userID = :userID")
    , @NamedQuery(name = "User.findByFirstName", query = "SELECT u FROM User u WHERE u.firstName = :firstName")
    , @NamedQuery(name = "User.findByLastName", query = "SELECT u FROM User u WHERE u.lastName = :lastName")
    , @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email")
    , @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password")
    , @NamedQuery(name = "User.findByIsActive", query = "SELECT u FROM User u WHERE u.isActive = :isActive")
    , @NamedQuery(name = "User.findByIsExtender", query = "SELECT u FROM User u WHERE u.isExtender = :isExtender")})
public class User implements Serializable {
    
    /**
     * Holds the serialization UID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Holds the ID of the User.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "UserID")
    private Integer userID;
    
    /**
     * Holds the first name of the User.
     */
    @Basic(optional = false)
    @Column(name = "FirstName")
    private String firstName;
    
    /**
     * Holds the last name of the User.
     */
    @Basic(optional = false)
    @Column(name = "LastName")
    private String lastName;
    
    /**
     * Holds the email of the User.
     */
    @Basic(optional = false)
    @Column(name = "Email")
    private String email;
    
    /**
     * Holds the password of the User.
     */
    @Basic(optional = false)
    @Column(name = "Password")
    private String password;
    
    /**
     * Holds the boolean if the User is active or not.
     */
    @Basic(optional = false)
    @Column(name = "IsActive")
    private boolean isActive;
    
    /**
     * Holds the boolean if the User is an extender or not.
     */
    @Column(name = "IsExtender")
    private Boolean isExtender;
    
    /**
     * Holds the list of notifications associated with the User.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Notification> notificationList;
    
    /**
     * Holds the list of shifts associated with the User.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Shift> shiftList;
    
    /**
     * Holds the list of time off requests associated with the User.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Timeoff> timeoffList;
    
    /**
     * Holds the hospital of the User.
     */
    @JoinColumn(name = "Hospital", referencedColumnName = "HospitalID")
    @ManyToOne(optional = false)
    private Hospital hospital;
    
    /**
     * Holds the role of the User.
     */
    @JoinColumn(name = "Role", referencedColumnName = "RoleID")
    @ManyToOne(optional = false)
    private Role role;

    /**
     * Default constructor of the User class.
     */
    public User() {
    }

    /**
     * Non default constructor of the User class. This constructor takes in the
     * ID of the User and sets it.
     * 
     * @param userID ID of the User.
     */
    public User(Integer userID) {
        this.userID = userID;
    }

    /**
     * Non default constructor of the User class. This constructor takes in the
     * ID, first name, last name, email, password, and the boolean that states
     * if the User is active then sets them.
     * 
     * @param userID ID of the User.
     * @param firstName First name of the User.
     * @param lastName Last name of the User.
     * @param email Email of the User.
     * @param password Password of the User.
     * @param isActive Boolean if the User is active or not.
     */
    public User(Integer userID, String firstName, String lastName, String email, String password, boolean isActive) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
    }

    /**
     * Gets the ID of the User.
     * 
     * @return The ID of the User.
     */
    public Integer getUserID() {
        return userID;
    }

    /**
     * Sets the ID of the User.
     * 
     * @param userID ID of the User.
     */
    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    /**
     * Gets the first name of the User.
     * 
     * @return The first name of the User.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the User.
     * @param firstName First name of the User.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the User.
     * 
     * @return Last name of the User.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the User.
     * 
     * @param lastName The last name of the User.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the email of the User.
     * 
     * @return The email of the User.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the User.
     * 
     * @param email The email of the User.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password of the User.
     * 
     * @return The password of the User.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the User.
     * 
     * @param password The password of the User.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the boolean that states if the User is active or not.
     * 
     * @return The boolean that states if the User is active or not.
     */
    public boolean getIsActive() {
        return isActive;
    }

    /**
     * Sets the boolean that states if the User is active or not.
     * 
     * @param isActive The boolean that states if the User is active or not.
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * Gets the boolean that states if the User is an extender or not.
     * 
     * @return The boolean that states if the User is an extender or not.
     */
    public Boolean getIsExtender() {
        return isExtender;
    }

    /**
     * Sets the boolean that states if the User is an extender or not.
     * 
     * @param isExtender The boolean that states if the User is an extender or not.
     */
    public void setIsExtender(Boolean isExtender) {
        this.isExtender = isExtender;
    }

    /**
     * Gets the list of notifications associated with the User.
     * 
     * @return The list of notifications associated with the User.
     */
    @XmlTransient
    public List<Notification> getNotificationList() {
        return notificationList;
    }

    /**
     * Sets the list of notifications associated with the User.
     * 
     * @param notificationList The list of notifications associated with the User.
     */
    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    /**
     * Gets the list of shifts associated with the User.
     * 
     * @return The list of shifts associated with the User.
     */
    @XmlTransient
    public List<Shift> getShiftList() {
        return shiftList;
    }

    /**
     * Sets the list of shifts associated with the User.
     * 
     * @param shiftList The list of shifts associated with the User.
     */
    public void setShiftList(List<Shift> shiftList) {
        this.shiftList = shiftList;
    }

    /**
     * Gets the list of time off requests associated with the User.
     * 
     * @return The list of time off requests associated with the User.
     */
    @XmlTransient
    public List<Timeoff> getTimeoffList() {
        return timeoffList;
    }

    /**
     * Sets the list of time off requests associated with the User.
     * 
     * @param timeoffList The list of time off requests associated with the User.
     */
    public void setTimeoffList(List<Timeoff> timeoffList) {
        this.timeoffList = timeoffList;
    }

    /**
     * Gets the hospital that the User is assigned to.
     * 
     * @return The hospital that the User is assigned to.
     */
    public Hospital getHospital() {
        return hospital;
    }

    /**
     * Sets the hospital that the User is assigned to.
     * 
     * @param hospital The hospital that the User is assigned to.
     */
    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    /**
     * Gets the role of the User.
     * 
     * @return The role of the User.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the role of the User.
     * 
     * @param role The role of the User.
     */
    public void setRole(Role role) {
        this.role = role;
    }
    
    /**
     * Creates a hash code.
     * 
     * @return The created hash code.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userID != null ? userID.hashCode() : 0);
        return hash;
    }
    
    /**
     * Compares the parameter object to this object. If the objects
     * are the same then the method returns true otherwise false.
     * 
     * @param object The object being compared to this object.
     * @return True if the same otherwise false.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userID == null && other.userID != null) || (this.userID != null && !this.userID.equals(other.userID))) {
            return false;
        }
        return true;
    }
    
    /**
     * String formatting for output.
     * 
     * @return Formatted string for output.
     */
    @Override
    public String toString() {
        return "models.User[ userID=" + userID + " ]";
    }
}