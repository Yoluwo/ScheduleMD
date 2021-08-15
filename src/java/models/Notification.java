package models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Auto generated model. This class models all of the data stored in the Notification
 * table in the database as a Notification object.
 */
@Entity
@Table(name = "notification")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notification.findAll", query = "SELECT n FROM Notification n")
    , @NamedQuery(name = "Notification.findByNotificationID", query = "SELECT n FROM Notification n WHERE n.notificationID = :notificationID")
    , @NamedQuery(name = "Notification.findByNote", query = "SELECT n FROM Notification n WHERE n.note = :note")
    , @NamedQuery(name = "Notification.findByIsHidden", query = "SELECT n FROM Notification n WHERE n.isHidden = :isHidden")})
public class Notification implements Serializable {

    /**
     * Holds the serialization UID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Holds the ID of the Notification.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "NotificationID")
    private Integer notificationID;
    
    /**
     * Holds the note that is on the Notification.
     */
    @Basic(optional = false)
    @Column(name = "Note")
    private String note;
    
    /**
     * Holds the boolean for if the notification is hidden or not.
     */
    @Column(name = "IsHidden")
    private Boolean isHidden;
    
    /**
     * Holds the user that the Notification belongs to.
     */
    @JoinColumn(name = "User", referencedColumnName = "UserID")
    @ManyToOne(optional = false)
    private User user;

    /**
     * Default constructor for the Notification class.
     */
    public Notification() {
    }

    /**
     * Non default constructor for the Notification class. This constructor
     * takes in the ID of the Notification and sets it.
     * 
     * @param notificationID The ID of the Notification.
     */
    public Notification(Integer notificationID) {
        this.notificationID = notificationID;
    }

    /**
     * Non default constructor for the Notification class. This constructor
     * takes in the ID of the Notification and the note that is attached to 
     * the Notification then sets them.
     * 
     * @param notificationID The ID of the Notification.
     * @param note The note attached to the Notification.
     */
    public Notification(Integer notificationID, String note) {
        this.notificationID = notificationID;
        this.note = note;
    }

    /**
     * Gets the ID of the Notification.
     * 
     * @return The ID of the Notification.
     */
    public Integer getNotificationID() {
        return notificationID;
    }

    /**
     * Sets the ID of the Notification.
     * 
     * @param notificationID The ID of the Notification.
     */
    public void setNotificationID(Integer notificationID) {
        this.notificationID = notificationID;
    }

    /**
     * Gets the note of the Notification.
     * 
     * @return The note of the Notification.
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets the note of the Notification.
     * 
     * @param note The note of the Notification.
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Gets the boolean that states whether or not the Notification is hidden.
     * 
     * @return True or false if the notification is hidden or not.
     */
    public Boolean getIsHidden() {
        return isHidden;
    }

    /**
     * Sets the boolean that states whether or not the Notification is hidden.
     * 
     * @param isHidden The boolean that states whether or not the Notification
     * is hidden.
     */
    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }

    /**
     * Gets the user that the Notification belongs to.
     * 
     * @return The user that the Notification belongs to.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user that the Notification belongs to.
     * 
     * @param user The user that the Notification belongs to.
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    /**
     * Creates a hash code.
     * 
     * @return The created hash code.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notificationID != null ? notificationID.hashCode() : 0);
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
        if (!(object instanceof Notification)) {
            return false;
        }
        Notification other = (Notification) object;
        if ((this.notificationID == null && other.notificationID != null) || (this.notificationID != null && !this.notificationID.equals(other.notificationID))) {
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
        return "models.Notification[ notificationID=" + notificationID + " ]";
    }
}