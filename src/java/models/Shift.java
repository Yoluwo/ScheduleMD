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
 * Auto generated model. This class models all of the data stored in the Shift
 * table in the database as a Shift object.
 */
@Entity
@Table(name = "shift")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Shift.findAll", query = "SELECT s FROM Shift s")
    , @NamedQuery(name = "Shift.findByShiftID", query = "SELECT s FROM Shift s WHERE s.shiftID = :shiftID")
    , @NamedQuery(name = "Shift.findByStartTime", query = "SELECT s FROM Shift s WHERE s.startTime = :startTime")
    , @NamedQuery(name = "Shift.findByEndTime", query = "SELECT s FROM Shift s WHERE s.endTime = :endTime")
    , @NamedQuery(name = "Shift.findByIsWeekend", query = "SELECT s FROM Shift s WHERE s.isWeekend = :isWeekend")
    , @NamedQuery(name = "Shift.findByIsHoliday", query = "SELECT s FROM Shift s WHERE s.isHoliday = :isHoliday")
    , @NamedQuery(name = "Shift.findByNumberInBlock", query = "SELECT s FROM Shift s WHERE s.numberInBlock = :numberInBlock")
    , @NamedQuery(name = "Shift.findByDayOfWeek", query = "SELECT s FROM Shift s WHERE s.dayOfWeek = :dayOfWeek")})
public class Shift implements Serializable {
    
    /**
     * Holds the serialization UID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Holds the ID of the Shift.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ShiftID")
    private Integer shiftID;
    
    /**
     * Holds the start time for the shift.
     */
    @Basic(optional = false)
    @Column(name = "StartTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    
    /**
     * Holds the end time for the shift.
     */
    @Basic(optional = false)
    @Column(name = "EndTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    
    /**
     * Holds the boolean stating if it is a weekend Shift or not.
     */
    @Column(name = "IsWeekend")
    private Boolean isWeekend;
    
    /**
     * Holds the boolean stating if is holiday Shift or not.
     */
    @Column(name = "IsHoliday")
    private Boolean isHoliday;
    
    /**
     * Holds the Shifts number is schedule block.
     */
    @Column(name = "NumberInBlock")
    private Integer numberInBlock;
    
    /**
     * Holds the day of week for the Shift.
     */
    @Column(name = "DayOfWeek")
    private Integer dayOfWeek;
    
    /**
     * Holds the Role that is associated with the Shift.
     */
    @JoinColumn(name = "Role", referencedColumnName = "RoleID")
    @ManyToOne
    private Role role;
    
    /**
     * Holds the schedule that is associated with the shift.
     */
    @JoinColumn(name = "Schedule", referencedColumnName = "ScheduleID")
    @ManyToOne(optional = false)
    private Schedule schedule;
    
    /** 
     * Holds the user that is associated with the Shift.
     */
    @JoinColumn(name = "User", referencedColumnName = "UserID")
    @ManyToOne(optional = false)
    private User user;

    /**
     * Default constructor for Shift class.
     */
    public Shift() {
    }

    /**
     * Non default constructor for the Shift class. This constructor takes in
     * the ID of the Shift and sets it.
     * 
     * @param shiftID The ID of the Shift.
     */
    public Shift(Integer shiftID) {
        this.shiftID = shiftID;
    }

    /**
     * Non default constructor for the Shift class. This constructor takes in
     * the ID of the Shift, start time, and end time then sets them.
     * 
     * @param shiftID
     * @param startTime
     * @param endTime 
     */
    public Shift(Integer shiftID, Date startTime, Date endTime) {
        this.shiftID = shiftID;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Gets the ID of the Shift.
     * 
     * @return The ID of the Shift.
     */
    public Integer getShiftID() {
        return shiftID;
    }

    /**
     * Sets the ID of the Shift.
     * 
     * @param shiftID The ID of the Shift.
     */
    public void setShiftID(Integer shiftID) {
        this.shiftID = shiftID;
    }

    /**
     * Gets the start time for the Shift.
     * 
     * @return The start time for the Shift.
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time for the Shift.
     * 
     * @param startTime The start time for the Shift.
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time of the Shift.
     * 
     * @return The end time of the Shift.
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the Shift.
     * 
     * @param endTime The end time of the Shift.
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the boolean that states whether or not the Shift is a weekend.
     * 
     * @return Boolean that states whether or not the Shift is a weekend.
     */
    public Boolean getIsWeekend() {
        return isWeekend;
    }

    /**
     * Sets the boolean that states whether or not the Shift is a weekend.
     * 
     * @param isWeekend Boolean that states whether or not the Shift is a weekend.
     */
    public void setIsWeekend(Boolean isWeekend) {
        this.isWeekend = isWeekend;
    }

    /**
     * Gets the boolean that states whether or not the Shift is a Holiday.
     * 
     * @return Boolean that states whether or not the Shift is a Holiday.
     */
    public Boolean getIsHoliday() {
        return isHoliday;
    }

    /**
     * Sets the boolean that states whether or not the Shift is a Holiday.
     * 
     * @param isHoliday Boolean that states whether or not the Shift is a Holiday.
     */
    public void setIsHoliday(Boolean isHoliday) {
        this.isHoliday = isHoliday;
    }

    /**
     * Gets the Shifts number in the schedule block.
     * 
     * @return The Shifts number in block.
     */
    public Integer getNumberInBlock() {
        return numberInBlock;
    }

    /**
     * Sets the Shifts number in the schedule block.
     * 
     * @param numberInBlock The Shifts number in block.
     */
    public void setNumberInBlock(Integer numberInBlock) {
        this.numberInBlock = numberInBlock;
    }

    /**
     * Gets the day of week that the Shift is in.
     * 
     * @return The day of week that the Shift is in.
     */
    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * Sets the day of week that the Shift is in.
     * 
     * @param dayOfWeek The day of week that the Shift is in.
     */
    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * Gets the role associated with the Shift.
     * 
     * @return The role associated with the Shift.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the role associated with the Shift.
     * 
     * @param role The role associated with the Shift.
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Gets the schedule associated with the Shift.
     * 
     * @return The schedule associated with the Shift.
     */
    public Schedule getSchedule() {
        return schedule;
    }

    /**
     * Sets the schedule associated with the Shift.
     * 
     * @param schedule The schedule associated with the Shift.
     */
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    /**
     * Gets the user associated with the Shift.
     * 
     * @return The user associated with the Shift.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user associated with the Shift.
     * 
     * @param user The user associated with the Shift.
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
        hash += (shiftID != null ? shiftID.hashCode() : 0);
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
        if (!(object instanceof Shift)) {
            return false;
        }
        Shift other = (Shift) object;
        if ((this.shiftID == null && other.shiftID != null) || (this.shiftID != null && !this.shiftID.equals(other.shiftID))) {
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
        return "models.Shift[ shiftID=" + shiftID + " ]";
    }
}