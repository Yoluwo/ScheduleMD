/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * This class describes the Shift object
 * @author 743851
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

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ShiftID")
    private Integer shiftID;
    @Basic(optional = false)
    @Column(name = "StartTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Basic(optional = false)
    @Column(name = "EndTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    @Column(name = "IsWeekend")
    private Boolean isWeekend;
    @Column(name = "IsHoliday")
    private Boolean isHoliday;
    @Column(name = "NumberInBlock")
    private Integer numberInBlock;
    @Column(name = "DayOfWeek")
    private Integer dayOfWeek;
    @JoinColumn(name = "Role", referencedColumnName = "RoleID")
    @ManyToOne
    private Role role;
    @JoinColumn(name = "Schedule", referencedColumnName = "ScheduleID")
    @ManyToOne(optional = false)
    private Schedule schedule;
    @JoinColumn(name = "User", referencedColumnName = "UserID")
    @ManyToOne(optional = false)
    private User user;

    /**
     * The default constructor for the Shift object
     */
    public Shift() {
    }

    /**
     * Constructs a Shift object using the shiftID
     * @param shiftID - Integer value of the shiftID
     */
    public Shift(Integer shiftID) {
        this.shiftID = shiftID;
    }

    /**
     * Constructs a Shift object using the shiftID, startTime and endTime
     * @param shiftID - Integer value of the shiftID
     * @param startTime - Date object specifying the start time of the shift
     * @param endTime - Date object specifying the end time of the shift
     */
    public Shift(Integer shiftID, Date startTime, Date endTime) {
        this.shiftID = shiftID;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * This method gets the shiftID
     * @return The integer value of the shiftID
     */
    public Integer getShiftID() {
        return shiftID;
    }

    /**
     * This method sets the shiftID
     * @param shiftID - Integer value of the shiftID
     */
    public void setShiftID(Integer shiftID) {
        this.shiftID = shiftID;
    }

    /**
     * This method gets the startTime
     * @return Date object specifying startTime of the shift
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * This method sets startTime of the shift
     * @param startTime - Value of Date object specifying startTime
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * This method gets the endTime
     * @return Date object specifying the endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * This method sets the endTime
     * @param endTime - Value of the Date object specifying endTime
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * This method determines whether the day falls on a weekend
     * @return The boolean value true or false
     */
    public Boolean getIsWeekend() {
        return isWeekend;
    }

    /**
     * This method sets the day as a weekend
     * @param isWeekend - True or False
     */
    public void setIsWeekend(Boolean isWeekend) {
        this.isWeekend = isWeekend;
    }

    /**
     * This method determines whether the day is a holiday
     * @return The boolean value True or False
     */
    public Boolean getIsHoliday() {
        return isHoliday;
    }

    /**
     * This method sets the day as a holiday
     * @param isHoliday - True or False
     */
    public void setIsHoliday(Boolean isHoliday) {
        this.isHoliday = isHoliday;
    }

    /**
     * This method gets the number in block
     * @return The integer value of numberInBlock
     */
    public Integer getNumberInBlock() {
        return numberInBlock;
    }

    /**
     * This method sets the value of numberInBlock
     * @param numberInBlock - Integer value for numberInBlock
     */
    public void setNumberInBlock(Integer numberInBlock) {
        this.numberInBlock = numberInBlock;
    }

    /**
     * This methods gets the day of the week
     * @return The integer value of dayOfWeek
     */
    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * This method sets the value of dayOfWeek
     * @param dayOfWeek - The integer value of dayOfWeek
     */
    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * This method gets the role
     * @return The details of the role object
     */
    public Role getRole() {
        return role;
    }

    /**
     * This method sets the Role object
     * @param role - The role name for the Role object
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * This method gets the schedule object
     * @return The details of the schedule object
     */
    public Schedule getSchedule() {
        return schedule;
    }

    /**
     * This method sets the schedule object
     * @param schedule - The name of the schedule of the object
     */
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    /**
     * This method gets the user object
     * @return The details of the user object
     */
    public User getUser() {
        return user;
    }

    /**
     * This method sets the user object
     * @param user - The name of the user object
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (shiftID != null ? shiftID.hashCode() : 0);
        return hash;
    }

    /**
     * 
     * @param object
     * @return 
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
     * This method prints the Shift objects to the display
     * @return Displays shiftIDs for all shift objects
     */
    @Override
    public String toString() {
        return "models.Shift[ shiftID=" + shiftID + " ]";
    }
    
}
