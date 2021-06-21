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
 * @author Alex Zecevic
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

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TimeOffID")
    private Integer timeOffID;
    @Basic(optional = false)
    @Column(name = "RequestedDate")
    @Temporal(TemporalType.DATE)
    private Date requestedDate;
    @Basic(optional = false)
    @Column(name = "StartDate")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic(optional = false)
    @Column(name = "EndDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Basic(optional = false)
    @Column(name = "IsApproved")
    private boolean isApproved;
    @JoinColumn(name = "User", referencedColumnName = "UserID")
    @ManyToOne(optional = false)
    private User user;

    public Timeoff() {
    }

    public Timeoff(Integer timeOffID) {
        this.timeOffID = timeOffID;
    }

    public Timeoff(Integer timeOffID, Date requestedDate, Date startDate, Date endDate, boolean isApproved) {
        this.timeOffID = timeOffID;
        this.requestedDate = requestedDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isApproved = isApproved;
    }

    public Integer getTimeOffID() {
        return timeOffID;
    }

    public void setTimeOffID(Integer timeOffID) {
        this.timeOffID = timeOffID;
    }

    public Date getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(Date requestedDate) {
        this.requestedDate = requestedDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(boolean isApproved) {
        this.isApproved = isApproved;
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
        hash += (timeOffID != null ? timeOffID.hashCode() : 0);
        return hash;
    }

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

    @Override
    public String toString() {
        return "models.Timeoff[ timeOffID=" + timeOffID + " ]";
    }
    
}
