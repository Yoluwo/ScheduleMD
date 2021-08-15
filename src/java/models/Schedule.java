package models;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Auto generated model. This class models all of the data stored in the Schedule
 * table in the database as a Schedule object.
 */
@Entity
@Table(name = "schedule")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Schedule.findAll", query = "SELECT s FROM Schedule s")
    , @NamedQuery(name = "Schedule.findByScheduleID", query = "SELECT s FROM Schedule s WHERE s.scheduleID = :scheduleID")
    , @NamedQuery(name = "Schedule.findByStartDate", query = "SELECT s FROM Schedule s WHERE s.startDate = :startDate")
    , @NamedQuery(name = "Schedule.findByEndDate", query = "SELECT s FROM Schedule s WHERE s.endDate = :endDate")
    , @NamedQuery(name = "Schedule.findByIsUsed", query = "SELECT s FROM Schedule s WHERE s.isUsed = :isUsed")})
public class Schedule implements Serializable {
    
    /**
     * Holds the serialization UID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Holds the ID of the Schedule.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ScheduleID")
    private Integer scheduleID;
    
    /**
     * Holds the start date of the Schedule.
     */
    @Basic(optional = false)
    @Column(name = "StartDate")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    /**
     * Holds the end date of the schedule.
     */
    @Basic(optional = false)
    @Column(name = "EndDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    
    /**
     * Holds the boolean that determines if the Schedule is used or not.
     */
    @Basic(optional = false)
    @Column(name = "IsUsed")
    private boolean isUsed;
    
    /**
     * Holds the hospital that the Schedule belongs to.
     */
    @JoinColumn(name = "Hospital", referencedColumnName = "HospitalID")
    @ManyToOne(optional = false)
    private Hospital hospital;
    
    /**
     * Holds the list of shifts that are associated with the Schedule.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "schedule")
    private List<Shift> shiftList;

    /**
     * Default constructor for the Schedule class.
     */
    public Schedule() {
    }

    /**
     * Non default constructor for the Schedule class. This constructor takes 
     * in the ID of the Schedule and sets it.
     * 
     * @param scheduleID The ID of the Schedule.
     */
    public Schedule(Integer scheduleID) {
        this.scheduleID = scheduleID;
    }

    /**
     * Non default constructor for the Schedule class. This constructor takes 
     * in the ID of the schedule, the start date, the end date, and the boolean 
     * if the schedule is used then sets them.
     * 
     * @param scheduleID ID of the Schedule.
     * @param startDate Start date of the Schedule.
     * @param endDate End date of the Schedule.
     * @param isUsed Boolean if the Schedule is being used or not.
     */
    public Schedule(Integer scheduleID, Date startDate, Date endDate, boolean isUsed) {
        this.scheduleID = scheduleID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isUsed = isUsed;
    }

    /**
     * Gets the ID of the Schedule.
     * 
     * @return The ID of the Schedule.
     */
    public Integer getScheduleID() {
        return scheduleID;
    }

    /**
     * Sets the ID of the Schedule.
     * 
     * @param scheduleID The ID of the Schedule.
     */
    public void setScheduleID(Integer scheduleID) {
        this.scheduleID = scheduleID;
    }

    /**
     * Gets the start date of the Schedule.
     * 
     * @return The start date of the Schedule.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the Schedule.
     * 
     * @param startDate The start date of the Schedule.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date of the schedule.
     * 
     * @return The end date of the Schedule.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the Schedule.
     * 
     * @param endDate The end date of the Schedule.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the boolean that states whether or not the Schedule is used.
     * 
     * @return The boolean that states whether or not the Schedule is used.
     */
    public boolean getIsUsed() {
        return isUsed;
    }

    /**
     * Sets the boolean that states whether or not the Schedule is used.
     * 
     * @param isUsed The boolean that states whether or not the Schedule is used.
     */
    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    /**
     * Gets the hospital that the Schedule belongs to.
     * 
     * @return The hospital that the Schedule belongs to.
     */
    public Hospital getHospital() {
        return hospital;
    }

    /**
     * Sets the hospital that the Schedule belongs to.
     * 
     * @param hospital The 
     */
    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    /**
     * Gets the shifts that are associated with the Schedule.
     * 
     * @return The shifts associated with the Schedule.
     */
    @XmlTransient
    public List<Shift> getShiftList() {
        return shiftList;
    }

    /**
     * Sets the shifts that are associated with the Schedule.
     * 
     * @param shiftList The shifts that are associated with the Schedule.
     */
    public void setShiftList(List<Shift> shiftList) {
        this.shiftList = shiftList;
    }

    /**
     * Creates a hash code.
     * 
     * @return The created hash code.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (scheduleID != null ? scheduleID.hashCode() : 0);
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
        if (!(object instanceof Schedule)) {
            return false;
        }
        Schedule other = (Schedule) object;
        if ((this.scheduleID == null && other.scheduleID != null) || (this.scheduleID != null && !this.scheduleID.equals(other.scheduleID))) {
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
        return "models.Schedule[ scheduleID=" + scheduleID + " ]";
    }
}