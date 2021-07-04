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
 *
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

    public Shift() {
    }

    public Shift(Integer shiftID) {
        this.shiftID = shiftID;
    }

    public Shift(Integer shiftID, Date startTime, Date endTime) {
        this.shiftID = shiftID;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getShiftID() {
        return shiftID;
    }

    public void setShiftID(Integer shiftID) {
        this.shiftID = shiftID;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Boolean getIsWeekend() {
        return isWeekend;
    }

    public void setIsWeekend(Boolean isWeekend) {
        this.isWeekend = isWeekend;
    }

    public Boolean getIsHoliday() {
        return isHoliday;
    }

    public void setIsHoliday(Boolean isHoliday) {
        this.isHoliday = isHoliday;
    }

    public Integer getNumberInBlock() {
        return numberInBlock;
    }

    public void setNumberInBlock(Integer numberInBlock) {
        this.numberInBlock = numberInBlock;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (shiftID != null ? shiftID.hashCode() : 0);
        return hash;
    }

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

    @Override
    public String toString() {
        return "models.Shift[ shiftID=" + shiftID + " ]";
    }
    
}
