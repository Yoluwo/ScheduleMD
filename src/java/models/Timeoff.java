package models;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Auto generated model. This class models all of the data stored in the Timeoff
 * table in the database as a Timeoff object.
 */
@Entity
@Table(name = "timeoff")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Timeoff.findAll", query = "SELECT t FROM Timeoff t")
    , @NamedQuery(name = "Timeoff.findByTimeOffID", query = "SELECT t FROM Timeoff t WHERE t.timeOffID = :timeOffID")
    , @NamedQuery(name = "Timeoff.findByRequestedDate", query = "SELECT t FROM Timeoff t WHERE t.requestedDate = :requestedDate")
    , @NamedQuery(name = "Timeoff.findByStartDate", query = "SELECT t FROM Timeoff t WHERE t.startDate = :startDate")
    , @NamedQuery(name = "Timeoff.findByEndDate", query = "SELECT t FROM Timeoff t WHERE t.endDate = :endDate")
    , @NamedQuery(name = "Timeoff.findByIsApproved", query = "SELECT t FROM Timeoff t WHERE t.isApproved = :isApproved")})
public class Timeoff implements Serializable {
    
    /**
     * Holds the serialization UID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Holds the ID of the Timeoff request.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TimeOffID")
    private Integer timeOffID;
    
    /**
     * Holds the requested date of the Timeoff request.
     */
    @Basic(optional = false)
    @Column(name = "RequestedDate")
    @Temporal(TemporalType.DATE)
    private Date requestedDate;
    
    /**
     * Holds the start date of the Timeoff request.
     */
    @Basic(optional = false)
    @Column(name = "StartDate")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    /**
     * Holds the end date of the Timeoff request.
     */
    @Basic(optional = false)
    @Column(name = "EndDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    
    /**
     * Holds the boolean that states if the Timeoff is approved or not.
     */
    @Basic(optional = false)
    @Column(name = "IsApproved")
    private boolean isApproved;
    
    /**
     * Holds the user associated with the Timeoff request.
     */
    @JoinColumn(name = "User", referencedColumnName = "UserID")
    @ManyToOne(optional = false)
    private User user;

    /**
     * Default constructor for the Timeoff class.
     */
    public Timeoff() {
    }

    /**
     * Non default constructor for the Timeoff class. This constructor takes
     * in the ID of the Timeoff request and sets it.
     * 
     * @param timeOffID ID of the Timeoff request.
     */
    public Timeoff(Integer timeOffID) {
        this.timeOffID = timeOffID;
    }

    /**
     * Non default constructor for the Timeoff class. This constructor takes in
     * the ID, date requested, start date, end date, and the approval status
     * then sets them.
     * 
     * @param timeOffID ID of the Timeoff request.
     * @param requestedDate Date that the Timeoff was requested.
     * @param startDate Start date of the Timeoff request.
     * @param endDate End date of the Timeoff request.
     * @param isApproved Boolean that determines if the Timeoff is approved or not.
     */
    public Timeoff(Integer timeOffID, Date requestedDate, Date startDate, Date endDate, boolean isApproved) {
        this.timeOffID = timeOffID;
        this.requestedDate = requestedDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isApproved = isApproved;
    }

    /**
     * Gets the ID of the Timeoff request.
     * 
     * @return The ID of the Timeoff request.
     */
    public Integer getTimeOffID() {
        return timeOffID;
    }

    /**
     * Sets the ID of the Timeoff request.
     * 
     * @param timeOffID The ID of the Timeoff request.
     */
    public void setTimeOffID(Integer timeOffID) {
        this.timeOffID = timeOffID;
    }

    /**
     * Gets the date that the Timeoff was requested.
     * 
     * @return Date that the Timeoff was requested.
     */
    public Date getRequestedDate() {
        return requestedDate;
    }

    /**
     * Sets the date that the Timeoff was requested.
     * 
     * @param requestedDate Date that the Timeoff was requested.
     */
    public void setRequestedDate(Date requestedDate) {
        this.requestedDate = requestedDate;
    }

    /**
     * Gets the start date of the Timeoff request.
     * 
     * @return Start date of the Timeoff request.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the Timeoff request.
     * 
     * @param startDate Start date of the Time off request.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date of the Timeoff request.
     * 
     * @return End date of the Timeoff request.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the Timeoff request.
     * 
     * @param endDate End date of the Timeoff request.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the approval status of the Timeoff request.
     * 
     * @return The approval status of the Timeoff request.
     */
    public boolean getIsApproved() {
        return isApproved;
    }

    /**
     * Sets the approval status of the Timeoff request.
     * 
     * @param isApproved The approval status of the Timeoff request.
     */
    public void setIsApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    /**
     * Gets the user associated with the Timeoff request.
     * 
     * @return The user associated with the Timeoff request.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user associated with the Timeoff request.
     * 
     * @param user The user associated with the Timeoff request.
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
        hash += (timeOffID != null ? timeOffID.hashCode() : 0);
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
        if (!(object instanceof Timeoff)) {
            return false;
        }
        Timeoff other = (Timeoff) object;
        if ((this.timeOffID == null && other.timeOffID != null) || (this.timeOffID != null && !this.timeOffID.equals(other.timeOffID))) {
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
        return "models.Timeoff[ timeOffID=" + timeOffID + " ]";
    } 
}