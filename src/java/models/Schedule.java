/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * This class describes the Schedule entity
 * @author 743851
 */
@Entity
@Table(name = "schedule")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Schedule.findAll", query = "SELECT s FROM Schedule s")
    , @NamedQuery(name = "Schedule.findByScheduleID", query = "SELECT s FROM Schedule s WHERE s.scheduleID = :scheduleID")
    , @NamedQuery(name = "Schedule.findByStartDate", query = "SELECT s FROM Schedule s WHERE s.startDate = :startDate")
    , @NamedQuery(name = "Schedule.findByEndDate", query = "SELECT s FROM Schedule s WHERE s.endDate = :endDate")})
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ScheduleID")
    private Integer scheduleID;
    @Basic(optional = false)
    @Column(name = "StartDate")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic(optional = false)
    @Column(name = "EndDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @JoinColumn(name = "Hospital", referencedColumnName = "HospitalID")
    @ManyToOne(optional = false)
    private Hospital hospital;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "schedule")
    private List<Shift> shiftList;

    /**
     * The default constructor for the Schedule object
     */
    public Schedule() {
    }

    /**
     * This constructs a schedule object using the scheduleID
     * @param scheduleID - The integer value of the scheduleID
     */
    public Schedule(Integer scheduleID) {
        this.scheduleID = scheduleID;
    }

    /**
     * This constructs a schedule object using the scheduleID, startDate and endDate
     * @param scheduleID - Integer value of the scheduleID
     * @param startDate - Date the schedule begins
     * @param endDate - Date the schedule ends
     */
    public Schedule(Integer scheduleID, Date startDate, Date endDate) {
        this.scheduleID = scheduleID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * This method gets the scheduleID
     * @return The integer value of the scheduleID
     */
    public Integer getScheduleID() {
        return scheduleID;
    }

    /**
     * This method sets the value of the scheduleID
     * @param scheduleID - Integer value of the scheduleID
     */
    public void setScheduleID(Integer scheduleID) {
        this.scheduleID = scheduleID;
    }

    /**
     * This method gets the value of the startDate
     * @return The start date of the schedule
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * This method sets the value of the startDate
     * @param startDate - Date the schedule begins
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * This method gets the value of the endDate
     * @return The date the schedule ends
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * This method sets the endDate of the schedule
     * @param endDate - Date the schedule terminates
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * This method gets the Hospital object
     * @return The hospital of type Hospital
     */
    public Hospital getHospital() {
        return hospital;
    }

    /**
     * This method sets the Hospital object
     * @param hospital - The Hospital object where the schedule will be implemented
     */
    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    /**
     * This method gets a list of all the shifts
     * @return The list of shifts in the shiftList
     */
    @XmlTransient
    public List<Shift> getShiftList() {
        return shiftList;
    }

    /**
     * This method sets the list of shifts
     * @param shiftList - The shifts in the shiftList
     */
    public void setShiftList(List<Shift> shiftList) {
        this.shiftList = shiftList;
    }

    /**
     * 
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (scheduleID != null ? scheduleID.hashCode() : 0);
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
     * This method prints the details of the Schedule objects
     * @return - The details of the schedule object
     */
    @Override
    public String toString() {
        return "models.Schedule[ scheduleID=" + scheduleID + " ]";
    }
    
}
